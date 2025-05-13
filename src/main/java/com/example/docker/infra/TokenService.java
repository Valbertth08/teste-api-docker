package com.example.docker.infra;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.docker.domain.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    public String gerarToken(Usuario usuario){
        try {
            var algortimo= Algorithm.HMAC256(secret);
            return JWT.create()
                    .withIssuer("API barberia")
                    .withSubject(usuario.getLogin())
                    .withClaim("id",usuario.getId())
                    .withExpiresAt(dataExpiracaoToken())
                    .sign(algortimo);
        }catch (JWTCreationException exception){
            throw new RuntimeException("erro ao gerar o token",exception);
        }

    }
    public String pegarUsuarioDoToken(String token){
        System.out.println("chegou aqui no token service validador");
        try{
            var algoritimo= Algorithm.HMAC256(secret);
            return JWT.require(algoritimo)
                    .withIssuer("API barberia")
                    .build()
                    .verify(token)
                    .getSubject();
        }catch (JWTVerificationException exception){
            throw new RuntimeException("Token invalido ou expirado",exception);
        }

    }
    private Instant dataExpiracaoToken() {
        return LocalDateTime.now().plusDays(1).toInstant(ZoneOffset.of("-03:00"));

    }

}
