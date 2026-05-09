package com.btg.funds.domain.port.in;

import com.btg.funds.domain.model.Fondo;
import java.util.List;

public interface ListarFondosUseCase {
    List<Fondo> listar();
}
