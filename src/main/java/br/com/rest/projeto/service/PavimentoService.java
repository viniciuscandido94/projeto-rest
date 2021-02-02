package br.com.rest.projeto.service;

import br.com.rest.projeto.DTO.requestDTO.PavimentoRequestDTO;
import br.com.rest.projeto.DTO.responseDTO.PavimentoResponseDTO;
import br.com.rest.projeto.business.PavimentoBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PavimentoService {

    @Autowired
    private PavimentoBusiness business;

    public List<PavimentoResponseDTO> findByProjetoId(Long idProjeto, String userLogado) throws Exception {
        return business.findByProjetoId(idProjeto, userLogado);
    }

    @Transactional
    public PavimentoResponseDTO cadastra(PavimentoRequestDTO pavimentoRequestDTO, String userLogado) throws Exception {
        return business.cadastra(pavimentoRequestDTO, userLogado);
    }

    public PavimentoResponseDTO findById(Long id, String userLogado) throws Exception {
        return business.findById(id, userLogado);
    }

    @Transactional
    public PavimentoResponseDTO atualizar(Long id, PavimentoRequestDTO pavimentoRequestDTO, String userLogado) throws Exception {
        return business.atualizar(id, pavimentoRequestDTO, userLogado);
    }
}
