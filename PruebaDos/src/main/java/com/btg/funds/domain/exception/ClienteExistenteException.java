package com.btg.funds.domain.exception;

public class ClienteExistenteException extends RuntimeException {
    public ClienteExistenteException(String email) {
        super("Ya existe un cliente registrado con el email " + email);
    }
}
