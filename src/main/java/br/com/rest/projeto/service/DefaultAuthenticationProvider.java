package br.com.rest.projeto.service;

import br.com.rest.projeto.repository.UsuarioRepository;
import br.com.rest.projeto.entity.Usuario;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collections;
import java.util.Optional;

public class DefaultAuthenticationProvider implements AuthenticationProvider {

    private final UsuarioRepository usuarioRepository;

    public DefaultAuthenticationProvider(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public Authentication authenticate(final Authentication authentication) throws AuthenticationException {

        if (authentication.getName() == null || authentication.getCredentials() == null) {
            return null;
        }

        if (authentication.getName().isEmpty() || authentication.getCredentials().toString().isEmpty()) {
            return null;
        }

        final Optional<Usuario> appUser = this.usuarioRepository.findByTelefoneLogin(authentication.getName());

        if (appUser.isPresent()) {
            final Usuario user = appUser.get();
            final String providedUserTelefone = authentication.getName();
            final Object providedUserPassword = authentication.getCredentials();

            if (providedUserTelefone.equalsIgnoreCase(user.getTelefoneLogin())
                    && providedUserPassword.equals(user.getSenha())) {
                return new UsernamePasswordAuthenticationToken(
                        user.getTelefoneLogin(),
                        user.getSenha(),
                        Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")));
            }
        }

        throw new UsernameNotFoundException("Invalid username or password.");
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}