package com.example.docker;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("teste")
public class HellowDocker {

    @GetMapping
    public ResponseEntity<String> ola(){
        return  ResponseEntity.ok().body("ola docker");

    }
}
