package com.btg.funds.domain.port.in;

import com.btg.funds.domain.model.Cliente;
public interface RegistrarClienteUseCase {
    Cliente registrar(String email, String password, String nombre, String telefono);
}
