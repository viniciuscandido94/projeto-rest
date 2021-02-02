package br.com.rest.projeto.controller;

import br.com.rest.projeto.DTO.responseDTO.NCServicoResponseDTO;
import br.com.rest.projeto.service.NCServicoService;
import br.com.rest.projeto.DTO.requestDTO.NCServicoRequestDTO;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value="/servicos")
public class NCServicoController {

      private static Logger logger = LoggerFactory.getLogger(NCServicoController.class);

      @Autowired
      private NCServicoService service;

      @ApiOperation(value = "Busca Servicos e NCs pelo Projeto")
      @GetMapping(path="/projeto/{idProjeto}")
      public ResponseEntity<List<NCServicoResponseDTO>> buscarServicosByProjeto(@PathVariable(name = "idProjeto", required = true) Long idProjeto, Pageable pageable, @AuthenticationPrincipal User userLogado) throws Exception {
          List<NCServicoResponseDTO> ncServicoResponseDTO = service.findByProjetoId(idProjeto, pageable, userLogado.getUsername());
          return ResponseEntity.status(HttpStatus.OK).body(ncServicoResponseDTO);
      }

      @ResponseStatus(HttpStatus.OK)
      @GetMapping(path="/download/projeto/{idProjeto}")
      public void gerarExcelByProjeto(@PathVariable(name = "idProjeto", required = true) Long idProjeto, HttpServletResponse response) throws Exception {
          service.gerarExcelByProjeto(idProjeto, response);
      }

      @ApiOperation(value = "Busca Servico/NC pelo ID e pelo Projeto")
      @GetMapping(path="/{idServico}/projeto/{idProjeto}")
      public ResponseEntity<NCServicoResponseDTO> buscarPorId(@PathVariable(name = "idServico", required = true) Long idServico, @PathVariable(name = "idProjeto", required = true) Long idProjeto, @AuthenticationPrincipal User user) throws Exception {
          NCServicoResponseDTO ncServicoResponseDTO = service.findById(idServico, idProjeto, user.getUsername());
          return ResponseEntity.status(HttpStatus.OK).body(ncServicoResponseDTO);
      }

      @ApiOperation(value="Cadastra Servico/NC")
      @PostMapping
      public ResponseEntity<NCServicoResponseDTO> cadastrarNCServico(@Valid @RequestBody NCServicoRequestDTO ncServicoRequestDTO, @AuthenticationPrincipal User user) throws Exception {
          NCServicoResponseDTO ncServicoResponseDTO = service.cadastra(ncServicoRequestDTO, user.getUsername());
          ResponseEntity<NCServicoResponseDTO> status = ResponseEntity.status(HttpStatus.CREATED).body(ncServicoResponseDTO);
          try {
              service.sendToMessagesApi(ncServicoResponseDTO);
          } catch (Exception e){
              logger.error("Erro ao enviar mensagem para API do whatsApp. Servico/NC com ID: " + ncServicoResponseDTO.getId() + ". Projeto: " + ncServicoResponseDTO.getProjeto().getId() + ". Pelo usuario: " + user + ". Segue o erro -> " + e);
          }
          return status;
      }

      @ApiOperation(value = "Altera Servico/NC pelo ID")
      @PutMapping(path="/{id}")
      public ResponseEntity<NCServicoResponseDTO> atualizar(@PathVariable(name = "id", required = true) Long id, @Valid @RequestBody NCServicoRequestDTO ncServicoRequestDTO, @AuthenticationPrincipal User user) throws Exception {
          NCServicoResponseDTO ncServicoResponseDTO = service.atualizar(id, ncServicoRequestDTO, user.getUsername());
          return ResponseEntity.ok(ncServicoResponseDTO);
      }

}
