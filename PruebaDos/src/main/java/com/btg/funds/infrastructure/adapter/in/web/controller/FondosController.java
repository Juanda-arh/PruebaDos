package com.btg.funds.infrastructure.adapter.in.web.controller;

import com.btg.funds.domain.port.in.ListarFondosUseCase;
import com.btg.funds.infrastructure.adapter.in.web.dto.ApiResponse;
import com.btg.funds.infrastructure.adapter.in.web.dto.FondoDto;
import com.btg.funds.infrastructure.adapter.in.web.mapper.WebMapper;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/fondos")
public class FondosController {
    private final ListarFondosUseCase listarFondosUseCase;
    private final WebMapper mapper;

    public FondosController(ListarFondosUseCase listarFondosUseCase, WebMapper mapper) {
        this.listarFondosUseCase = listarFondosUseCase;
        this.mapper = mapper;
    }

    @GetMapping
    public ApiResponse<List<FondoDto>> listar() {
        return ApiResponse.ok(listarFondosUseCase.listar().stream().map(mapper::toDto).toList(), "Fondos disponibles");
    }
}
