package com.btg.funds.infrastructure.adapter.in.web.controller;

import com.btg.funds.domain.port.in.ListarTransaccionesUseCase;
import com.btg.funds.infrastructure.adapter.in.web.dto.ApiResponse;
import com.btg.funds.infrastructure.adapter.in.web.dto.TransaccionDto;
import com.btg.funds.infrastructure.adapter.in.web.mapper.WebMapper;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transacciones")
public class TransaccionesController extends AbstractAuthenticatedController {
    private final ListarTransaccionesUseCase listarTransaccionesUseCase;
    private final WebMapper mapper;

    public TransaccionesController(ListarTransaccionesUseCase listarTransaccionesUseCase, WebMapper mapper) {
        this.listarTransaccionesUseCase = listarTransaccionesUseCase;
        this.mapper = mapper;
    }

    @GetMapping
    public ApiResponse<List<TransaccionDto>> listar(Authentication authentication) {
        return ApiResponse.ok(listarTransaccionesUseCase.listarPorCliente(currentClienteId(authentication))
                .stream().map(mapper::toDto).toList(), "Historial de transacciones");
    }
}
