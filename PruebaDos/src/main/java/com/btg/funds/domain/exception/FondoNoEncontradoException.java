package com.btg.funds.domain.exception;

public class FondoNoEncontradoException extends RuntimeException {
    public FondoNoEncontradoException(String idFondo) {
        super("Fondo no encontrado: " + idFondo);
    }
}
