package com.btg.funds.domain.port.out;

import com.btg.funds.domain.model.Cliente;

public interface TokenProviderPort {
    String generarToken(Cliente cliente);
}
