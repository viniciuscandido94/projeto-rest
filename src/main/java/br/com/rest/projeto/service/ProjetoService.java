package br.com.rest.projeto.service;

import br.com.rest.projeto.DTO.responseDTO.ProjetoResponseDTO;
import br.com.rest.projeto.business.ProjetoBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjetoService {

    @Autowired
    private ProjetoBusiness projetoBusiness;

    public List<ProjetoResponseDTO> findByEmpresaId(Pageable pageable, String userLogado) throws Exception {
        return projetoBusiness.findByEmpresaId(pageable, userLogado);
    }
}
