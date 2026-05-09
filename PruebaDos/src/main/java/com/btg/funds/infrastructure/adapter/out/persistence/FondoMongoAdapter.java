package com.btg.funds.infrastructure.adapter.out.persistence;

import com.btg.funds.domain.model.Fondo;
import com.btg.funds.domain.port.out.FondoRepositoryPort;
import com.btg.funds.infrastructure.adapter.out.persistence.mapper.FondoDocumentMapper;
import com.btg.funds.infrastructure.adapter.out.persistence.repository.SpringDataFondoRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class FondoMongoAdapter implements FondoRepositoryPort {
    private final SpringDataFondoRepository repository;
    private final FondoDocumentMapper mapper;

    public FondoMongoAdapter(SpringDataFondoRepository repository, FondoDocumentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Fondo> findById(String id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Fondo> findAll() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public boolean isEmpty() {
        return repository.count() == 0;
    }

    @Override
    public List<Fondo> saveAll(List<Fondo> fondos) {
        return repository.saveAll(fondos.stream().map(mapper::toDocument).toList())
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
