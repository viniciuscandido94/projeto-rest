package br.com.rest.projeto.service;

import br.com.rest.projeto.DTO.responseDTO.UnidadeResponseDTO;
import br.com.rest.projeto.DTO.requestDTO.UnidadeRequestDTO;
import br.com.rest.projeto.business.UnidadeBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

@Service
public class UnidadeService {

    @Autowired
    private UnidadeBusiness business;

    public List<UnidadeResponseDTO> findByProjetoId(Long idProjeto, String userLogado) throws Exception {
        return business.findByProjetoId(idProjeto, userLogado);
    }

    @Transactional
    public UnidadeResponseDTO cadastra(UnidadeRequestDTO unidadeRequestDTO, String userLogado) throws Exception {
        return business.cadastra(unidadeRequestDTO, userLogado);
    }

    public UnidadeResponseDTO findById(Long id, String userLogado) throws Exception {
        return business.findById(id, userLogado);
    }

    @Transactional
    public UnidadeResponseDTO atualizar(Long id, @Valid UnidadeRequestDTO unidadeRequestDTO, String userLogado) throws Exception {
        return business.atualizar(id, unidadeRequestDTO, userLogado);
    }
}
