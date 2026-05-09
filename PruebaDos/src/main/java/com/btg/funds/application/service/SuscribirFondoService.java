package com.btg.funds.application.service;

import com.btg.funds.domain.exception.ClienteNoEncontradoException;
import com.btg.funds.domain.exception.FondoNoEncontradoException;
import com.btg.funds.domain.model.Cliente;
import com.btg.funds.domain.model.Fondo;
import com.btg.funds.domain.model.PreferenciaNotificacion;
import com.btg.funds.domain.model.TipoTransaccion;
import com.btg.funds.domain.model.Transaccion;
import com.btg.funds.domain.port.in.SuscribirFondoUseCase;
import com.btg.funds.domain.port.out.ClienteRepositoryPort;
import com.btg.funds.domain.port.out.FondoRepositoryPort;
import com.btg.funds.domain.port.out.NotificacionPort;
import com.btg.funds.domain.port.out.TransaccionRepositoryPort;
import java.time.LocalDateTime;
import java.util.UUID;

public class SuscribirFondoService implements SuscribirFondoUseCase {
    private final ClienteRepositoryPort clienteRepository;
    private final FondoRepositoryPort fondoRepository;
    private final TransaccionRepositoryPort transaccionRepository;
    private final NotificacionPort notificacionPort;

    public SuscribirFondoService(ClienteRepositoryPort clienteRepository, FondoRepositoryPort fondoRepository,
            TransaccionRepositoryPort transaccionRepository, NotificacionPort notificacionPort) {
        this.clienteRepository = clienteRepository;
        this.fondoRepository = fondoRepository;
        this.transaccionRepository = transaccionRepository;
        this.notificacionPort = notificacionPort;
    }

    @Override
    public Transaccion suscribir(String idCliente, String idFondo, PreferenciaNotificacion preferenciaNotificacion) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ClienteNoEncontradoException(idCliente));
        Fondo fondo = fondoRepository.findById(idFondo).orElseThrow(() -> new FondoNoEncontradoException(idFondo));
        cliente.setPreferenciaNotificacion(preferenciaNotificacion);

        String idTransaccion = UUID.randomUUID().toString();
        LocalDateTime fecha = LocalDateTime.now();
        cliente.suscribir(fondo, idTransaccion, fecha);

        clienteRepository.save(cliente);
        Transaccion transaccion = new Transaccion(idTransaccion, cliente.getId(), fondo.getId(), fondo.getNombre(),
                TipoTransaccion.APERTURA, fondo.getMontoMinimo(), fecha, cliente.getSaldo());
        Transaccion guardada = transaccionRepository.save(transaccion);
        notificacionPort.notificarSuscripcion(cliente, fondo,
                "Suscripción realizada al fondo " + fondo.getNombre() + " por COP " + fondo.getMontoMinimo());
        return guardada;
    }
}
