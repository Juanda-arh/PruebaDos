package com.btg.funds.infrastructure.adapter.in.web.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.btg.funds.domain.model.CategoriaFondo;
import com.btg.funds.domain.model.Fondo;
import com.btg.funds.infrastructure.adapter.in.web.dto.FondoDto;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;

class WebMapperTest {
    @Test
    void mapsFondoDto() {
        WebMapper mapper = new WebMapper();

        FondoDto dto = mapper.toDto(new Fondo("1", "Fondo", new BigDecimal("75000"), CategoriaFondo.FPV));

        assertThat(dto.id()).isEqualTo("1");
        assertThat(dto.categoria()).isEqualTo("FPV");
    }
}
