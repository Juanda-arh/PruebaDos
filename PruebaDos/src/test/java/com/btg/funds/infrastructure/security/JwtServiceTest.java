package com.btg.funds.infrastructure.security;

import static org.assertj.core.api.Assertions.assertThat;

import com.btg.funds.domain.model.Cliente;
import com.btg.funds.domain.model.PreferenciaNotificacion;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

class JwtServiceTest {
    @Test
    void generatesAndValidatesToken() {
        JwtService jwtService = new JwtService("test-secret-with-enough-entropy-for-hmac-signature", 60000);
        Cliente cliente = new Cliente("c1", "cliente@btg.com", "hash", "Cliente", new BigDecimal("500000"),
                PreferenciaNotificacion.EMAIL, "300", List.of("ROLE_CLIENTE"), List.of());

        String token = jwtService.generarToken(cliente);

        assertThat(jwtService.extractUsername(token)).isEqualTo("cliente@btg.com");
        assertThat(jwtService.isTokenValid(token, "cliente@btg.com")).isTrue();
    }
}
