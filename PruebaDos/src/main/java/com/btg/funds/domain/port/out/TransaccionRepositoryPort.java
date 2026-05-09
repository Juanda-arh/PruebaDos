package com.btg.funds.domain.port.out;

import com.btg.funds.domain.model.Transaccion;
import java.util.List;

public interface TransaccionRepositoryPort {
    Transaccion save(Transaccion transaccion);

    List<Transaccion> findByClienteIdOrderByFechaDesc(String idCliente);
}
