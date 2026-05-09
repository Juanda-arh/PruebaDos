package com.btg.funds.domain.exception;

public class ClienteNoEncontradoException extends RuntimeException {
    public ClienteNoEncontradoException(String idCliente) {
        super("Cliente no encontrado: " + idCliente);
    }
}
