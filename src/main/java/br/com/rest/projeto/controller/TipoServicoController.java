package br.com.rest.projeto.controller;

import br.com.rest.projeto.service.TipoServicoService;
import br.com.rest.projeto.DTO.requestDTO.TipoServicoRequestDTO;
import br.com.rest.projeto.DTO.responseDTO.TipoServicoResponseDTO;
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
@RequestMapping(value="/tiposervicos")
public class TipoServicoController {

    @Autowired
    private TipoServicoService service;

    @ApiOperation(value = "Busca os Tipos de Servicos pelo Projeto")
    @GetMapping(path="/projeto/{idProjeto}")
    public ResponseEntity<List<TipoServicoResponseDTO>> buscarTipoServicoByProjeto(@PathVariable(name = "idProjeto", required = true) Long idProjeto, @AuthenticationPrincipal User user) throws Exception {
        List<TipoServicoResponseDTO> tipoServicoResponseDTO = service.findByProjetoId(idProjeto, user.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(tipoServicoResponseDTO);
    }

    @ApiOperation(value = "Cadastra Tipo de Servico")
    @PostMapping
    public ResponseEntity<TipoServicoResponseDTO> cadastrarTipoServico(@Valid @RequestBody TipoServicoRequestDTO tipoServicoRequestDTO, @AuthenticationPrincipal User user) throws Exception {
        TipoServicoResponseDTO tipoServicoResponseDTO = service.cadastra(tipoServicoRequestDTO, user.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(tipoServicoResponseDTO);
    }

    @ApiOperation(value = "Busca Tipo de Servico pelo ID")
    @GetMapping(path="/{id}")
    public ResponseEntity<TipoServicoResponseDTO> buscarPorId(@PathVariable(name = "id", required = true) Long id, @AuthenticationPrincipal User user) throws Exception {
        TipoServicoResponseDTO tipoServicoResponseDTO = service.findById(id, user.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(tipoServicoResponseDTO);
    }

    @ApiOperation(value = "Altera Tipo de Servico pelo ID")
    @PutMapping(path="/{id}")
    public ResponseEntity<TipoServicoResponseDTO> atualizar(@PathVariable(name = "id", required = true) Long id, @Valid @RequestBody TipoServicoRequestDTO tipoServicoRequestDTO, @AuthenticationPrincipal User user) throws Exception {
        TipoServicoResponseDTO tipoServicoResponseDTO = service.atualizar(id, tipoServicoRequestDTO, user.getUsername());
        return ResponseEntity.ok(tipoServicoResponseDTO);
    }
}
