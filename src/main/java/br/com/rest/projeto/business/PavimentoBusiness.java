package br.com.rest.projeto.business;

import br.com.rest.projeto.DTO.requestDTO.PavimentoRequestDTO;
import br.com.rest.projeto.entity.Projeto;
import br.com.rest.projeto.repository.PavimentoRepository;
import br.com.rest.projeto.DTO.responseDTO.PavimentoResponseDTO;
import br.com.rest.projeto.entity.Pavimento;
import br.com.rest.projeto.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.Collator;
import java.util.*;

@Component
public class PavimentoBusiness {

    @Autowired
    private PavimentoRepository pavimentoRepository;

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private EmpresaBusiness empresaBusiness;

    public List<PavimentoResponseDTO> findByProjetoId(Long idProjeto, String userLogado) throws Exception {
        empresaBusiness.empresaValida(idProjeto, userLogado);
        List<Pavimento> pavimentos = pavimentoRepository.findByProjetoId(idProjeto);
        List<PavimentoResponseDTO> pavimentoResponseDTOS = new ArrayList<>();

        pavimentos.forEach( pav -> pavimentoResponseDTOS.add(new PavimentoResponseDTO(pav.getId(), pav.getDescricao(), idProjeto)));
        pavimentoResponseDTOS.sort(new Comparator<PavimentoResponseDTO>() {
            @Override
            public int compare(PavimentoResponseDTO pavimentoResponseDTO, PavimentoResponseDTO t1) {
                return Collator.getInstance().compare(pavimentoResponseDTO.getDescricao(), t1.getDescricao());
            }
        });
        return pavimentoResponseDTOS;
    }

    public PavimentoResponseDTO cadastra(PavimentoRequestDTO pavimentoRequestDTO, String userLogado) throws Exception {
        empresaBusiness.empresaValida(pavimentoRequestDTO.getIdProjeto(), userLogado);

        Optional<Projeto> optionalProjeto = projetoRepository.findById(pavimentoRequestDTO.getIdProjeto());
        if (!optionalProjeto.isPresent()){
            throw new Exception("Nao encontrado projeto com ID: " + pavimentoRequestDTO.getIdProjeto());
        }
        Projeto projeto = optionalProjeto.get();
        Pavimento pavimento = new Pavimento(pavimentoRequestDTO.getDescricao(), projeto);
        pavimentoRepository.save(pavimento);

        return new PavimentoResponseDTO(pavimento.getId(), pavimento.getDescricao(), projeto.getId());
    }

    public PavimentoResponseDTO findById(Long id, String userLogado) throws Exception {
        // Em um futuro alterar a rota para vir o projeto e validar se pertence ao mesmo, não feito agora devido não ter nenhuma
        // funcionalidade ainda que usa essa rota, e requisicoes direto pela URL não necessitam dessa validação.

        Optional<Pavimento> pavimentoOptional = findPavimentoRepositoryById(id);
        if (!pavimentoOptional.isPresent()) {
            throw new Exception("Nao encontrado pavimento atraves do ID: " + id);
        }

        Pavimento pavimento = pavimentoOptional.get();
        empresaBusiness.empresaValida(pavimento.getProjeto().getId(), userLogado);
        return new PavimentoResponseDTO(pavimento.getId(), pavimento.getDescricao(), pavimento.getProjeto().getId());
    }

    public PavimentoResponseDTO atualizar(Long id, PavimentoRequestDTO pavimentoRequestDTO, String userLogado) throws Exception {
        empresaBusiness.empresaValida(pavimentoRequestDTO.getIdProjeto(), userLogado);

        Optional<Pavimento> optionalPavimento = findPavimentoRepositoryById(id);
        if (!optionalPavimento.isPresent()) {
            throw new Exception("Nao encontrado pavimento atraves do ID: " + id);
        }
        Pavimento pavimento = optionalPavimento.get();
        pavimento.setDescricao(pavimentoRequestDTO.getDescricao());
        pavimentoRepository.save(pavimento);

        return new PavimentoResponseDTO(pavimento.getId(), pavimento.getDescricao(), pavimento.getProjeto().getId());
    }

    private Optional<Pavimento> findPavimentoRepositoryById(Long id){
        return pavimentoRepository.findById(id);
    }

}
