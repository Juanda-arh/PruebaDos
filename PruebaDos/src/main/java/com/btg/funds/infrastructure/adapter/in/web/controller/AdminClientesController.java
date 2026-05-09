package com.btg.funds.infrastructure.adapter.in.web.controller;

import com.btg.funds.domain.port.in.ListarClientesUseCase;
import com.btg.funds.infrastructure.adapter.in.web.dto.ApiResponse;
import com.btg.funds.infrastructure.adapter.in.web.dto.ClienteDto;
import com.btg.funds.infrastructure.adapter.in.web.mapper.WebMapper;
import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/clientes")
public class AdminClientesController {
    private final ListarClientesUseCase listarClientesUseCase;
    private final WebMapper mapper;

    public AdminClientesController(ListarClientesUseCase listarClientesUseCase, WebMapper mapper) {
        this.listarClientesUseCase = listarClientesUseCase;
        this.mapper = mapper;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<ClienteDto>> listar() {
        return ApiResponse.ok(listarClientesUseCase.listar().stream().map(mapper::toDto).toList(),
                "Clientes registrados");
    }
}
