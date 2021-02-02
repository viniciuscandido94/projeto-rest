package br.com.rest.projeto.service;

import br.com.rest.projeto.repository.UsuarioRepository;
import br.com.rest.projeto.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.*;

@Service
public class DefaultUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public DefaultUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<Usuario> userEntity = usuarioRepository.findByTelefoneLogin(username);
        if (userEntity.isPresent()) {
            final Usuario appUser = userEntity.get();
            appUser.setUltimoLogin(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            usuarioRepository.save(appUser);
            return new User(appUser.getTelefoneLogin(), appUser.getSenha(), Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
        } else {
            throw new UsernameNotFoundException("Usuario não encontrado na base de dados!");
        }
    }

}