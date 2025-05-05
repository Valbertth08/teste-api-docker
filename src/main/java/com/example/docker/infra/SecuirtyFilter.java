package com.example.docker.infra;

import com.example.docker.repository.UsuarioReposirotory;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@Component
public class SecuirtyFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioReposirotory repository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token= recuperaToken(request);
        if (token != null && !verificaEmissaoDoTokenKeycloack(token)) {
            //PROCESSA COM MEU FILTRO
            String email = tokenService.pegarUsuarioDoToken(token);
            var usuario = repository.findByLogin(email);
            if (usuario != null) {
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
                System.out.println(authentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
    private boolean verificaEmissaoDoTokenKeycloack(String token) {
        try {
            String[] partes = token.split("\\.");
            if (partes.length != 3) return false;
            String payload = new String(Base64.getUrlDecoder().decode(partes[1]));
            ObjectMapper mapper = new ObjectMapper();
            var body = mapper.readValue(payload, Map.class);

            String issuer = (String) body.get("iss");
            boolean realm_access = body.containsKey("realm_access");
            return (issuer != null && issuer.contains("realms")) || realm_access;
        } catch (Exception e) {
            return false;
        }
    }

    private String recuperaToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        System.out.println(authorizationHeader);
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        } else {
            return null;
        }
    }


}
