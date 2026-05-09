package com.btg.funds.infrastructure.adapter.in.web.controller;

import com.btg.funds.domain.exception.ClienteNoEncontradoException;
import com.btg.funds.domain.model.Cliente;
import com.btg.funds.domain.port.in.CancelarSuscripcionUseCase;
import com.btg.funds.domain.port.in.SuscribirFondoUseCase;
import com.btg.funds.domain.port.out.ClienteRepositoryPort;
import com.btg.funds.infrastructure.adapter.in.web.dto.ApiResponse;
import com.btg.funds.infrastructure.adapter.in.web.dto.SuscribirFondoRequest;
import com.btg.funds.infrastructure.adapter.in.web.dto.SuscripcionDto;
import com.btg.funds.infrastructure.adapter.in.web.dto.TransaccionDto;
import com.btg.funds.infrastructure.adapter.in.web.mapper.WebMapper;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/suscripciones")
public class SuscripcionesController extends AbstractAuthenticatedController {
    private final SuscribirFondoUseCase suscribirFondoUseCase;
    private final CancelarSuscripcionUseCase cancelarSuscripcionUseCase;
    private final ClienteRepositoryPort clienteRepository;
    private final WebMapper mapper;

    public SuscripcionesController(SuscribirFondoUseCase suscribirFondoUseCase,
            CancelarSuscripcionUseCase cancelarSuscripcionUseCase, ClienteRepositoryPort clienteRepository,
            WebMapper mapper) {
        this.suscribirFondoUseCase = suscribirFondoUseCase;
        this.cancelarSuscripcionUseCase = cancelarSuscripcionUseCase;
        this.clienteRepository = clienteRepository;
        this.mapper = mapper;
    }

    @PostMapping
    public ApiResponse<TransaccionDto> suscribir(@Valid @RequestBody SuscribirFondoRequest request,
            Authentication authentication) {
        return ApiResponse.ok(mapper.toDto(suscribirFondoUseCase.suscribir(currentClienteId(authentication),
                request.idFondo(), request.preferenciaNotificacion())), "Suscripción realizada correctamente");
    }

    @DeleteMapping("/{idFondo}")
    public ApiResponse<TransaccionDto> cancelar(@PathVariable String idFondo, Authentication authentication) {
        return ApiResponse.ok(mapper.toDto(cancelarSuscripcionUseCase.cancelar(currentClienteId(authentication),
                idFondo)), "Suscripción cancelada correctamente");
    }

    @GetMapping
    public ApiResponse<List<SuscripcionDto>> listarActivas(Authentication authentication) {
        String idCliente = currentClienteId(authentication);
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(() -> new ClienteNoEncontradoException(idCliente));
        return ApiResponse.ok(cliente.getSuscripcionesActivas().stream().map(mapper::toDto).toList(),
                "Suscripciones activas");
    }
}
