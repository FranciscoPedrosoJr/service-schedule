package com.serviceschedule.controller;

import com.serviceschedule.dto.UsuarioRequest;
import com.serviceschedule.model.Usuario;
import com.serviceschedule.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody UsuarioRequest usuarioRequest) {
        final Usuario novoUsuario = usuarioService.cadastrarUsuario(
                usuarioRequest.getNome(),
                usuarioRequest.getEmail(),
                usuarioRequest.getPassword(),
                usuarioRequest.getRole()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(novoUsuario);
    }

}
