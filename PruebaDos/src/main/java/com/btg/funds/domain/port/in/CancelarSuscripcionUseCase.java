package com.btg.funds.domain.port.in;

import com.btg.funds.domain.model.Transaccion;

public interface CancelarSuscripcionUseCase {
    Transaccion cancelar(String idCliente, String idFondo);
}
