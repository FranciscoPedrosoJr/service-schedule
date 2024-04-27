package com.serviceschedule.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class HorarioDuplicadoException extends RuntimeException {

    private final Long id;

    public HorarioDuplicadoException(Long id) {
        super("{\"error\": \"Horário duplicado para o prestador\" : " + id + "}");
        this.id = id;

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
