package br.com.rest.projeto.business;

import br.com.rest.projeto.DTO.responseDTO.EmpresaResponseDTO;
import br.com.rest.projeto.DTO.responseDTO.UsuarioResponseDTO;
import br.com.rest.projeto.entity.Empresa;
import br.com.rest.projeto.repository.EmpresaRepository;
import br.com.rest.projeto.repository.UsuarioRepository;
import br.com.rest.projeto.DTO.requestDTO.UsuarioRequestDTO;
import br.com.rest.projeto.entity.Usuario;
import br.com.rest.projeto.entity.entityEnum.TipoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsuarioBusiness {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmpresaRepository empresaRepository;

    public UsuarioResponseDTO cadastra(UsuarioRequestDTO usuarioRequestDTO) throws Exception {
        if (!usuarioRequestDTO.getTelefoneLogin().substring(0, 3).equals("+55")){
            throw new Exception("Telefone precisa comecar com o código do Pais. Ex: +55 (Brasil)");
        }

        Optional<Usuario> optionalUsuario = usuarioRepository.findByTelefoneLogin(usuarioRequestDTO.getTelefoneLogin());
        if (optionalUsuario.isPresent()){
            throw new Exception("Usuario com login (Telefone) " + usuarioRequestDTO.getTelefoneLogin() + " ja cadastrado!" );
        }

        Usuario usuario = cadastraUsuario(usuarioRequestDTO, new Usuario());
        return montagemResponse(usuario, new UsuarioResponseDTO());
    }

    public UsuarioResponseDTO findUsuarioByLogin(String userLogado) throws Exception {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByTelefoneLogin(userLogado);
        if (!optionalUsuario.isPresent()){
            throw new Exception("Nao encontrado usuario com user/telefone: " + userLogado);
        }
        return montagemResponse(optionalUsuario.get(), new UsuarioResponseDTO());
    }

    public Usuario findUsuario(String usuario) throws Exception {
        Optional<Usuario> optionalUsuario = usuarioRepository.findByTelefoneLogin(usuario);
        if (!optionalUsuario.isPresent()){
            throw new Exception("Nao encontrado usuario com user/telefone: " + usuario);
        }
        return optionalUsuario.get();
    }

    private Usuario cadastraUsuario(UsuarioRequestDTO usuarioRequestDTO, Usuario usuario) throws Exception {
        Optional<Empresa> optionalEmpresa = empresaRepository.findById(usuarioRequestDTO.getEmpresa());
        if (!optionalEmpresa.isPresent()){
            throw new Exception("Empresa com ID " + usuarioRequestDTO.getEmpresa() +  " nao encontrada!");
        }
        usuario.setNome(usuarioRequestDTO.getNome());
        usuario.setTelefoneLogin(usuarioRequestDTO.getTelefoneLogin());
        usuario.setTipoUsuario(TipoUsuario.valueOf(usuarioRequestDTO.getTipoUsuario()));
        usuario.setUltimoLogin(null);
        usuario.setEmpresa(optionalEmpresa.get());
        usuario.setSenha(passwordEncoder.encode(usuarioRequestDTO.getSenha()));
        usuarioRepository.save(usuario);
        return usuario;
    }

    private UsuarioResponseDTO montagemResponse(Usuario usuario, UsuarioResponseDTO usuarioResponseDTO) {
        usuarioResponseDTO.setId(usuario.getId());
        usuarioResponseDTO.setNome(usuario.getNome());
        usuarioResponseDTO.setTelefoneLogin(usuario.getTelefoneLogin());
        usuarioResponseDTO.setEmpresa(montagemEmpresa(new EmpresaResponseDTO(), usuario));
        usuarioResponseDTO.setTipoUsuario(usuario.getTipoUsuario().name());
        usuarioResponseDTO.setUltimoLogin(usuario.getUltimoLogin());
        return usuarioResponseDTO;
    }

    private EmpresaResponseDTO montagemEmpresa(EmpresaResponseDTO empresaResponseDTO, Usuario usuario) {
        empresaResponseDTO.setId(usuario.getEmpresa().getId());
        empresaResponseDTO.setNomeFantasia(usuario.getEmpresa().getNomeFantasia());
        empresaResponseDTO.setRazaoSocial(usuario.getEmpresa().getRazaoSocial());
        return empresaResponseDTO;
    }
}
