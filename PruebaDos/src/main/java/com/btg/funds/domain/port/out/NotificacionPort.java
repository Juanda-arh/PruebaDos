package com.btg.funds.domain.port.out;

import com.btg.funds.domain.model.Cliente;
import com.btg.funds.domain.model.Fondo;

public interface NotificacionPort {
    void notificarSuscripcion(Cliente cliente, Fondo fondo, String mensaje);
}
