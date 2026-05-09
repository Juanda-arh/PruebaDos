package com.btg.funds.infrastructure.config;

import com.btg.funds.domain.model.CategoriaFondo;
import com.btg.funds.domain.model.Cliente;
import com.btg.funds.domain.model.Fondo;
import com.btg.funds.domain.model.PreferenciaNotificacion;
import com.btg.funds.domain.port.out.ClienteRepositoryPort;
import com.btg.funds.domain.port.out.FondoRepositoryPort;
import com.btg.funds.domain.port.out.PasswordEncoderPort;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {
    @Bean
    CommandLineRunner seedFondos(FondoRepositoryPort fondoRepository, ClienteRepositoryPort clienteRepository,
            PasswordEncoderPort passwordEncoder) {
        return args -> {
            if (fondoRepository.isEmpty()) {
                fondoRepository.saveAll(List.of(
                        new Fondo("1", "FPV_BTG_PACTUAL_RECAUDADORA", new BigDecimal("75000"), CategoriaFondo.FPV),
                        new Fondo("2", "FPV_BTG_PACTUAL_ECOPETROL", new BigDecimal("125000"), CategoriaFondo.FPV),
                        new Fondo("3", "DEUDAPRIVADA", new BigDecimal("50000"), CategoriaFondo.FIC),
                        new Fondo("4", "FDO-ACCIONES", new BigDecimal("250000"), CategoriaFondo.FIC),
                        new Fondo("5", "FPV_BTG_PACTUAL_DINAMICA", new BigDecimal("100000"), CategoriaFondo.FPV)));
            }
            if (!clienteRepository.existsByEmail("admin@btg.com")) {
                Cliente admin = new Cliente();
                admin.setEmail("admin@btg.com");
                admin.setPassword(passwordEncoder.encode("Admin123*"));
                admin.setNombre("Admin BTG");
                admin.setSaldo(Cliente.SALDO_INICIAL);
                admin.setPreferenciaNotificacion(PreferenciaNotificacion.EMAIL);
                admin.setTelefono("3000000000");
                admin.setRoles(List.of("ROLE_ADMIN"));
                clienteRepository.save(admin);
            }
        };
    }
}
