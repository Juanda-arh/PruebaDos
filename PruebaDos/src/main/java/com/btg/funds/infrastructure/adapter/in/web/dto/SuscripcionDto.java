package com.btg.funds.infrastructure.adapter.in.web.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SuscripcionDto(String idFondo, String nombreFondo, BigDecimal montoVinculado,
        LocalDateTime fechaApertura, String idTransaccion) {
}
