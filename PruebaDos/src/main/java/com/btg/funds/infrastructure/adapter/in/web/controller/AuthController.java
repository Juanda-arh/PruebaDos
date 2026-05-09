package com.btg.funds.infrastructure.adapter.in.web.controller;

import com.btg.funds.domain.exception.ClienteNoEncontradoException;
import com.btg.funds.domain.model.AuthResult;
import com.btg.funds.domain.model.Cliente;
import com.btg.funds.domain.port.in.LoginUseCase;
import com.btg.funds.domain.port.in.RegistrarClienteUseCase;
import com.btg.funds.domain.port.out.ClienteRepositoryPort;
import com.btg.funds.infrastructure.adapter.in.web.dto.ApiResponse;
import com.btg.funds.infrastructure.adapter.in.web.dto.AuthResponse;
import com.btg.funds.infrastructure.adapter.in.web.dto.ClienteDto;
import com.btg.funds.infrastructure.adapter.in.web.dto.LoginRequest;
import com.btg.funds.infrastructure.adapter.in.web.dto.RegisterRequest;
import com.btg.funds.infrastructure.adapter.in.web.mapper.WebMapper;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController extends AbstractAuthenticatedController {
    private final RegistrarClienteUseCase registrarClienteUseCase;
    private final LoginUseCase loginUseCase;
    private final ClienteRepositoryPort clienteRepository;
    private final WebMapper mapper;

    public AuthController(RegistrarClienteUseCase registrarClienteUseCase, LoginUseCase loginUseCase,
            ClienteRepositoryPort clienteRepository, WebMapper mapper) {
        this.registrarClienteUseCase = registrarClienteUseCase;
        this.loginUseCase = loginUseCase;
        this.clienteRepository = clienteRepository;
        this.mapper = mapper;
    }

    @PostMapping("/register")
    public ApiResponse<ClienteDto> register(@Valid @RequestBody RegisterRequest request) {
        Cliente cliente = registrarClienteUseCase.registrar(request.email(), request.password(), request.nombre(),
                request.telefono());
        return ApiResponse.ok(mapper.toDto(cliente), "Cliente registrado correctamente");
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResult result = loginUseCase.login(request.email(), request.password());
        return ApiResponse.ok(new AuthResponse(result.getToken(), mapper.toDto(result.getCliente())),
                "Login exitoso");
    }

    @GetMapping("/me")
    public ApiResponse<ClienteDto> me(Authentication authentication) {
        String idCliente = currentClienteId(authentication);
        Cliente cliente = clienteRepository.findById(idCliente).orElseThrow(() -> new ClienteNoEncontradoException(idCliente));
        return ApiResponse.ok(mapper.toDto(cliente), "Cliente autenticado");
    }
}
