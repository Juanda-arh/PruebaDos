package com.btg.funds.application.service;

import com.btg.funds.domain.model.Cliente;
import com.btg.funds.domain.port.in.ListarClientesUseCase;
import com.btg.funds.domain.port.out.ClienteRepositoryPort;
import java.util.List;

public class ListarClientesService implements ListarClientesUseCase {
    private final ClienteRepositoryPort clienteRepository;

    public ListarClientesService(ClienteRepositoryPort clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public List<Cliente> listar() {
        return clienteRepository.findAll();
    }
}
