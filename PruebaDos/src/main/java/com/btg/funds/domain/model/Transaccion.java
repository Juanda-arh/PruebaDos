package com.btg.funds.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaccion {
    private String id;
    private String idCliente;
    private String idFondo;
    private String nombreFondo;
    private TipoTransaccion tipo;
    private BigDecimal monto;
    private LocalDateTime fecha;
    private BigDecimal saldoResultante;

    public Transaccion() {
    }

    public Transaccion(String id, String idCliente, String idFondo, String nombreFondo,
            TipoTransaccion tipo, BigDecimal monto, LocalDateTime fecha, BigDecimal saldoResultante) {
        this.id = id;
        this.idCliente = idCliente;
        this.idFondo = idFondo;
        this.nombreFondo = nombreFondo;
        this.tipo = tipo;
        this.monto = monto;
        this.fecha = fecha;
        this.saldoResultante = saldoResultante;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
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

    public TipoTransaccion getTipo() {
        return tipo;
    }

    public void setTipo(TipoTransaccion tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public BigDecimal getSaldoResultante() {
        return saldoResultante;
    }

    public void setSaldoResultante(BigDecimal saldoResultante) {
        this.saldoResultante = saldoResultante;
    }
}
