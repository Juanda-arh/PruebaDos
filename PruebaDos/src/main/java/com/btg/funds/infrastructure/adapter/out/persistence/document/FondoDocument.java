package com.btg.funds.infrastructure.adapter.out.persistence.document;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "fondos")
public class FondoDocument {
    @Id
    private String id;
    private String nombre;
    private BigDecimal montoMinimo;
    private String categoria;
}
