package br.com.rest.projeto.controller;

import br.com.rest.projeto.service.PavimentoService;
import br.com.rest.projeto.DTO.requestDTO.PavimentoRequestDTO;
import br.com.rest.projeto.DTO.responseDTO.PavimentoResponseDTO;
import io.swagger.annotations.Api;
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
@Api(value = "Pavimentos")
@RequestMapping(value="/pavimentos")
public class PavimentoController {

    @Autowired
    private PavimentoService service;

    @ApiOperation(value = "Busca Pavimentos pelo Projeto")
    @GetMapping(path="/projeto/{idProjeto}")
    public ResponseEntity<List<PavimentoResponseDTO>> buscarPavimentoByProjeto(@PathVariable(name = "idProjeto", required = true) Long idProjeto, @AuthenticationPrincipal User user) throws Exception {
        List<PavimentoResponseDTO> pavimentoResponseDTO = service.findByProjetoId(idProjeto, user.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(pavimentoResponseDTO);
    }

    @ApiOperation(value = "Cadastra Pavimento")
    @PostMapping
    public ResponseEntity<PavimentoResponseDTO> cadastrarPavimento(@Valid @RequestBody PavimentoRequestDTO pavimentoRequestDTO, @AuthenticationPrincipal User user) throws Exception {
        PavimentoResponseDTO pavimentoResponseDTO = service.cadastra(pavimentoRequestDTO, user.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(pavimentoResponseDTO);
    }

    @ApiOperation(value = "Busca Pavimento pelo ID")
    @GetMapping(path="/{id}")
    public ResponseEntity<PavimentoResponseDTO> buscarPorId(@PathVariable(name = "id", required = true) Long id, @AuthenticationPrincipal User user) throws Exception {
        PavimentoResponseDTO pavimentoResponseDTO = service.findById(id, user.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(pavimentoResponseDTO);
    }

    @ApiOperation(value = "Altera Pavimento pelo ID")
    @PutMapping(path="/{id}")
    public ResponseEntity<PavimentoResponseDTO> atualizar(@PathVariable(name = "id", required = true) Long id, @Valid @RequestBody PavimentoRequestDTO pavimentoRequestDTO, @AuthenticationPrincipal User user) throws Exception {
        PavimentoResponseDTO pavimentoResponseDTO = service.atualizar(id, pavimentoRequestDTO, user.getUsername());
        return ResponseEntity.ok(pavimentoResponseDTO);
    }
}
