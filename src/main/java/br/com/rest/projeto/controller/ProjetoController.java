package br.com.rest.projeto.controller;

import br.com.rest.projeto.DTO.responseDTO.ProjetoResponseDTO;
import br.com.rest.projeto.service.ProjetoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/projetos")
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;

    @ApiOperation(value="Busca projetos pela Empresa")
    @GetMapping
    public ResponseEntity<List<ProjetoResponseDTO>> buscarProjetos(Pageable pageable, @AuthenticationPrincipal User user) throws Exception {
        List<ProjetoResponseDTO> ncServicoResponseDTO = projetoService.findByEmpresaId(pageable, user.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(ncServicoResponseDTO);
    }
}
