package com.btg.funds.infrastructure.adapter.out.persistence.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.btg.funds.domain.model.CategoriaFondo;
import com.btg.funds.domain.model.Fondo;
import com.btg.funds.domain.model.TipoTransaccion;
import com.btg.funds.domain.model.Transaccion;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class DocumentMapperTest {
    @Test
    void mapsFondoBothWays() {
        FondoDocumentMapper mapper = new FondoDocumentMapper();

        Fondo mapped = mapper.toDomain(mapper.toDocument(new Fondo("1", "Fondo", new BigDecimal("75000"),
                CategoriaFondo.FPV)));

        assertThat(mapped.getId()).isEqualTo("1");
        assertThat(mapped.getCategoria()).isEqualTo(CategoriaFondo.FPV);
    }

    @Test
    void mapsTransaccionBothWays() {
        TransaccionDocumentMapper mapper = new TransaccionDocumentMapper();
        Transaccion transaccion = new Transaccion("tx", "c1", "1", "Fondo", TipoTransaccion.APERTURA,
                new BigDecimal("75000"), LocalDateTime.now(), new BigDecimal("425000"));

        Transaccion mapped = mapper.toDomain(mapper.toDocument(transaccion));

        assertThat(mapped.getTipo()).isEqualTo(TipoTransaccion.APERTURA);
        assertThat(mapped.getSaldoResultante()).isEqualByComparingTo("425000");
    }
}
