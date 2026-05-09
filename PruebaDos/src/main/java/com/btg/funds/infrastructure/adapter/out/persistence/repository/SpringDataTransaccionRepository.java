package com.btg.funds.infrastructure.adapter.out.persistence.repository;

import com.btg.funds.infrastructure.adapter.out.persistence.document.TransaccionDocument;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataTransaccionRepository extends MongoRepository<TransaccionDocument, String> {
    List<TransaccionDocument> findByIdClienteOrderByFechaDesc(String idCliente);
}
