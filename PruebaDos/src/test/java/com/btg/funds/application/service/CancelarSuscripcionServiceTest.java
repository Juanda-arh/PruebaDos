package com.btg.funds.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import com.btg.funds.domain.exception.SuscripcionNoActivaException;
import com.btg.funds.domain.model.CategoriaFondo;
import com.btg.funds.domain.model.Cliente;
import com.btg.funds.domain.model.Fondo;
import com.btg.funds.domain.model.PreferenciaNotificacion;
import com.btg.funds.domain.model.Transaccion;
import com.btg.funds.domain.port.out.ClienteRepositoryPort;
import com.btg.funds.domain.port.out.TransaccionRepositoryPort;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CancelarSuscripcionServiceTest {
    @Mock
    ClienteRepositoryPort clienteRepository;
    @Mock
    TransaccionRepositoryPort transaccionRepository;

    CancelarSuscripcionService service;

    @BeforeEach
    void setUp() {
        service = new CancelarSuscripcionService(clienteRepository, transaccionRepository);
    }

    @Test
    void cancelsSuccessfully() {
        Cliente cliente = cliente();
        cliente.suscribir(new Fondo("1", "Fondo Test", new BigDecimal("75000"), CategoriaFondo.FPV), "tx",
                LocalDateTime.now());
        when(clienteRepository.findById("c1")).thenReturn(Optional.of(cliente));
        when(transaccionRepository.save(org.mockito.ArgumentMatchers.any(Transaccion.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Transaccion transaccion = service.cancelar("c1", "1");

        assertThat(transaccion.getMonto()).isEqualByComparingTo("75000");
        assertThat(cliente.getSaldo()).isEqualByComparingTo("500000");
    }

    @Test
    void rejectsInactiveSubscription() {
        when(clienteRepository.findById("c1")).thenReturn(Optional.of(cliente()));

        assertThatThrownBy(() -> service.cancelar("c1", "1")).isInstanceOf(SuscripcionNoActivaException.class);
    }

    private Cliente cliente() {
        return new Cliente("c1", "cliente@btg.com", "hash", "Cliente", new BigDecimal("500000"),
                PreferenciaNotificacion.EMAIL, "300", List.of("ROLE_CLIENTE"), List.of());
    }
}
