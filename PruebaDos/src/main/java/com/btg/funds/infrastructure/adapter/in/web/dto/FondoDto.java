package com.btg.funds.infrastructure.adapter.in.web.dto;

import java.math.BigDecimal;

public record FondoDto(String id, String nombre, BigDecimal montoMinimo, String categoria) {
}
