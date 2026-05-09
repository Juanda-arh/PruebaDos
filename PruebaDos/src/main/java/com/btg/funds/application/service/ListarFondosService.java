package com.btg.funds.application.service;

import com.btg.funds.domain.model.Fondo;
import com.btg.funds.domain.port.in.ListarFondosUseCase;
import com.btg.funds.domain.port.out.FondoRepositoryPort;
import java.util.List;

public class ListarFondosService implements ListarFondosUseCase {
    private final FondoRepositoryPort fondoRepository;

    public ListarFondosService(FondoRepositoryPort fondoRepository) {
        this.fondoRepository = fondoRepository;
    }

    @Override
    public List<Fondo> listar() {
        return fondoRepository.findAll();
    }
}
