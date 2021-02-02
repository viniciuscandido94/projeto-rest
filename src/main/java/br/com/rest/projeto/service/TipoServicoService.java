package br.com.rest.projeto.service;

import br.com.rest.projeto.DTO.requestDTO.TipoServicoRequestDTO;
import br.com.rest.projeto.DTO.responseDTO.TipoServicoResponseDTO;
import br.com.rest.projeto.business.TipoServicoBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TipoServicoService {

    @Autowired
    private TipoServicoBusiness business;

    public List<TipoServicoResponseDTO> findByProjetoId(Long idProjeto, String userLogado) throws Exception {
        return business.findByProjetoId(idProjeto, userLogado);
    }

    @Transactional
    public TipoServicoResponseDTO cadastra(TipoServicoRequestDTO tipoServicoRequestDTO, String userLogado) throws Exception {
        return business.cadastra(tipoServicoRequestDTO, userLogado);
    }

    public TipoServicoResponseDTO findById(Long id, String userLogado) throws Exception {
        return business.findById(id, userLogado);
    }

    @Transactional
    public TipoServicoResponseDTO atualizar(Long id, TipoServicoRequestDTO tipoServicoRequestDTO, String userLogado) throws Exception {
        return business.atualizar(id, tipoServicoRequestDTO, userLogado);
    }
}
