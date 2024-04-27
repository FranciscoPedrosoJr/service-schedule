package com.serviceschedule.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class PrestadorDuplicadoException extends RuntimeException {

    private final String nomePrestador;

    public PrestadorDuplicadoException(String nomePrestador) {

        super("{\"error\": \"Desculpe mas parece que já existe um prestador com este nome\" : " + nomePrestador + "}");
        this.nomePrestador = nomePrestador;
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
