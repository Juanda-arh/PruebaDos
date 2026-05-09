package com.btg.funds.infrastructure.adapter.out.persistence;

import com.btg.funds.domain.model.Cliente;
import com.btg.funds.domain.port.out.ClienteRepositoryPort;
import com.btg.funds.infrastructure.adapter.out.persistence.mapper.ClienteDocumentMapper;
import com.btg.funds.infrastructure.adapter.out.persistence.repository.SpringDataClienteRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class ClienteMongoAdapter implements ClienteRepositoryPort {
    private final SpringDataClienteRepository repository;
    private final ClienteDocumentMapper mapper;

    public ClienteMongoAdapter(SpringDataClienteRepository repository, ClienteDocumentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Cliente> findById(String id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Cliente> findByEmail(String email) {
        return repository.findByEmail(email).map(mapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public Cliente save(Cliente cliente) {
        return mapper.toDomain(repository.save(mapper.toDocument(cliente)));
    }

    @Override
    public List<Cliente> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }
}
