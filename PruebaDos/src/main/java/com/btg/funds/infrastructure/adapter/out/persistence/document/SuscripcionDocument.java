package com.btg.funds.infrastructure.adapter.out.persistence.document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SuscripcionDocument {
    private String idFondo;
    private String nombreFondo;
    private BigDecimal montoVinculado;
    private LocalDateTime fechaApertura;
    private String idTransaccion;
}
