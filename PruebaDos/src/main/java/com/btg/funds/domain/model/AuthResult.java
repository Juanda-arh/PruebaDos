package com.btg.funds.domain.model;

public class AuthResult {
    private String token;
    private Cliente cliente;

    public AuthResult(String token, Cliente cliente) {
        this.token = token;
        this.cliente = cliente;
    }

    public String getToken() {
        return token;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
