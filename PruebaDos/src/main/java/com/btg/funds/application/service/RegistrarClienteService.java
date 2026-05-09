package com.btg.funds.application.service;

import com.btg.funds.domain.exception.ClienteExistenteException;
import com.btg.funds.domain.model.Cliente;
import com.btg.funds.domain.port.in.RegistrarClienteUseCase;
import com.btg.funds.domain.port.out.ClienteRepositoryPort;
import com.btg.funds.domain.port.out.PasswordEncoderPort;
import java.util.List;

public class RegistrarClienteService implements RegistrarClienteUseCase {
    private final ClienteRepositoryPort clienteRepository;
    private final PasswordEncoderPort passwordEncoder;

    public RegistrarClienteService(ClienteRepositoryPort clienteRepository, PasswordEncoderPort passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Cliente registrar(String email, String password, String nombre, String telefono) {
        if (clienteRepository.existsByEmail(email)) {
            throw new ClienteExistenteException(email);
        }
        Cliente cliente = new Cliente();
        cliente.setEmail(email);
        cliente.setPassword(passwordEncoder.encode(password));
        cliente.setNombre(nombre);
        cliente.setSaldo(Cliente.SALDO_INICIAL);
        cliente.setTelefono(telefono);
        cliente.setRoles(List.of("ROLE_CLIENTE"));
        return clienteRepository.save(cliente);
    }
}
