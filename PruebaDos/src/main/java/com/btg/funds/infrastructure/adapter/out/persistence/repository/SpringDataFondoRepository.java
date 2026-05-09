package com.btg.funds.infrastructure.adapter.out.persistence.repository;

import com.btg.funds.infrastructure.adapter.out.persistence.document.FondoDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataFondoRepository extends MongoRepository<FondoDocument, String> {
}
