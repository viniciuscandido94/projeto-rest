package br.com.rest.projeto.business;

import br.com.rest.projeto.DTO.requestDTO.UnidadeRequestDTO;
import br.com.rest.projeto.DTO.responseDTO.UnidadeResponseDTO;
import br.com.rest.projeto.entity.Projeto;
import br.com.rest.projeto.entity.Unidade;
import br.com.rest.projeto.repository.ProjetoRepository;
import br.com.rest.projeto.repository.UnidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.text.Collator;
import java.util.*;

@Component
public class UnidadeBusiness {

    @Autowired
    private UnidadeRepository unidadeRepository;

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private EmpresaBusiness empresaBusiness;

    public List<UnidadeResponseDTO> findByProjetoId(Long idProjeto, String userLogado) throws Exception {
        empresaBusiness.empresaValida(idProjeto, userLogado);

        List<Unidade> unidades = unidadeRepository.findByProjetoId(idProjeto);
        List<UnidadeResponseDTO> unidadesResponseDTOS = new ArrayList<>();
        unidades.forEach( uni -> unidadesResponseDTOS.add(new UnidadeResponseDTO(uni.getId(), uni.getDescricao(), idProjeto)));
        unidadesResponseDTOS.sort(new Comparator<UnidadeResponseDTO>() {
            @Override
            public int compare(UnidadeResponseDTO unidadeResponseDTO, UnidadeResponseDTO t1) {
                return Collator.getInstance().compare(unidadeResponseDTO.getDescricao(), t1.getDescricao());
            }
        });
        return unidadesResponseDTOS;
    }

    public UnidadeResponseDTO cadastra(UnidadeRequestDTO unidadeRequestDTO, String userLogado) throws Exception {
        empresaBusiness.empresaValida(unidadeRequestDTO.getIdProjeto(), userLogado);

        Optional<Projeto> optionalProjeto = projetoRepository.findById(unidadeRequestDTO.getIdProjeto());
        if (!optionalProjeto.isPresent()){
            throw new Exception("Nao encontrado projeto com ID: " + unidadeRequestDTO.getIdProjeto());
        }

        Projeto projeto = optionalProjeto.get();
        Unidade unidade = new Unidade(unidadeRequestDTO.getDescricao(), projeto);
        unidadeRepository.save(unidade);

        return new UnidadeResponseDTO(unidade.getId(), unidade.getDescricao(), projeto.getId());
    }

    public UnidadeResponseDTO findById(Long id, String userLogado) throws Exception {
        // Em um futuro alterar a rota para vir o projeto e validar se pertence ao mesmo, não feito agora devido não ter nenhuma
        // funcionalidade ainda que usa essa rota, e requisicoes direto pela URL não necessitam dessa validação.

        Optional<Unidade> optionalUnidade = findUnidadeRepositoryById(id);

        if (!optionalUnidade.isPresent()) {
            throw new Exception("Nao encontrado unidade atraves do ID: " + id);
        }

        Unidade unidade = optionalUnidade.get();
        empresaBusiness.empresaValida(unidade.getProjeto().getId(), userLogado);
        return new UnidadeResponseDTO(unidade.getId(), unidade.getDescricao(), unidade.getProjeto().getId());
    }

    public UnidadeResponseDTO atualizar(Long id, @Valid UnidadeRequestDTO unidadeRequestDTO, String userLogado) throws Exception {
        empresaBusiness.empresaValida(unidadeRequestDTO.getIdProjeto(), userLogado);

        Optional<Unidade> optionalUnidade = findUnidadeRepositoryById(id);
        if (!optionalUnidade.isPresent()) {
            throw new Exception("Nao encontrado unidade atraves do ID: " + id);
        }
        Unidade unidade = optionalUnidade.get();
        unidade.setDescricao(unidadeRequestDTO.getDescricao());
        unidadeRepository.save(unidade);

        return new UnidadeResponseDTO(unidade.getId(), unidade.getDescricao(), unidade.getProjeto().getId());
    }

    private Optional<Unidade> findUnidadeRepositoryById(Long id){
        return unidadeRepository.findById(id);
    }
}
