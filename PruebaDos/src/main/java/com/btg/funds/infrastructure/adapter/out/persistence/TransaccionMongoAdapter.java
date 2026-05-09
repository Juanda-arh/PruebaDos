package com.btg.funds.infrastructure.adapter.out.persistence;

import com.btg.funds.domain.model.Transaccion;
import com.btg.funds.domain.port.out.TransaccionRepositoryPort;
import com.btg.funds.infrastructure.adapter.out.persistence.mapper.TransaccionDocumentMapper;
import com.btg.funds.infrastructure.adapter.out.persistence.repository.SpringDataTransaccionRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class TransaccionMongoAdapter implements TransaccionRepositoryPort {
    private final SpringDataTransaccionRepository repository;
    private final TransaccionDocumentMapper mapper;

    public TransaccionMongoAdapter(SpringDataTransaccionRepository repository, TransaccionDocumentMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Transaccion save(Transaccion transaccion) {
        return mapper.toDomain(repository.save(mapper.toDocument(transaccion)));
    }

    @Override
    public List<Transaccion> findByClienteIdOrderByFechaDesc(String idCliente) {
        return repository.findByIdClienteOrderByFechaDesc(idCliente).stream().map(mapper::toDomain).toList();
    }
}
