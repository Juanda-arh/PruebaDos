package com.btg.funds.infrastructure.config;

import com.btg.funds.application.service.CancelarSuscripcionService;
import com.btg.funds.application.service.ListarClientesService;
import com.btg.funds.application.service.ListarFondosService;
import com.btg.funds.application.service.ListarTransaccionesService;
import com.btg.funds.application.service.LoginService;
import com.btg.funds.application.service.RegistrarClienteService;
import com.btg.funds.application.service.SuscribirFondoService;
import com.btg.funds.domain.port.in.CancelarSuscripcionUseCase;
import com.btg.funds.domain.port.in.ListarClientesUseCase;
import com.btg.funds.domain.port.in.ListarFondosUseCase;
import com.btg.funds.domain.port.in.ListarTransaccionesUseCase;
import com.btg.funds.domain.port.in.LoginUseCase;
import com.btg.funds.domain.port.in.RegistrarClienteUseCase;
import com.btg.funds.domain.port.in.SuscribirFondoUseCase;
import com.btg.funds.domain.port.out.ClienteRepositoryPort;
import com.btg.funds.domain.port.out.FondoRepositoryPort;
import com.btg.funds.domain.port.out.NotificacionPort;
import com.btg.funds.domain.port.out.PasswordEncoderPort;
import com.btg.funds.domain.port.out.TokenProviderPort;
import com.btg.funds.domain.port.out.TransaccionRepositoryPort;
import com.btg.funds.infrastructure.adapter.out.persistence.mapper.ClienteDocumentMapper;
import com.btg.funds.infrastructure.adapter.out.persistence.mapper.FondoDocumentMapper;
import com.btg.funds.infrastructure.adapter.out.persistence.mapper.TransaccionDocumentMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {
    @Bean
    ClienteDocumentMapper clienteDocumentMapper() {
        return new ClienteDocumentMapper();
    }

    @Bean
    FondoDocumentMapper fondoDocumentMapper() {
        return new FondoDocumentMapper();
    }

    @Bean
    TransaccionDocumentMapper transaccionDocumentMapper() {
        return new TransaccionDocumentMapper();
    }

    @Bean
    RegistrarClienteUseCase registrarClienteUseCase(ClienteRepositoryPort clienteRepository,
            PasswordEncoderPort passwordEncoder) {
        return new RegistrarClienteService(clienteRepository, passwordEncoder);
    }

    @Bean
    LoginUseCase loginUseCase(ClienteRepositoryPort clienteRepository, PasswordEncoderPort passwordEncoder,
            TokenProviderPort tokenProvider) {
        return new LoginService(clienteRepository, passwordEncoder, tokenProvider);
    }

    @Bean
    ListarFondosUseCase listarFondosUseCase(FondoRepositoryPort fondoRepository) {
        return new ListarFondosService(fondoRepository);
    }

    @Bean
    SuscribirFondoUseCase suscribirFondoUseCase(ClienteRepositoryPort clienteRepository,
            FondoRepositoryPort fondoRepository, TransaccionRepositoryPort transaccionRepository,
            NotificacionPort notificacionPort) {
        return new SuscribirFondoService(clienteRepository, fondoRepository, transaccionRepository, notificacionPort);
    }

    @Bean
    CancelarSuscripcionUseCase cancelarSuscripcionUseCase(ClienteRepositoryPort clienteRepository,
            TransaccionRepositoryPort transaccionRepository) {
        return new CancelarSuscripcionService(clienteRepository, transaccionRepository);
    }

    @Bean
    ListarTransaccionesUseCase listarTransaccionesUseCase(TransaccionRepositoryPort transaccionRepository) {
        return new ListarTransaccionesService(transaccionRepository);
    }

    @Bean
    ListarClientesUseCase listarClientesUseCase(ClienteRepositoryPort clienteRepository) {
        return new ListarClientesService(clienteRepository);
    }
}
