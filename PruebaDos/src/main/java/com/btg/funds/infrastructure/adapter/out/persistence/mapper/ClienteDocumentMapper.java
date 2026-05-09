package com.btg.funds.infrastructure.adapter.out.persistence.mapper;

import com.btg.funds.domain.model.Cliente;
import com.btg.funds.domain.model.PreferenciaNotificacion;
import com.btg.funds.domain.model.Suscripcion;
import com.btg.funds.infrastructure.adapter.out.persistence.document.ClienteDocument;
import com.btg.funds.infrastructure.adapter.out.persistence.document.SuscripcionDocument;
import java.util.List;

public class ClienteDocumentMapper {
    public Cliente toDomain(ClienteDocument document) {
        if (document == null) {
            return null;
        }
        List<Suscripcion> suscripciones = document.getSuscripcionesActivas() == null ? List.of()
                : document.getSuscripcionesActivas().stream().map(this::toDomain).toList();
        return new Cliente(document.getId(), document.getEmail(), document.getPassword(), document.getNombre(),
                document.getSaldo(), toPreferencia(document.getPreferenciaNotificacion()),
                document.getTelefono(), document.getRoles(), suscripciones);
    }

    public ClienteDocument toDocument(Cliente cliente) {
        if (cliente == null) {
            return null;
        }
        List<SuscripcionDocument> suscripciones = cliente.getSuscripcionesActivas() == null ? List.of()
                : cliente.getSuscripcionesActivas().stream().map(this::toDocument).toList();
        return new ClienteDocument(cliente.getId(), cliente.getEmail(), cliente.getPassword(), cliente.getNombre(),
                cliente.getSaldo(), cliente.getPreferenciaNotificacion() == null ? null : cliente.getPreferenciaNotificacion().name(), cliente.getTelefono(),
                cliente.getRoles(), suscripciones);
    }

    private PreferenciaNotificacion toPreferencia(String value) {
        return value == null || value.isBlank() ? null : PreferenciaNotificacion.valueOf(value);
    }

    private Suscripcion toDomain(SuscripcionDocument document) {
        return new Suscripcion(document.getIdFondo(), document.getNombreFondo(), document.getMontoVinculado(),
                document.getFechaApertura(), document.getIdTransaccion());
    }

    private SuscripcionDocument toDocument(Suscripcion suscripcion) {
        return new SuscripcionDocument(suscripcion.getIdFondo(), suscripcion.getNombreFondo(),
                suscripcion.getMontoVinculado(), suscripcion.getFechaApertura(), suscripcion.getIdTransaccion());
    }
}
