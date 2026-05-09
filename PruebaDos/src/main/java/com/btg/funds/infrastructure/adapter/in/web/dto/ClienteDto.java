package com.btg.funds.infrastructure.adapter.in.web.dto;

import java.math.BigDecimal;
import java.util.List;

public record ClienteDto(String id, String email, String nombre, BigDecimal saldo, String preferenciaNotificacion,
        String telefono, List<String> roles, List<SuscripcionDto> suscripcionesActivas) {
}
