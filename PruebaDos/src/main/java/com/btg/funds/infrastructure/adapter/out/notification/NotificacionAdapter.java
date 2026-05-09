package com.btg.funds.infrastructure.adapter.out.notification;

import com.btg.funds.domain.model.Cliente;
import com.btg.funds.domain.model.Fondo;
import com.btg.funds.domain.model.PreferenciaNotificacion;
import com.btg.funds.domain.port.out.NotificacionPort;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class NotificacionAdapter implements NotificacionPort {
    private static final Logger log = LoggerFactory.getLogger(NotificacionAdapter.class);

    private final Map<PreferenciaNotificacion, NotificacionStrategy> strategies = new EnumMap<>(PreferenciaNotificacion.class);

    public NotificacionAdapter(List<NotificacionStrategy> strategies) {
        strategies.forEach(strategy -> this.strategies.put(strategy.tipo(), strategy));
    }

    @Async
    @Override
    public void notificarSuscripcion(Cliente cliente, Fondo fondo, String mensaje) {
        NotificacionStrategy strategy = strategies.get(cliente.getPreferenciaNotificacion());
        if (strategy == null) {
            log.warn("No hay estrategia de notificación para {}", cliente.getPreferenciaNotificacion());
            return;
        }
        strategy.enviar(cliente, mensaje);
        log.info("Notificación de suscripción enviada para fondo {}", fondo.getNombre());
    }
}
