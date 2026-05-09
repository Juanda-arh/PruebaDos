package com.btg.funds.infrastructure.adapter.out.persistence.mapper;

import com.btg.funds.domain.model.TipoTransaccion;
import com.btg.funds.domain.model.Transaccion;
import com.btg.funds.infrastructure.adapter.out.persistence.document.TransaccionDocument;

public class TransaccionDocumentMapper {
    public Transaccion toDomain(TransaccionDocument document) {
        if (document == null) {
            return null;
        }
        return new Transaccion(document.getId(), document.getIdCliente(), document.getIdFondo(), document.getNombreFondo(),
                TipoTransaccion.valueOf(document.getTipo()), document.getMonto(), document.getFecha(),
                document.getSaldoResultante());
    }

    public TransaccionDocument toDocument(Transaccion transaccion) {
        if (transaccion == null) {
            return null;
        }
        return new TransaccionDocument(transaccion.getId(), transaccion.getIdCliente(), transaccion.getIdFondo(),
                transaccion.getNombreFondo(), transaccion.getTipo().name(), transaccion.getMonto(),
                transaccion.getFecha(), transaccion.getSaldoResultante());
    }
}
