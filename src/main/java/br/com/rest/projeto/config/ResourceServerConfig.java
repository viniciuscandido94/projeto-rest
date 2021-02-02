package br.com.rest.projeto.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "resource_id";

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(false);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
            .and().csrf().disable()
            .authorizeRequests().antMatchers("/oauth/token",
                                             "/fotos/**",
                                             "/planilhas/**",
                                             "/servicos/download/projeto/**",
                                             "/swagger-ui.html",
                                             "/v2/api-docs",
                                             "/configuration/ui",
                                             "/swagger-resources/**",
                                             "/configuration/security",
                                             "/webjars/**").permitAll()
            .antMatchers(HttpMethod.POST, "/usuario").permitAll()
            .anyRequest().authenticated()
            .and().exceptionHandling().accessDeniedHandler(new OAuth2AccessDeniedHandler());
    }
}