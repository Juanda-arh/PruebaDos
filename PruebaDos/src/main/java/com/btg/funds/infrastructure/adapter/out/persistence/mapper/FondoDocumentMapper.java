package com.btg.funds.infrastructure.adapter.out.persistence.mapper;

import com.btg.funds.domain.model.CategoriaFondo;
import com.btg.funds.domain.model.Fondo;
import com.btg.funds.infrastructure.adapter.out.persistence.document.FondoDocument;

public class FondoDocumentMapper {
    public Fondo toDomain(FondoDocument document) {
        if (document == null) {
            return null;
        }
        return new Fondo(document.getId(), document.getNombre(), document.getMontoMinimo(),
                CategoriaFondo.valueOf(document.getCategoria()));
    }

    public FondoDocument toDocument(Fondo fondo) {
        if (fondo == null) {
            return null;
        }
        return new FondoDocument(fondo.getId(), fondo.getNombre(), fondo.getMontoMinimo(), fondo.getCategoria().name());
    }
}
