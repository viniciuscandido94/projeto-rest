package br.com.rest.projeto.controller;

import br.com.rest.projeto.DTO.requestDTO.FuncionarioRequestDTO;
import br.com.rest.projeto.service.FuncionarioService;
import br.com.rest.projeto.DTO.responseDTO.FuncionarioResponseDTO;
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
@RequestMapping(value="/funcionarios")
public class FuncionarioController {

    @Autowired
    private FuncionarioService service;

    @ApiOperation(value = "Busca Funcionarios da Empresa")
    @GetMapping
    public ResponseEntity<List<FuncionarioResponseDTO>> buscarFuncionariosByEmpresa(@AuthenticationPrincipal User user) throws Exception {
        List<FuncionarioResponseDTO> funcionarioResponseDTOS = service.findByEmpresaId(user.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(funcionarioResponseDTOS);
    }

    @ApiOperation(value = "Cadastra Funcionario")
    @PostMapping
    public ResponseEntity<FuncionarioResponseDTO> cadastrarFuncionario(@Valid @RequestBody FuncionarioRequestDTO funcionarioRequestDTO, @AuthenticationPrincipal User user) throws Exception {
        FuncionarioResponseDTO funcionarioResponseDTO = service.cadastra(funcionarioRequestDTO, user.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(funcionarioResponseDTO);
    }

    @ApiOperation(value = "Altera Funcionario pelo ID")
    @PutMapping(path="/{id}")
    public ResponseEntity<FuncionarioResponseDTO> atualizar(@PathVariable(name = "id", required = true) Long id, @Valid @RequestBody FuncionarioRequestDTO funcionarioRequestDTO, @AuthenticationPrincipal User user) throws Exception {
        FuncionarioResponseDTO funcionarioResponseDTO = service.atualizar(id, funcionarioRequestDTO, user.getUsername());
        return ResponseEntity.ok(funcionarioResponseDTO);
    }

}
