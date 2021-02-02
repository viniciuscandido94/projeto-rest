package br.com.rest.projeto.controller;

import br.com.rest.projeto.DTO.responseDTO.UnidadeResponseDTO;
import br.com.rest.projeto.DTO.requestDTO.UnidadeRequestDTO;
import br.com.rest.projeto.service.UnidadeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value="/unidades")
public class UnidadeController {

    @Autowired
    private UnidadeService service;

    @ApiOperation(value = "Busca Unidades pelo Projeto")
    @GetMapping(path="/projeto/{idProjeto}")
    public ResponseEntity<List<UnidadeResponseDTO>> buscarUnidadeByProjeto(@PathVariable(name = "idProjeto", required = true) Long idProjeto, @AuthenticationPrincipal User user) throws Exception {
        List<UnidadeResponseDTO> unidadeResponseDTO = service.findByProjetoId(idProjeto, user.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(unidadeResponseDTO);
    }

    @ApiOperation(value = "Cadastra Unidade")
    @PostMapping
    public ResponseEntity<UnidadeResponseDTO> cadastrarUnidade(@Valid @RequestBody UnidadeRequestDTO unidadeRequestDTO, @AuthenticationPrincipal User user) throws Exception {
        UnidadeResponseDTO unidadeResponseDTO = service.cadastra(unidadeRequestDTO, user.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(unidadeResponseDTO);
    }

    @ApiOperation(value = "Busca Unidade pelo ID")
    @GetMapping(path="/{id}")
    public ResponseEntity<UnidadeResponseDTO> buscarPorId(@PathVariable(name = "id", required = true) Long id, @AuthenticationPrincipal User user) throws Exception {
        UnidadeResponseDTO unidadeResponseDTO = service.findById(id, user.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(unidadeResponseDTO);
    }

    @ApiOperation(value = "Altera Unidade pelo ID")
    @PutMapping(path="/{id}")
    public ResponseEntity<UnidadeResponseDTO> atualizar(@PathVariable(name = "id", required = true) Long id, @Valid @RequestBody UnidadeRequestDTO unidadeRequestDTO, @AuthenticationPrincipal User user) throws Exception {
        UnidadeResponseDTO unidadeResponseDTO = service.atualizar(id, unidadeRequestDTO, user.getUsername());
        return ResponseEntity.ok(unidadeResponseDTO);
    }

}
