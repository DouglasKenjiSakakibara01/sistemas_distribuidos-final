package com.example.cadastro.controle;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.cadastro.entidade.usuario;
import com.example.cadastro.repositorio.usuarioRepositorio;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import java.util.concurrent.CompletableFuture;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.Binding;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@RestController()
@RequestMapping("/cadastro")
public class controleCadastro {
    
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private usuarioRepositorio usuarioRepositorio;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    
    @PostMapping("/cadastrar")
    public void cadastrarUsuario(@RequestBody usuario usuario){ 
        usuario objeto = new usuario(usuario.getNome(), usuario.getCpf(), usuario.getEmail());
        rabbitTemplate.convertAndSend("validacao", objeto);

    }
    @RabbitListener(queues = "cadastro")
    public void efetuarCadastro(usuario mensagem){
        usuarioRepositorio.save(mensagem);
        System.out.println("Usuario Cadastrado com sucesso");
        

    }
    

}
       
    

