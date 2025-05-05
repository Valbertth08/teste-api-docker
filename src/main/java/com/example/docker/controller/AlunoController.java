package com.example.docker.controller;

import com.example.docker.domain.Aluno;
import com.example.docker.dto.AlunoCadastrarDTO;
import com.example.docker.mapper.AlunoMapper;
import com.example.docker.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aluno")
public class AlunoController {

   @GetMapping
    public ResponseEntity<String> mostrarMensagem(){
        return ResponseEntity.ok("controller de usuario comum ");
    }
}
