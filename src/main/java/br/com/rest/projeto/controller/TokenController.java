package br.com.rest.projeto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Optional.ofNullable;

@RestController
@RequestMapping(value="/oauth")
public class TokenController {

    @Autowired
    private TokenStore tokenStore;

    @DeleteMapping(path="/revoke")
    @ResponseStatus(HttpStatus.OK)
    public void revokeToken(Authentication authentication) {
        ofNullable(authentication).ifPresent(auth -> {
            OAuth2AccessToken accessToken = tokenStore.getAccessToken((OAuth2Authentication) auth);

            ofNullable(accessToken).ifPresent(oAuth2AccessToken -> {
                ofNullable(oAuth2AccessToken.getRefreshToken()).ifPresent(tokenStore::removeRefreshToken);
                tokenStore.removeAccessToken(accessToken);
            });
        });
    }
}