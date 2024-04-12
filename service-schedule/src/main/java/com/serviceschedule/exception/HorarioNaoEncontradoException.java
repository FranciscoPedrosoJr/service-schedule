package com.serviceschedule.exception;

public class HorarioNaoEncontradoException extends RuntimeException {
    public HorarioNaoEncontradoException(Long id) {
        super("Horário não encontrado para o prestador com ID: " + id);
    }
}
