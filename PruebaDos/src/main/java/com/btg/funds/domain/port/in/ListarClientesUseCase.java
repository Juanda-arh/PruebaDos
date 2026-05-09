package com.btg.funds.domain.port.in;

import com.btg.funds.domain.model.Cliente;
import java.util.List;

public interface ListarClientesUseCase {
    List<Cliente> listar();
}
