package com.serviceschedule.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PrestadorNaoEncontradoException extends RuntimeException {

    private final Long id;

    public PrestadorNaoEncontradoException(Long id) {
        
        super("{\"error\": \"Prestador não encontrado com o ID\" : " + id + "}");
        this.id = id;
    }

    public Long getId() {
        return id;
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
