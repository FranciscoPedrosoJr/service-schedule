package com.serviceschedule.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioRequest {
    private String nome;
    private String email;
    private String password;
    private String role;
}
