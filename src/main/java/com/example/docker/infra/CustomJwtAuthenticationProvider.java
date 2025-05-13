package com.example.docker.infra;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.docker.domain.Usuario;
import com.example.docker.repository.UsuarioReposirotory;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;

@Component
public class CustomJwtAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UsuarioReposirotory usuarioReposirotory;

    @Autowired
    private TokenService tokenService;

    @Value("${jwt.secret}")
    private String secret;

    private final JwtDecoder keycloakDecoder;

    public CustomJwtAuthenticationProvider(@Value("${keycloak.issuer-uri}") String issuerUri) {
        this.keycloakDecoder = JwtDecoders.fromIssuerLocation(issuerUri);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getCredentials().toString();
        System.out.println(token);
        if (token.startsWith("Bearer ")) {
            System.out.println("chegou aqui");
            token = token.substring(7);
            System.out.println(token);
        }

        // Tenta decodificar com Keycloak
        try {
            Jwt jwt = keycloakDecoder.decode(token);
            Collection<GrantedAuthority> authorities = new ConversorKeyCloak().convert(jwt);
            System.out.println(authorities);
            return new JwtAuthenticationToken(jwt, authorities);
        } catch (JwtException e) {
            // fallback
        }

        // Tenta decodificar JWT customizado
        try {

            DecodedJWT decoded = JWT.require(Algorithm.HMAC256(secret))
                    .withIssuer("API barberia")
                    .build()
                    .verify(token);

            String username = decoded.getSubject();
            Usuario usuario = (Usuario) usuarioReposirotory.findByLogin(username);
            return new UsernamePasswordAuthenticationToken(username, token, usuario.getAuthorities());

        } catch (JwtException e) {
            throw new BadCredentialsException("Token inválido");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return BearerTokenAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
