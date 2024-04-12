package com.serviceschedule.exception;

public class HorarioDisponivelException extends RuntimeException {
    public HorarioDisponivelException() {
        super("Horário já esta disponível" );
    }
}
