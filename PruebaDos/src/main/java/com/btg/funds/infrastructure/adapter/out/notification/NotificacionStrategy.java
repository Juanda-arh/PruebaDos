package com.btg.funds.infrastructure.adapter.out.notification;

import com.btg.funds.domain.model.Cliente;
import com.btg.funds.domain.model.PreferenciaNotificacion;

public interface NotificacionStrategy {
    PreferenciaNotificacion tipo();

    void enviar(Cliente cliente, String mensaje);
}
