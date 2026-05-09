package com.btg.funds.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.btg.funds.domain.exception.FondoNoEncontradoException;
import com.btg.funds.domain.exception.SaldoInsuficienteException;
import com.btg.funds.domain.exception.SuscripcionDuplicadaException;
import com.btg.funds.domain.model.CategoriaFondo;
import com.btg.funds.domain.model.Cliente;
import com.btg.funds.domain.model.Fondo;
import com.btg.funds.domain.model.PreferenciaNotificacion;
import com.btg.funds.domain.model.Transaccion;
import com.btg.funds.domain.port.out.ClienteRepositoryPort;
import com.btg.funds.domain.port.out.FondoRepositoryPort;
import com.btg.funds.domain.port.out.NotificacionPort;
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
class SuscribirFondoServiceTest {
    @Mock
    ClienteRepositoryPort clienteRepository;
    @Mock
    FondoRepositoryPort fondoRepository;
    @Mock
    TransaccionRepositoryPort transaccionRepository;
    @Mock
    NotificacionPort notificacionPort;

    SuscribirFondoService service;

    @BeforeEach
    void setUp() {
        service = new SuscribirFondoService(clienteRepository, fondoRepository, transaccionRepository, notificacionPort);
    }

    @Test
    void subscribesSuccessfully() {
        Cliente cliente = cliente(new BigDecimal("500000"));
        Fondo fondo = fondo("1", new BigDecimal("75000"));
        when(clienteRepository.findById("c1")).thenReturn(Optional.of(cliente));
        when(fondoRepository.findById("1")).thenReturn(Optional.of(fondo));
        when(transaccionRepository.save(org.mockito.ArgumentMatchers.any(Transaccion.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Transaccion transaccion = service.suscribir("c1", "1", PreferenciaNotificacion.EMAIL);

        assertThat(transaccion.getId()).isNotBlank();
        assertThat(transaccion.getMonto()).isEqualByComparingTo("75000");
        assertThat(cliente.getSaldo()).isEqualByComparingTo("425000");
        verify(clienteRepository).save(cliente);
        verify(notificacionPort).notificarSuscripcion(org.mockito.ArgumentMatchers.eq(cliente),
                org.mockito.ArgumentMatchers.eq(fondo), org.mockito.ArgumentMatchers.anyString());
    }

    @Test
    void rejectsInsufficientBalance() {
        Cliente cliente = cliente(new BigDecimal("1000"));
        when(clienteRepository.findById("c1")).thenReturn(Optional.of(cliente));
        when(fondoRepository.findById("1")).thenReturn(Optional.of(fondo("1", new BigDecimal("75000"))));

        assertThatThrownBy(() -> service.suscribir("c1", "1", PreferenciaNotificacion.EMAIL))
                .isInstanceOf(SaldoInsuficienteException.class);
        verify(transaccionRepository, never()).save(org.mockito.ArgumentMatchers.any());
    }

    @Test
    void rejectsMissingFund() {
        when(clienteRepository.findById("c1")).thenReturn(Optional.of(cliente(new BigDecimal("500000"))));
        when(fondoRepository.findById("1")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.suscribir("c1", "1", PreferenciaNotificacion.EMAIL))
                .isInstanceOf(FondoNoEncontradoException.class);
    }

    @Test
    void rejectsDuplicateSubscription() {
        Cliente cliente = cliente(new BigDecimal("500000"));
        cliente.suscribir(fondo("1", new BigDecimal("75000")), "tx", LocalDateTime.now());
        when(clienteRepository.findById("c1")).thenReturn(Optional.of(cliente));
        when(fondoRepository.findById("1")).thenReturn(Optional.of(fondo("1", new BigDecimal("75000"))));

        assertThatThrownBy(() -> service.suscribir("c1", "1", PreferenciaNotificacion.EMAIL))
                .isInstanceOf(SuscripcionDuplicadaException.class);
    }

    private Cliente cliente(BigDecimal saldo) {
        return new Cliente("c1", "cliente@btg.com", "hash", "Cliente", saldo, PreferenciaNotificacion.EMAIL,
                "300", List.of("ROLE_CLIENTE"), List.of());
    }

    private Fondo fondo(String id, BigDecimal minimo) {
        return new Fondo(id, "Fondo Test", minimo, CategoriaFondo.FPV);
    }
}
