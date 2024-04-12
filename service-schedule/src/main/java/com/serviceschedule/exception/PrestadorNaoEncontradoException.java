package com.serviceschedule.exception;

public class PrestadorNaoEncontradoException extends RuntimeException {

    public PrestadorNaoEncontradoException(Long id) {
        super("Prestador não encontrado com o ID: " + id);

    }
}
