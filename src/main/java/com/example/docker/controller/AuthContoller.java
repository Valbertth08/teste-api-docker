package com.example.docker.controller;

import com.example.docker.domain.Aluno;
import com.example.docker.domain.Usuario;
import com.example.docker.dto.AlunoCadastrarDTO;
import com.example.docker.dto.LoginDTO;
import com.example.docker.infra.TokenService;
import com.example.docker.repository.UsuarioReposirotory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthContoller {
    @Autowired
    private TokenService tokenService;
    @Autowired
    private UsuarioReposirotory usuarioReposirotory;

    //errado
    @PostMapping("/login")
    public ResponseEntity<String> efetuarLogin(@RequestBody LoginDTO loginRequest){
        System.out.println("chegou no controller");
        var email = loginRequest.email();
        UserDetails usuario = usuarioReposirotory.findByLogin(email);
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Usuário não encontrado");
        }
        var tokenJWT = tokenService.gerarToken((Usuario) usuario);
        return ResponseEntity.ok(tokenJWT);
    }
}
