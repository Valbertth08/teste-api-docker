package com.example.docker.controller;

import com.example.docker.domain.Aluno;
import com.example.docker.domain.Usuario;
import com.example.docker.dto.AlunoCadastrarDTO;
import com.example.docker.dto.CadastrarUsuarioDTO;
import com.example.docker.mapper.AlunoMapper;
import com.example.docker.repository.AlunoRepository;
import com.example.docker.repository.UsuarioReposirotory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioReposirotory usuarioReposirotory;

    @PostMapping
    public ResponseEntity<String> inserirUsuario(@RequestBody CadastrarUsuarioDTO dados){
        usuarioReposirotory.save(new Usuario(dados.email()));
        return  ResponseEntity.ok().body("usuario cadastrado");
    }

}
