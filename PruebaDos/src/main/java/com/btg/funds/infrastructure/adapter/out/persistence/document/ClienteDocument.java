package com.btg.funds.infrastructure.adapter.out.persistence.document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "clientes")
public class ClienteDocument {
    @Id
    private String id;
    private String email;
    private String password;
    private String nombre;
    private BigDecimal saldo;
    private String preferenciaNotificacion;
    private String telefono;
    private List<String> roles = new ArrayList<>();
    private List<SuscripcionDocument> suscripcionesActivas = new ArrayList<>();
}
