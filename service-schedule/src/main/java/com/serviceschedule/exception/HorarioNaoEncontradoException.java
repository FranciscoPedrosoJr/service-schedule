package com.serviceschedule.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class HorarioNaoEncontradoException extends RuntimeException {

    private final Long id;

    public HorarioNaoEncontradoException(Long id) {

        super("{\"error\": \"Horário não encontrado para o prestador com ID\" : " + id + "}");
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
