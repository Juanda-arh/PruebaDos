package com.btg.funds.infrastructure.adapter.out.persistence.repository;

import com.btg.funds.infrastructure.adapter.out.persistence.document.ClienteDocument;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataClienteRepository extends MongoRepository<ClienteDocument, String> {
    Optional<ClienteDocument> findByEmail(String email);

    boolean existsByEmail(String email);
}
