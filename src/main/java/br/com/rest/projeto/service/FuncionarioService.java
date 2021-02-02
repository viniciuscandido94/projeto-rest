package br.com.rest.projeto.service;

import br.com.rest.projeto.DTO.requestDTO.FuncionarioRequestDTO;
import br.com.rest.projeto.DTO.responseDTO.FuncionarioResponseDTO;
import br.com.rest.projeto.business.FuncionarioBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioBusiness business;

    public List<FuncionarioResponseDTO> findByEmpresaId(String userLogado) throws Exception {
        return business.findByEmpresaId(userLogado);
    }

    @Transactional
    public FuncionarioResponseDTO cadastra(FuncionarioRequestDTO funcionarioRequestDTO, String userLogado) throws Exception {
        return business.cadastra(funcionarioRequestDTO, userLogado);
    }

    @Transactional
    public FuncionarioResponseDTO atualizar(Long id, FuncionarioRequestDTO funcionarioRequestDTO, String userLogado) throws Exception {
        return business.atualizar(id, funcionarioRequestDTO, userLogado);
    }
}
