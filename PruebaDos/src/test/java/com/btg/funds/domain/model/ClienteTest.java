package com.btg.funds.domain.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.btg.funds.domain.exception.SaldoInsuficienteException;
import com.btg.funds.domain.exception.SuscripcionDuplicadaException;
import com.btg.funds.domain.exception.SuscripcionNoActivaException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

class ClienteTest {
    @Test
    void suscribeAndDiscountsBalance() {
        Cliente cliente = cliente(new BigDecimal("500000"));
        Fondo fondo = fondo("1", new BigDecimal("75000"));

        cliente.suscribir(fondo, "tx-1", LocalDateTime.now());

        assertThat(cliente.getSaldo()).isEqualByComparingTo("425000");
        assertThat(cliente.getSuscripcionesActivas()).hasSize(1);
    }

    @Test
    void rejectsInsufficientBalance() {
        Cliente cliente = cliente(new BigDecimal("1000"));
        Fondo fondo = fondo("1", new BigDecimal("75000"));

        assertThatThrownBy(() -> cliente.suscribir(fondo, "tx-1", LocalDateTime.now()))
                .isInstanceOf(SaldoInsuficienteException.class)
                .hasMessage("No tiene saldo disponible para vincularse al fondo Fondo Test");
    }

    @Test
    void rejectsDuplicateSubscription() {
        Cliente cliente = cliente(new BigDecimal("500000"));
        Fondo fondo = fondo("1", new BigDecimal("75000"));
        cliente.suscribir(fondo, "tx-1", LocalDateTime.now());

        assertThatThrownBy(() -> cliente.suscribir(fondo, "tx-2", LocalDateTime.now()))
                .isInstanceOf(SuscripcionDuplicadaException.class);
    }

    @Test
    void cancelsAndReturnsBalance() {
        Cliente cliente = cliente(new BigDecimal("500000"));
        Fondo fondo = fondo("1", new BigDecimal("75000"));
        cliente.suscribir(fondo, "tx-1", LocalDateTime.now());

        Suscripcion cancelada = cliente.cancelarSuscripcion("1");

        assertThat(cancelada.getIdFondo()).isEqualTo("1");
        assertThat(cliente.getSaldo()).isEqualByComparingTo("500000");
        assertThat(cliente.getSuscripcionesActivas()).isEmpty();
    }

    @Test
    void rejectsCancelOfInactiveSubscription() {
        Cliente cliente = cliente(new BigDecimal("500000"));

        assertThatThrownBy(() -> cliente.cancelarSuscripcion("1"))
                .isInstanceOf(SuscripcionNoActivaException.class);
    }

    private Cliente cliente(BigDecimal saldo) {
        return new Cliente("c1", "cliente@btg.com", "hash", "Cliente", saldo, PreferenciaNotificacion.EMAIL,
                "300", List.of("ROLE_CLIENTE"), List.of());
    }

    private Fondo fondo(String id, BigDecimal minimo) {
        return new Fondo(id, "Fondo Test", minimo, CategoriaFondo.FPV);
    }
}
