package br.com.rest.projeto.service;

import br.com.rest.projeto.DTO.requestDTO.UsuarioRequestDTO;
import br.com.rest.projeto.DTO.responseDTO.UsuarioResponseDTO;
import br.com.rest.projeto.business.UsuarioBusiness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioBusiness usuarioBusiness;

    public UsuarioResponseDTO cadastra(UsuarioRequestDTO usuarioRequestDTO) throws Exception {
        return usuarioBusiness.cadastra(usuarioRequestDTO);
    }

    public UsuarioResponseDTO findUsuarioByLogin(String userLogado) throws Exception {
        return usuarioBusiness.findUsuarioByLogin(userLogado);
    }
}
