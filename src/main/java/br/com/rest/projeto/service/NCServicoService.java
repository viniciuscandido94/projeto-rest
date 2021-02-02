package br.com.rest.projeto.service;

import br.com.rest.projeto.DTO.requestDTO.NCServicoRequestDTO;
import br.com.rest.projeto.DTO.responseDTO.NCServicoResponseDTO;
import br.com.rest.projeto.auxiliary.FunctionsAuxiliary;
import br.com.rest.projeto.business.NCServicoBusiness;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NCServicoService {

    @Autowired
    private NCServicoBusiness business;

    private static Logger logger = LoggerFactory.getLogger(NCServicoService.class);

    public List<NCServicoResponseDTO> findByProjetoId(Long IdProjeto, Pageable pageable, String userLogado) throws Exception {
        return business.findByProjetoId(IdProjeto, pageable, userLogado);
    }

    public NCServicoResponseDTO findById(Long idServico, Long idProjeto, String userLogado) throws Exception {
        return business.findById(idServico, idProjeto, userLogado);
    }

    @Transactional
    public NCServicoResponseDTO cadastra(NCServicoRequestDTO ncServicoRequestDTO, String userLogado) throws Exception {
        return business.cadastra(ncServicoRequestDTO, userLogado);
    }

    @Transactional
    public NCServicoResponseDTO atualizar(Long id, NCServicoRequestDTO ncServicoRequestDTO, String userLogado) throws Exception {
        return business.atualiza(id, ncServicoRequestDTO, userLogado);
    }

    public void gerarExcelByProjeto(Long idProjeto, HttpServletResponse response) throws Exception {
        business.gerarExcelByProjeto(idProjeto, response);
    }

    public void sendToMessagesApi(NCServicoResponseDTO ncServicoResponseDTO) throws ParseException {
        String mensagemResponsavel = FunctionsAuxiliary.retornaMensagem(ncServicoResponseDTO, "RESPONSAVEL");
        String mensagemEngenheiro = FunctionsAuxiliary.retornaMensagem(ncServicoResponseDTO, "ENGENHEIRO");

        Map<String, String> map = new HashMap<>();
        map.put("contato", ncServicoResponseDTO.getFuncionario().getTelefone());
        map.put("mensagem", mensagemResponsavel);

        Map<String, String> mapEngenheiro = new HashMap<>();
        mapEngenheiro.put("contato", ncServicoResponseDTO.getTelefoneUsuario());
        mapEngenheiro.put("mensagem", mensagemEngenheiro);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForLocation("", map);
        restTemplate.postForLocation("", mapEngenheiro);
    }
}
