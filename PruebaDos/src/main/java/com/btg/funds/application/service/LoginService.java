package com.btg.funds.application.service;

import com.btg.funds.domain.exception.CredencialesInvalidasException;
import com.btg.funds.domain.model.AuthResult;
import com.btg.funds.domain.model.Cliente;
import com.btg.funds.domain.port.in.LoginUseCase;
import com.btg.funds.domain.port.out.ClienteRepositoryPort;
import com.btg.funds.domain.port.out.PasswordEncoderPort;
import com.btg.funds.domain.port.out.TokenProviderPort;

public class LoginService implements LoginUseCase {
    private final ClienteRepositoryPort clienteRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final TokenProviderPort tokenProvider;

    public LoginService(ClienteRepositoryPort clienteRepository, PasswordEncoderPort passwordEncoder,
            TokenProviderPort tokenProvider) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public AuthResult login(String email, String password) {
        Cliente cliente = clienteRepository.findByEmail(email).orElseThrow(CredencialesInvalidasException::new);
        if (!passwordEncoder.matches(password, cliente.getPassword())) {
            throw new CredencialesInvalidasException();
        }
        return new AuthResult(tokenProvider.generarToken(cliente), cliente);
    }
}
