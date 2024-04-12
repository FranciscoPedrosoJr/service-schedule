package com.serviceschedule.exception;

public class HorarioDuplicadoException extends RuntimeException {
    public HorarioDuplicadoException(Long id) {
        super("Horário duplicado para o prestador: " + id);
    }
}
