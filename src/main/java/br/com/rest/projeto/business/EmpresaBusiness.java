package br.com.rest.projeto.business;

import br.com.rest.projeto.repository.UsuarioRepository;
import br.com.rest.projeto.entity.Projeto;
import br.com.rest.projeto.entity.Usuario;
import br.com.rest.projeto.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class EmpresaBusiness {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioBusiness usuarioBusiness;

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private ProjetoBusiness projetoBusiness;

    public void empresaValida(Long idProjeto, String userLogado) throws Exception {
        Usuario usuario = usuarioBusiness.findUsuario(userLogado);
        Projeto projeto = projetoBusiness.findProjeto(idProjeto);
        if (!usuario.getEmpresa().getId().equals(projeto.getEmpresa().getId())){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
}
