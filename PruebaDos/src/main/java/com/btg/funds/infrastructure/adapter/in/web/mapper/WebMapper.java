package com.btg.funds.infrastructure.adapter.in.web.mapper;

import com.btg.funds.domain.model.Cliente;
import com.btg.funds.domain.model.Fondo;
import com.btg.funds.domain.model.Suscripcion;
import com.btg.funds.domain.model.Transaccion;
import com.btg.funds.infrastructure.adapter.in.web.dto.ClienteDto;
import com.btg.funds.infrastructure.adapter.in.web.dto.FondoDto;
import com.btg.funds.infrastructure.adapter.in.web.dto.SuscripcionDto;
import com.btg.funds.infrastructure.adapter.in.web.dto.TransaccionDto;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class WebMapper {
    public ClienteDto toDto(Cliente cliente) {
        List<SuscripcionDto> suscripciones = cliente.getSuscripcionesActivas() == null ? List.of()
                : cliente.getSuscripcionesActivas().stream().map(this::toDto).toList();
        return new ClienteDto(cliente.getId(), cliente.getEmail(), cliente.getNombre(), cliente.getSaldo(),
                cliente.getPreferenciaNotificacion() == null ? null : cliente.getPreferenciaNotificacion().name(),
                cliente.getTelefono(), cliente.getRoles(), suscripciones);
    }

    public FondoDto toDto(Fondo fondo) {
        return new FondoDto(fondo.getId(), fondo.getNombre(), fondo.getMontoMinimo(), fondo.getCategoria().name());
    }

    public TransaccionDto toDto(Transaccion transaccion) {
        return new TransaccionDto(transaccion.getId(), transaccion.getIdCliente(), transaccion.getIdFondo(),
                transaccion.getNombreFondo(), transaccion.getTipo().name(), transaccion.getMonto(),
                transaccion.getFecha(), transaccion.getSaldoResultante());
    }

    public SuscripcionDto toDto(Suscripcion suscripcion) {
        return new SuscripcionDto(suscripcion.getIdFondo(), suscripcion.getNombreFondo(),
                suscripcion.getMontoVinculado(), suscripcion.getFechaApertura(), suscripcion.getIdTransaccion());
    }
}
