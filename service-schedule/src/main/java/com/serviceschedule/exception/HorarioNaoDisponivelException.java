package com.serviceschedule.exception;

public class HorarioNaoDisponivelException extends RuntimeException {
    public HorarioNaoDisponivelException() {
        super("Horário não está disponível." );
    }
}
