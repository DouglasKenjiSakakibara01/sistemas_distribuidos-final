package com.example.cadastro.repositorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.cadastro.entidade.usuario;

import org.springframework.context.annotation.Bean;

@Repository
public interface usuarioRepositorio extends JpaRepository<usuario, Long> {
    
}