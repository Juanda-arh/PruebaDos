package com.btg.funds.domain.port.out;

import com.btg.funds.domain.model.Fondo;
import java.util.List;
import java.util.Optional;

public interface FondoRepositoryPort {
    Optional<Fondo> findById(String id);

    List<Fondo> findAll();

    boolean isEmpty();

    List<Fondo> saveAll(List<Fondo> fondos);
}
