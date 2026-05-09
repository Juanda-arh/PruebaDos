package com.btg.funds.domain.port.in;

import com.btg.funds.domain.model.Transaccion;
import java.util.List;

public interface ListarTransaccionesUseCase {
    List<Transaccion> listarPorCliente(String idCliente);
}
