package com.example.validacao.controle;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import com.example.cadastro.entidade.*;
/*
@RestController()
@RequestMapping("/validacao")
class controleValidacao { 
    
    public static boolean validarCPF(String cpf) {
        
        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11) {
            return false;
        }

        // calcula o primeiro dígito verificador
        int soma= 0;
        for (int i = 0; i < 9; i++) {
            soma+= Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int resto = soma% 11;
        int digito1 = (resto < 2) ? 0 : (11 - resto);

        // calcula o segundo dígito verificador
        soma= 0;
        for (int i = 0; i < 10; i++) {
            soma+= Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        resto = soma% 11;
        int digito2 = (resto < 2) ? 0 : (11 - resto);

        // verifica se os dígitos verificadores estão corretos
        return (Character.getNumericValue(cpf.charAt(9)) == digito1) && (Character.getNumericValue(cpf.charAt(10)) == digito2);
    }
    @Autowired
    private RestTemplate restTemplate;
    
    @GetMapping("/teste")
    public String retorno() {
        return "Teste de realizado com sucesso";
    }
    
    
    @PostMapping("/validar")
    public ResponseEntity<String> validarUsuario(@RequestBody usuario usuario) {
        // Simulando a validação (verifica se o e-mail não está vazio)
        if(validarCPF(usuario.getCpf())) {
            return ResponseEntity.ok("Validação bem-sucedida");
        } 
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CPF inválido");

        }
    }
}
*/


@Component
class controleValidacao { 
    
    public static boolean validarCPF(String cpf) {
        
        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11) {
            return false;
        }

        // calcula o primeiro dígito verificador
        int soma= 0;
        for (int i = 0; i < 9; i++) {
            soma+= Character.getNumericValue(cpf.charAt(i)) * (10 - i);
        }
        int resto = soma% 11;
        int digito1 = (resto < 2) ? 0 : (11 - resto);

        // calcula o segundo dígito verificador
        soma= 0;
        for (int i = 0; i < 10; i++) {
            soma+= Character.getNumericValue(cpf.charAt(i)) * (11 - i);
        }
        resto = soma% 11;
        int digito2 = (resto < 2) ? 0 : (11 - resto);

        // verifica se os dígitos verificadores estão corretos
        return (Character.getNumericValue(cpf.charAt(9)) == digito1) && (Character.getNumericValue(cpf.charAt(10)) == digito2);
    }
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;
    /*
    @Autowired
    private controleCadastro cadastro;
*/
    
    @RabbitListener(queues = "validacao")
    public void validarUsuario(usuario mensagem) {
        System.out.println(mensagem);
        if(validarCPF(mensagem.getCpf())) {
            System.out.println("Cadastro validado");
            usuario objeto = new usuario(mensagem.getNome(), mensagem.getCpf(), mensagem.getEmail());
            rabbitTemplate.convertAndSend("cadastro", objeto);
            
        } 
        else {
            System.out.println("Cpf invalido");

        }
        
    }
}