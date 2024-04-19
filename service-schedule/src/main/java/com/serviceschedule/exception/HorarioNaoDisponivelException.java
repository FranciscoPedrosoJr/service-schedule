package com.serviceschedule.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class HorarioNaoDisponivelException extends RuntimeException {
    public HorarioNaoDisponivelException() {

        super("{\"error\": \"Desculpe mas parece que este horário não esta disponivel\"  }");

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
