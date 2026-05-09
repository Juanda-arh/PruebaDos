package com.btg.funds.domain.port.out;

import com.btg.funds.domain.model.Cliente;
import java.util.List;
import java.util.Optional;

public interface ClienteRepositoryPort {
    Optional<Cliente> findById(String id);

    Optional<Cliente> findByEmail(String email);

    boolean existsByEmail(String email);

    Cliente save(Cliente cliente);

    List<Cliente> findAll();
}
