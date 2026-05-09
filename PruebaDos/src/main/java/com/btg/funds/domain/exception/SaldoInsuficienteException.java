package com.btg.funds.domain.exception;

public class SaldoInsuficienteException extends RuntimeException {
    public SaldoInsuficienteException(String nombreFondo) {
        super("No tiene saldo disponible para vincularse al fondo " + nombreFondo);
    }
}
