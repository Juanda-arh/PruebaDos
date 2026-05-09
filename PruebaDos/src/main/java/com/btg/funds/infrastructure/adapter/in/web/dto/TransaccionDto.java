package com.btg.funds.infrastructure.adapter.in.web.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransaccionDto(String id, String idCliente, String idFondo, String nombreFondo, String tipo,
        BigDecimal monto, LocalDateTime fecha, BigDecimal saldoResultante) {
}
