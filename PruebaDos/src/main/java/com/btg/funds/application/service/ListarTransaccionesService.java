package com.btg.funds.application.service;

import com.btg.funds.domain.model.Transaccion;
import com.btg.funds.domain.port.in.ListarTransaccionesUseCase;
import com.btg.funds.domain.port.out.TransaccionRepositoryPort;
import java.util.List;

public class ListarTransaccionesService implements ListarTransaccionesUseCase {
    private final TransaccionRepositoryPort transaccionRepository;

    public ListarTransaccionesService(TransaccionRepositoryPort transaccionRepository) {
        this.transaccionRepository = transaccionRepository;
    }

    @Override
    public List<Transaccion> listarPorCliente(String idCliente) {
        return transaccionRepository.findByClienteIdOrderByFechaDesc(idCliente);
    }
}
