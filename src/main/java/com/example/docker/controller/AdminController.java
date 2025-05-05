package com.example.docker.controller;

import com.example.docker.domain.Aluno;
import com.example.docker.dto.AlunoCadastrarDTO;
import com.example.docker.mapper.AlunoMapper;
import com.example.docker.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AlunoMapper alunoMapper;

    @Autowired
    private AlunoRepository alunoRepository;

    @PostMapping
    public ResponseEntity<Aluno> inserirAluno(@RequestBody AlunoCadastrarDTO dados){
        return  ResponseEntity.ok().body(alunoRepository.save(alunoMapper.alunoCadastrarDtoToAluno(dados)));
    }
}
