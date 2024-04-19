package com.serviceschedule.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class HorarioDisponivelException extends RuntimeException {
    public HorarioDisponivelException() {

        super("{\"error\": \"Desculpe mas parece que este horário já esta disponível\"  }");
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
