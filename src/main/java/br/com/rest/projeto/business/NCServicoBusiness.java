package br.com.rest.projeto.business;

import br.com.rest.projeto.DTO.responseDTO.*;
import br.com.rest.projeto.auxiliary.ExcelFileGenerator;
import br.com.rest.projeto.auxiliary.FunctionsAuxiliary;
import br.com.rest.projeto.entity.*;
import br.com.rest.projeto.entity.entityEnum.Eficacia;
import br.com.rest.projeto.entity.entityEnum.StatusServico;
import br.com.rest.projeto.entity.entityEnum.TipoCadastro;
import br.com.rest.projeto.repository.*;
import br.com.rest.projeto.DTO.requestDTO.NCServicoRequestDTO;
import br.com.rest.projeto.entity.entityEnum.TipoPrazo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Component
public class NCServicoBusiness {

    private static Logger logger = LoggerFactory.getLogger(NCServicoBusiness.class);

    @Autowired
    private NCServicoRepository ncServicoRepository;

    @Autowired
    private UnidadeRepository unidadeRepository;

    @Autowired
    private PavimentoRepository pavimentoRepository;

    @Autowired
    private TipoServicoRepository tipoServicoRepository;

    @Autowired
    private FuncionarioRepository funcionarioRepository;

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private FotosRepository fotosRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EmpresaBusiness empresaBusiness;

    @Autowired
    private UsuarioBusiness usuarioBusiness;

    @Autowired
    private ProjetoBusiness projetoBusiness;

    public List<NCServicoResponseDTO> findByProjetoId(Long idProjeto, Pageable pageable, String userLogado) throws Exception {
        empresaBusiness.empresaValida(idProjeto, userLogado);

        List<NCServico> ncServicosList = ncServicoRepository.findByProjetoId(idProjeto, pageable);
        List<NCServicoResponseDTO> ncServicoResponseDTOList = new ArrayList<>();
        ncServicosList.forEach( ncServico -> {
            List<String> fotos = findFotosServico(ncServico.getId());
            NCServicoResponseDTO ncServicoResponseDTO = montagemResponse(new NCServicoResponseDTO(), ncServico, fotos);
            ncServicoResponseDTOList.add(ncServicoResponseDTO);
        });
        return ncServicoResponseDTOList;
    }

