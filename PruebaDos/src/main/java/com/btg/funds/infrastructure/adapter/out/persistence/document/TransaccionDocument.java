package com.btg.funds.infrastructure.adapter.out.persistence.document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "transacciones")
public class TransaccionDocument {
    @Id
    private String id;
    private String idCliente;
    private String idFondo;
    private String nombreFondo;
    private String tipo;
    private BigDecimal monto;
    private LocalDateTime fecha;
    private BigDecimal saldoResultante;
}
