package com.btg.funds.infrastructure.adapter.in.web.dto;

import com.btg.funds.domain.model.PreferenciaNotificacion;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

public record SuscribirFondoRequest(@NotBlank String idFondo, @NotNull PreferenciaNotificacion preferenciaNotificacion) {
}
