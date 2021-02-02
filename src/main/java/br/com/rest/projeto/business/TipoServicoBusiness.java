package br.com.rest.projeto.business;

import br.com.rest.projeto.DTO.responseDTO.TipoServicoResponseDTO;
import br.com.rest.projeto.entity.Projeto;
import br.com.rest.projeto.entity.TipoServico;
import br.com.rest.projeto.repository.ProjetoRepository;
import br.com.rest.projeto.repository.TipoServicoRepository;
import br.com.rest.projeto.DTO.requestDTO.TipoServicoRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TipoServicoBusiness {

    @Autowired
    private TipoServicoRepository tipoServicoRepository;

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private EmpresaBusiness empresaBusiness;

    public List<TipoServicoResponseDTO> findByProjetoId(Long idProjeto, String userLogado) throws Exception {
        empresaBusiness.empresaValida(idProjeto, userLogado);

        List<TipoServico> tipoServicos = tipoServicoRepository.findByProjetoId(idProjeto);
        List<TipoServico> tipoServicosDefault = tipoServicoRepository.findByProjetoIdIsNull();
        tipoServicos.addAll(tipoServicosDefault);

        List<TipoServicoResponseDTO> tipoServicosResponseDTOS = new ArrayList<>();
        tipoServicos.forEach( tip -> tipoServicosResponseDTOS.add(new TipoServicoResponseDTO(tip.getId(), tip.getDescricao(), (tip.getProjeto() == null) ? null : idProjeto, tip.getServicoQualidade())));

        return tipoServicosResponseDTOS;
    }

    public TipoServicoResponseDTO cadastra(TipoServicoRequestDTO tipoServicoRequestDTO, String userLogado) throws Exception {
        empresaBusiness.empresaValida(tipoServicoRequestDTO.getIdProjeto(), userLogado);

        Optional<Projeto> optionalProjeto = projetoRepository.findById(tipoServicoRequestDTO.getIdProjeto());
        if (!optionalProjeto.isPresent()){
            throw new Exception("Nao encontrado projeto com ID: " + tipoServicoRequestDTO.getIdProjeto());
        }

        Projeto projeto = optionalProjeto.get();
        TipoServico tipoServico = new TipoServico(tipoServicoRequestDTO.getDescricao(), tipoServicoRequestDTO.getServicoDeQualidade(), projeto);
        tipoServicoRepository.save(tipoServico);

        return new TipoServicoResponseDTO(tipoServico.getId(), tipoServico.getDescricao(), projeto.getId(), tipoServico.getServicoQualidade());
    }

    public TipoServicoResponseDTO findById(Long id, String userLogado) throws Exception {
        // Em um futuro alterar a rota para vir o projeto e validar se pertence ao mesmo, não feito agora devido não ter nenhuma
        // funcionalidade ainda que usa essa rota, e requisicoes direto pela URL não necessitam dessa validação.

        Optional<TipoServico> optionalTipoServico = findTipoServicoRepositoryById(id);

        if (!optionalTipoServico.isPresent()) {
            throw new Exception("Nao encontrado servico atraves do ID: " + id);
        }

        TipoServico tipoServico = optionalTipoServico.get();
        empresaBusiness.empresaValida(tipoServico.getProjeto().getId(), userLogado);
        return new TipoServicoResponseDTO(tipoServico.getId(), tipoServico.getDescricao(), tipoServico.getProjeto().getId(), tipoServico.getServicoQualidade());
    }

    public TipoServicoResponseDTO atualizar(Long id, TipoServicoRequestDTO tipoServicoRequestDTO, String userLogado) throws Exception {
        empresaBusiness.empresaValida(tipoServicoRequestDTO.getIdProjeto(), userLogado);

        Optional<TipoServico> optionalTipoServico = findTipoServicoRepositoryById(id);
        if (!optionalTipoServico.isPresent()) {
            throw new Exception("Nao encontrado servico atraves do ID: " + id);
        }
        TipoServico tipoServico = optionalTipoServico.get();
        tipoServico.setDescricao(tipoServicoRequestDTO.getDescricao());
        tipoServicoRepository.save(tipoServico);

        return new TipoServicoResponseDTO(tipoServico.getId(), tipoServico.getDescricao(), tipoServico.getProjeto().getId(), tipoServico.getServicoQualidade());
    }

    private Optional<TipoServico> findTipoServicoRepositoryById(Long id){
        return tipoServicoRepository.findById(id);
    }
}
