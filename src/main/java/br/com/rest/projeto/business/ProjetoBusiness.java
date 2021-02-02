package br.com.rest.projeto.business;

import br.com.rest.projeto.DTO.responseDTO.EmpresaResponseDTO;
import br.com.rest.projeto.DTO.responseDTO.ProjetoResponseDTO;
import br.com.rest.projeto.entity.Projeto;
import br.com.rest.projeto.repository.UsuarioRepository;
import br.com.rest.projeto.entity.Usuario;
import br.com.rest.projeto.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ProjetoBusiness {

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<ProjetoResponseDTO> findByEmpresaId(Pageable pageable, String userLogado) throws Exception {
        Usuario usuario = findEmpresaByUsuario(userLogado);
        List<Projeto> projetoList = projetoRepository.findByEmpresaId(usuario.getEmpresa().getId(), pageable);
        List<ProjetoResponseDTO> projetoResponseDTOList = new ArrayList<>();

        projetoList.forEach( projeto -> {
            projetoResponseDTOList.add(new ProjetoResponseDTO(projeto.getId(), projeto.getDescricao()));
        });
        return projetoResponseDTOList;
    }

    private Usuario findEmpresaByUsuario(String usuario) throws Exception {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByTelefoneLogin(usuario);
        if (!optionalUsuario.isPresent()){
            throw new Exception("Nao encontrado usuario atraves do user/telfone: " + usuario);
        }
        return optionalUsuario.get();
    }

    private EmpresaResponseDTO montagemEmpresaResponse(EmpresaResponseDTO empresaResponseDTO, Usuario usuario) {
        empresaResponseDTO.setId(usuario.getEmpresa().getId());
        empresaResponseDTO.setNomeFantasia(usuario.getEmpresa().getNomeFantasia());
        empresaResponseDTO.setRazaoSocial(usuario.getEmpresa().getRazaoSocial());
        return empresaResponseDTO;
    }

    public Projeto findProjeto(Long idProjeto) throws Exception {
        Optional<Projeto> optionalProjeto = projetoRepository.findById(idProjeto);
        if (!optionalProjeto.isPresent()){
            throw new Exception("Nao encontrado projeto com ID: " + idProjeto);
        }
        return optionalProjeto.get();
    }
}
