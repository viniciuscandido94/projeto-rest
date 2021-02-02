package br.com.rest.projeto.controller;

import br.com.rest.projeto.DTO.responseDTO.UsuarioResponseDTO;
import br.com.rest.projeto.service.UsuarioService;
import br.com.rest.projeto.DTO.requestDTO.UsuarioRequestDTO;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value="/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping(path="/create")
    public ResponseEntity<UsuarioResponseDTO> createUsuario(@Valid @RequestBody UsuarioRequestDTO usuarioRequestDTO) throws Exception {
        UsuarioResponseDTO usuarioResponseDTO = usuarioService.cadastra(usuarioRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioResponseDTO);
    }

    @ApiOperation(value = "Busca informações do usuário logado")
    @GetMapping
    public ResponseEntity<UsuarioResponseDTO> findUsuario(@AuthenticationPrincipal User user) throws Exception {
        UsuarioResponseDTO usuarioResponseDTOS = usuarioService.findUsuarioByLogin(user.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(usuarioResponseDTOS);
    }
}