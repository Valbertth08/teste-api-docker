package com.example.docker.repository;

import com.example.docker.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioReposirotory extends JpaRepository<Usuario, Long> {
    UserDetails findByLogin(String username);
}
