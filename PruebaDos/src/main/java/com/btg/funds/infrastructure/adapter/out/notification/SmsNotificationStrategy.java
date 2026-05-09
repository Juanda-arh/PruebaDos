package com.btg.funds.infrastructure.adapter.out.notification;

import com.btg.funds.domain.model.Cliente;
import com.btg.funds.domain.model.PreferenciaNotificacion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SmsNotificationStrategy implements NotificacionStrategy {
    private static final Logger log = LoggerFactory.getLogger(SmsNotificationStrategy.class);

    @Override
    public PreferenciaNotificacion tipo() {
        return PreferenciaNotificacion.SMS;
    }

    @Override
    public void enviar(Cliente cliente, String mensaje) {
        log.info("SMS enviado a {}: {}", cliente.getTelefono(), mensaje);
    }
}