    public void gerarExcelByProjeto(Long idProjeto, HttpServletResponse response) throws Exception {
        List<NCServico> ncServicosList = ncServicoRepository.findByProjetoId(idProjeto);
        Optional<Projeto> projetoOptional = projetoRepository.findById(idProjeto);
        if (ncServicosList.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        try {
            String fileName = projetoOptional.get().getDescricao().replaceAll(" ", "_").trim();
            ExcelFileGenerator excelFileGenerator = new ExcelFileGenerator(fileName, "servicos");
            excelFileGenerator.generateExcelFileForNCServico(ncServicosList, response);
        } catch (Exception e) {
            logger.error("Erro ao gerar a listagem de servicos para excel do projeto com ID " + idProjeto + ". Segue erro -> " + e);
        }
    }

    public NCServicoResponseDTO findById(Long idServico, Long idProjeto, String userLogado) throws Exception {
        Optional<NCServico> optionalNCServico = ncServicoRepository.findById(idServico);
        if (!optionalNCServico.isPresent() || !optionalNCServico.get().getProjeto().getId().equals(idProjeto)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        empresaBusiness.empresaValida(optionalNCServico.get().getProjeto().getId(), userLogado);
        List<String> fotos = findFotosServico(idServico);
        return montagemResponse(new NCServicoResponseDTO(), optionalNCServico.get(), fotos);
    }

    public NCServicoResponseDTO cadastra(NCServicoRequestDTO ncServicoRequestDTO, String userLogado) throws Exception {
        empresaBusiness.empresaValida(ncServicoRequestDTO.getIdProjeto(), userLogado);

        List<String> fotos = null;
        NCServico ncServico = new NCServico();
        try {
            ncServico = cadastrarServico(ncServicoRequestDTO, new NCServico(), userLogado);
            ncServicoRepository.save(ncServico);
            if (ncServicoRequestDTO.getFoto() != null) {
                fotos = cadastrarFoto(ncServicoRequestDTO, ncServico);
            }
        }catch (IOException e) {
            logger.error("Erro ao cadastrar NC/Servico com ID: " + ncServico.getId() + ". Descricao: " + ncServicoRequestDTO.getDescricao() + ", que foi feita pelo Usuario: " + userLogado + ". Segue erro -> " + e);
        }
        return montagemResponse(new NCServicoResponseDTO(), ncServico, fotos);
    }

    public NCServicoResponseDTO atualiza(Long id, NCServicoRequestDTO ncServicoRequestDTO, String userLogado) throws Exception {
        empresaBusiness.empresaValida(ncServicoRequestDTO.getIdProjeto(), userLogado);

        List<String> fotos = null;
        NCServico ncServico = new NCServico();
        Optional<NCServico> optionalNCServico = ncServicoRepository.findById(id);
        if (!optionalNCServico.isPresent()) {
            throw new Exception("Nao encontrado NC/Servico atraves do ID: " + id);
        }
        try {
            ncServico = cadastrarServico(ncServicoRequestDTO, optionalNCServico.get(), null); // Usuario logado com valor null devido ao fato dele só gravar o usuário no cadastro.
            ncServicoRepository.save(ncServico);                                                         // Na alteracao grava apenas log.
            if (ncServicoRequestDTO.getFoto() != null) {
                fotos = cadastrarFoto(ncServicoRequestDTO, ncServico);
            }
            logger.info("O usuario: " + userLogado + " alterou o servico/NC de ID " + id);
        }catch (IOException e) {
            logger.error("Atualizacao do(a) NC/Servico com ID: " + id + " tentada pelo usuario: " + userLogado + ", FALHOU. Segue erro -> " + e);
        }
        return montagemResponse(new NCServicoResponseDTO(), ncServico, fotos);
    }

    private NCServico cadastrarServico(NCServicoRequestDTO ncServicoRequestDTO, NCServico ncServico, String userLogado) throws Exception {
        /* Unidade e Pavimento tem dois campos (numerico e normal), se vier os dois preenchidos no JSON, a prioridade será dada
        * para o NUMERICO, devido ao fato de nao poder ter os dois preenchidos no mesmo NC/Servico. Ou é um ou outro, ou os dois null */

        Projeto projeto = projetoBusiness.findProjeto(ncServicoRequestDTO.getIdProjeto());
        Unidade unidade = findUnidade(ncServicoRequestDTO, projeto);
        Pavimento pavimento = findPavimento(ncServicoRequestDTO, projeto);
        TipoServico tipoServico = findTipoServico(ncServicoRequestDTO, projeto);
        Funcionario funcionario = findFuncionario(ncServicoRequestDTO, projeto);

        if (userLogado != null){
            Usuario usuario = usuarioBusiness.findUsuario(userLogado);
            ncServico.setUsuario(usuario);
        }
        ncServico.setStatus(setStatusCorretoPelaData(ncServicoRequestDTO));
        ncServico.setDataConclusao(setDataConclusao(ncServico.getStatus()));
        ncServico.setTorre(ncServicoRequestDTO.getTorre());
        ncServico.setPavimento(pavimento);
        ncServico.setPavimentoNumerico(ncServicoRequestDTO.getPavimentoNumerico());
        ncServico.setUnidade(unidade);
        ncServico.setUnidadeNumerico(ncServicoRequestDTO.getUnidadeNumerico());
        ncServico.setTipoServico(tipoServico);
        ncServico.setDocumentoPbqp(ncServicoRequestDTO.getDocumentoPBQP());
        ncServico.setRevisao(ncServicoRequestDTO.getRevisao());
        ncServico.setDescricao(ncServicoRequestDTO.getDescricao());
        ncServico.setResponsavel(funcionario);
        ncServico.setDataInicio(ncServicoRequestDTO.getDataInicio());
        ncServico.setPrazo(ncServicoRequestDTO.getPrazo());
        ncServico.setTipoPrazo(TipoPrazo.valueOf(ncServicoRequestDTO.getTipoPrazo()));
        ncServico.setProjeto(projeto);
        ncServico.setTipoCadastro(TipoCadastro.valueOf(ncServicoRequestDTO.getTipoCadastro()));
        ncServico.setPrescricao(ncServicoRequestDTO.getPrescricao());
        ncServico.setEficacia((ncServicoRequestDTO.getEficacia() == null) ? null : Eficacia.valueOf(ncServicoRequestDTO.getEficacia()));
        ncServico.setJustificativaEficacia(ncServicoRequestDTO.getJustificativaEficacia());
        ncServico.setDocQualidade(ncServicoRequestDTO.getDocQualidade());
        ncServico.setDataPrevisaoTermino(ncServicoRequestDTO.getDataPrevisaoTermino());

        return ncServico;
    }

    private LocalDate setDataConclusao(StatusServico status) {
        LocalDate dataConclusao = null;
        if (status == StatusServico.CONCLUIDO){
            dataConclusao = FunctionsAuxiliary.retornaDataAtualEmLocalDate(new Date());
        }
        return dataConclusao;
    }

    private StatusServico setStatusCorretoPelaData(NCServicoRequestDTO ncServicoRequestDTO) {
        StatusServico status = StatusServico.EM_EXECUCAO;
        LocalDate dataAtual = FunctionsAuxiliary.retornaDataAtualEmLocalDate(new Date());

        if (ncServicoRequestDTO.getStatus() != null && StatusServico.valueOf(ncServicoRequestDTO.getStatus()) == StatusServico.CONCLUIDO) {
            status = StatusServico.CONCLUIDO;
        } else if (ncServicoRequestDTO.getDataPrevisaoTermino().isEqual(dataAtual)) {
            status = StatusServico.PENDENTE;
        } else if (dataAtual.isAfter(ncServicoRequestDTO.getDataPrevisaoTermino())) {
            status = StatusServico.VENCIDO;
        } else if (dataAtual.isBefore(ncServicoRequestDTO.getDataInicio())){
            status = StatusServico.AGENDADO;
        }

        return status;
    }

    private Unidade findUnidade(NCServicoRequestDTO ncServicoRequestDTO, Projeto projeto) throws Exception {
        Unidade unidade = null;
        if (ncServicoRequestDTO.getUnidade() != null && ncServicoRequestDTO.getUnidadeNumerico() == null) {
            if (ncServicoRequestDTO.getUnidade().getId() != null) {
                Optional<Unidade> unidadeOptional = unidadeRepository.findById(ncServicoRequestDTO.getUnidade().getId());
                if (!unidadeOptional.isPresent()) {
                    throw new Exception("Nao encontrado unidade com ID: " + ncServicoRequestDTO.getUnidade().getId());
                }
                unidade = unidadeOptional.get();
                unidade.setDescricao(ncServicoRequestDTO.getUnidade().getDescricao()); // SEMPRE ALTERANDO
                unidade.setProjeto(projeto);
            } else {
                unidade = new Unidade(ncServicoRequestDTO.getUnidade().getDescricao(), projeto);
            }
            unidadeRepository.save(unidade);
        }
        return unidade;
    }

    private Pavimento findPavimento(NCServicoRequestDTO ncServicoRequestDTO, Projeto projeto) throws Exception {
        Pavimento pavimento = null;
        if (ncServicoRequestDTO.getPavimento() != null && ncServicoRequestDTO.getPavimentoNumerico() == null) {
            if (ncServicoRequestDTO.getPavimento().getId() != null) {
                Optional<Pavimento> pavimentoOptional = pavimentoRepository.findById(ncServicoRequestDTO.getPavimento().getId());
                if (!pavimentoOptional.isPresent()) {
                    throw new Exception("Nao encontrado pavimento com ID: " + ncServicoRequestDTO.getPavimento().getId());
                }
                pavimento = pavimentoOptional.get();
                pavimento.setDescricao(ncServicoRequestDTO.getPavimento().getDescricao()); // SEMPRE ALTERANDO
                pavimento.setProjeto(projeto);
            } else {
                pavimento = new Pavimento(ncServicoRequestDTO.getPavimento().getDescricao(), projeto);
            }
            pavimentoRepository.save(pavimento);
        }
        return pavimento;
    }

    private TipoServico findTipoServico(NCServicoRequestDTO ncServicoRequestDTO, Projeto projeto) throws Exception {
        TipoServico tipoServico = new TipoServico();
        if (ncServicoRequestDTO.getTipoServico().getId() != null) {
            Optional<TipoServico> tipoServicoOptional = tipoServicoRepository.findById(ncServicoRequestDTO.getTipoServico().getId());
            if (!tipoServicoOptional.isPresent()) {
                throw new Exception("Nao encontrado servico com ID: " + ncServicoRequestDTO.getTipoServico().getId());
            }
            tipoServico = tipoServicoOptional.get();
        } else {
            tipoServico.setProjeto(projeto);
        }
        tipoServico.setDescricao(ncServicoRequestDTO.getTipoServico().getDescricao()); // SEMPRE ALTERANDO
        tipoServico.setServicoQualidade(ncServicoRequestDTO.getTipoServico().getServicoDeQualidade());
        tipoServicoRepository.save(tipoServico);

        return tipoServico;
    }

    private Funcionario findFuncionario(NCServicoRequestDTO ncServicoRequestDTO, Projeto projeto) throws Exception {
        Funcionario funcionario = new Funcionario();
        if (ncServicoRequestDTO.getFuncionario().getId() != null) {
            Optional<Funcionario> funcionarioOptional = funcionarioRepository.findById(ncServicoRequestDTO.getFuncionario().getId());
            if (!funcionarioOptional.isPresent()) {
                 throw new Exception("Nao encontrado funcionario com ID: " + ncServicoRequestDTO.getFuncionario().getId());
            }
            funcionario = funcionarioOptional.get();
        }
        funcionario.setNome(ncServicoRequestDTO.getFuncionario().getNome());
        funcionario.setTelefone(ncServicoRequestDTO.getFuncionario().getTelefone());
        funcionario.setEmpresa(projeto.getEmpresa());
        funcionarioRepository.save(funcionario);

        return funcionario;
    }

    private NCServicoResponseDTO montagemResponse(NCServicoResponseDTO ncServicoResponseDTO, NCServico ncServico, List<String> fotos){
        ncServicoResponseDTO.setId(ncServico.getId());
        ncServicoResponseDTO.setStatus(FunctionsAuxiliary.setStatusCorretoPelaData(ncServico));
        ncServicoResponseDTO.setTorre(ncServico.getTorre());
        ncServicoResponseDTO.setPavimento(montagemResponse(new PavimentoResponseDTO(), ncServico));
        ncServicoResponseDTO.setUnidade(montagemResponse(new UnidadeResponseDTO(), ncServico));
        ncServicoResponseDTO.setTipoServico(montagemResponse(new TipoServicoResponseDTO(), ncServico));
        ncServicoResponseDTO.setDocumentoPBQP(ncServico.getDocumentoPbqp());
        ncServicoResponseDTO.setRevisao(ncServico.getRevisao());
        ncServicoResponseDTO.setDescricao(ncServico.getDescricao());
        ncServicoResponseDTO.setFuncionario(montagemResponse(new FuncionarioResponseDTO(), ncServico));
        ncServicoResponseDTO.setDataInicio(ncServico.getDataInicio().toString());
        ncServicoResponseDTO.setPrazo(ncServico.getPrazo());
        ncServicoResponseDTO.setProjeto(montagemResponse(new ProjetoResponseDTO(), ncServico));
        ncServicoResponseDTO.setTipoCadastro(ncServico.getTipoCadastro().name());
        ncServicoResponseDTO.setPrescricao(ncServico.getPrescricao());
        ncServicoResponseDTO.setEficacia((ncServico.getEficacia() == null) ? null : ncServico.getEficacia().name());
        ncServicoResponseDTO.setJustificativaEficacia(ncServico.getJustificativaEficacia());
        ncServicoResponseDTO.setTipoPrazo(ncServico.getTipoPrazo().name());
        ncServicoResponseDTO.setFoto(fotos);
        ncServicoResponseDTO.setDocQualidade(ncServico.getDocQualidade());
        ncServicoResponseDTO.setDataPrevisaoTermino(ncServico.getDataPrevisaoTermino().toString());
        ncServicoResponseDTO.setDataConclusao((ncServico.getDataConclusao() != null) ? ncServico.getDataConclusao().toString() : null);
        ncServicoResponseDTO.setUnidadeNumerico(ncServico.getUnidadeNumerico());
        ncServicoResponseDTO.setPavimentoNumerico(ncServico.getPavimentoNumerico());
        ncServicoResponseDTO.setTelefoneUsuario(ncServico.getUsuario().getTelefoneLogin());
        ncServicoResponseDTO.setNomeUsuario(ncServico.getUsuario().getNome());

        return ncServicoResponseDTO;
    }

    private PavimentoResponseDTO montagemResponse(PavimentoResponseDTO pavimentoResponseDTO, NCServico ncServico){
        if(ncServico.getPavimento() == null){
            return null;
        }
        pavimentoResponseDTO.setId(ncServico.getPavimento().getId());
        pavimentoResponseDTO.setDescricao(ncServico.getPavimento().getDescricao());
        pavimentoResponseDTO.setIdProjeto(ncServico.getProjeto().getId());
        return pavimentoResponseDTO;
    }

    private UnidadeResponseDTO montagemResponse(UnidadeResponseDTO unidadeResponseDTO, NCServico ncServico){
        if(ncServico.getUnidade() == null){
            return null;
        }
        unidadeResponseDTO.setId(ncServico.getUnidade().getId());
        unidadeResponseDTO.setDescricao(ncServico.getUnidade().getDescricao());
        unidadeResponseDTO.setIdProjeto(ncServico.getProjeto().getId());
        return unidadeResponseDTO;
    }

    private TipoServicoResponseDTO montagemResponse(TipoServicoResponseDTO tipoServicoResponseDTO, NCServico ncServico){
        if(ncServico.getTipoServico() == null){
            return null;
        }
        tipoServicoResponseDTO.setId(ncServico.getTipoServico().getId());
        tipoServicoResponseDTO.setDescricao(ncServico.getTipoServico().getDescricao());
        tipoServicoResponseDTO.setIdProjeto(ncServico.getProjeto().getId());
        tipoServicoResponseDTO.setServicoDeQualidade(ncServico.getTipoServico().getServicoQualidade());
        return tipoServicoResponseDTO;
    }

    private FuncionarioResponseDTO montagemResponse(FuncionarioResponseDTO funcionarioResponseDTO, NCServico ncServico){
        funcionarioResponseDTO.setId(ncServico.getResponsavel().getId());
        funcionarioResponseDTO.setNome(ncServico.getResponsavel().getNome());
        funcionarioResponseDTO.setTelefone(ncServico.getResponsavel().getTelefone());
        return funcionarioResponseDTO;
    }

    private ProjetoResponseDTO montagemResponse(ProjetoResponseDTO projetoResponseDTO, NCServico ncServico){
        projetoResponseDTO.setId(ncServico.getProjeto().getId());
        projetoResponseDTO.setDescricao(ncServico.getProjeto().getDescricao());
        return projetoResponseDTO;
    }

    private List<String> cadastrarFoto(NCServicoRequestDTO ncServicoRequestDTO, NCServico ncServico){
        int[] index = { 0 };
        List<String> fotos = new ArrayList<>();
        List<Foto> fotosAntigas = fotosRepository.findByServicoId(ncServico.getId());
        if (!fotosAntigas.isEmpty()){
            deleteFotosAntigasInDirectory(fotosAntigas);
            fotosRepository.deleteByServicoId(ncServico.getId());
        }
        ncServicoRequestDTO.getFoto().forEach(mime -> {
            String extensionImg = FunctionsAuxiliary.findExtensionFromBase64(mime);
            mime = FunctionsAuxiliary.formatMimeBase64(mime);
            byte[] decodedImg = Base64.getMimeDecoder().decode(mime);
            DateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmssSSS");
            Date date = new Date();
            String name = dateFormat.format(date) + ncServico.getId() + index[0] + "." + extensionImg;
            try {
                Path destinationFile = Paths.get("/projeto-rest/src/main/resources/static/fotos/", name);
                Files.write(destinationFile, decodedImg);
                index[0]++;
                fotos.add(persistenceInPhotosTable("/fotos/" + name, ncServico));
            } catch (IOException e) {
                logger.error("Ocorreu um problema no momento de salvar/criar a foto para o Servico/NC de ID: " + ncServico.getId() + ". Segue erro -> " + e);
            }
        });
        return fotos;
    }

    private String persistenceInPhotosTable(String name, NCServico ncServico){
        Foto foto = new Foto(name, ncServico);
        fotosRepository.save(foto);
        return foto.getNome();
    }

    private List<String> findFotosServico(Long id) {
        List<String> fotosInString = new ArrayList<>();
        List<Foto> fotos = fotosRepository.findByServicoId(id);
        fotos.forEach( foto -> fotosInString.add("" + foto.getNome()));
        return fotosInString;
    }

    private void deleteFotosAntigasInDirectory(List<Foto> fotosAntigas) {
        File pastaFotos = new File("/projeto-rest/src/main/resources/static/fotos/");
        File [] arquivos = pastaFotos.listFiles();
        assert arquivos != null;
        for (int i = 0; i < arquivos.length; i++){
            for (Foto foto : fotosAntigas ){
                String nameFoto = FunctionsAuxiliary.removedPathOfFotosName(foto.getNome());
                if (arquivos[i].getName().matches(nameFoto)){
                    arquivos[i].delete();
                }
            }
        }
    }
}