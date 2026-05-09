package com.btg.funds.domain.port.in;

import com.btg.funds.domain.model.Transaccion;
import com.btg.funds.domain.model.PreferenciaNotificacion;

public interface SuscribirFondoUseCase {
    Transaccion suscribir(String idCliente, String idFondo, PreferenciaNotificacion preferenciaNotificacion);
}
