package com.btg.funds.application.service;

import com.btg.funds.domain.exception.ClienteNoEncontradoException;
import com.btg.funds.domain.model.Cliente;
import com.btg.funds.domain.model.Suscripcion;
import com.btg.funds.domain.model.TipoTransaccion;
import com.btg.funds.domain.model.Transaccion;
import com.btg.funds.domain.port.in.CancelarSuscripcionUseCase;
import com.btg.funds.domain.port.out.ClienteRepositoryPort;
import com.btg.funds.domain.port.out.TransaccionRepositoryPort;
import java.time.LocalDateTime;
import java.util.UUID;

public class CancelarSuscripcionService implements CancelarSuscripcionUseCase {
    private final ClienteRepositoryPort clienteRepository;
    private final TransaccionRepositoryPort transaccionRepository;

    public CancelarSuscripcionService(ClienteRepositoryPort clienteRepository,
            TransaccionRepositoryPort transaccionRepository) {
        this.clienteRepository = clienteRepository;
        this.transaccionRepository = transaccionRepository;
    }

    @Override
    public Transaccion cancelar(String idCliente, String idFondo) {
        Cliente cliente = clienteRepository.findById(idCliente)
                .orElseThrow(() -> new ClienteNoEncontradoException(idCliente));
        Suscripcion suscripcion = cliente.cancelarSuscripcion(idFondo);
        clienteRepository.save(cliente);

        Transaccion transaccion = new Transaccion(UUID.randomUUID().toString(), cliente.getId(), suscripcion.getIdFondo(),
                suscripcion.getNombreFondo(), TipoTransaccion.CANCELACION, suscripcion.getMontoVinculado(),
                LocalDateTime.now(), cliente.getSaldo());
        return transaccionRepository.save(transaccion);
    }
}
