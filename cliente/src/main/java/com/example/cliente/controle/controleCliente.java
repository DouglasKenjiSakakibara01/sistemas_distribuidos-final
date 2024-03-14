package com.example.cliente.controle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.example.cliente.entidade.usuarioCliente;
@RestController
public class controleCliente {
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/")
    public usuarioCliente imovel(@RequestBody usuarioCliente user) {
        HttpEntity<usuarioCliente> requestEntity = new HttpEntity<>(user);
        ResponseEntity<usuarioCliente> responseEntity = restTemplate.exchange("http://cadastro/cadastro/cadastrar", HttpMethod.POST, requestEntity, usuarioCliente.class);

        if(responseEntity.getStatusCode().is2xxSuccessful()) {
            System.out.println("Cadastro realizado: " + responseEntity.getBody());
            return responseEntity.getBody();
        } else {
            System.out.println("Erro no cadastro: " + responseEntity.getStatusCode());
            return null;
        }
    }

}
