package com.btg.funds.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

public class Suscripcion {
    private String idFondo;
    private String nombreFondo;
    private BigDecimal montoVinculado;
    private LocalDateTime fechaApertura;
    private String idTransaccion;

    public Suscripcion() {
    }

    public Suscripcion(String idFondo, String nombreFondo, BigDecimal montoVinculado,
            LocalDateTime fechaApertura, String idTransaccion) {
        this.idFondo = idFondo;
        this.nombreFondo = nombreFondo;
        this.montoVinculado = montoVinculado;
        this.fechaApertura = fechaApertura;
        this.idTransaccion = idTransaccion;
    }

    public String getIdFondo() {
        return idFondo;
    }

    public void setIdFondo(String idFondo) {
        this.idFondo = idFondo;
    }

    public String getNombreFondo() {
        return nombreFondo;
    }

    public void setNombreFondo(String nombreFondo) {
        this.nombreFondo = nombreFondo;
    }

    public BigDecimal getMontoVinculado() {
        return montoVinculado;
    }

    public void setMontoVinculado(BigDecimal montoVinculado) {
        this.montoVinculado = montoVinculado;
    }

    public LocalDateTime getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(LocalDateTime fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public String getIdTransaccion() {
        return idTransaccion;
    }

    public void setIdTransaccion(String idTransaccion) {
        this.idTransaccion = idTransaccion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Suscripcion that)) {
            return false;
        }
        return Objects.equals(idFondo, that.idFondo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idFondo);
    }
}
