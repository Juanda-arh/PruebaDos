package com.btg.funds.domain.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Fondo {
    private String id;
    private String nombre;
    private BigDecimal montoMinimo;
    private CategoriaFondo categoria;

    public Fondo() {
    }

    public Fondo(String id, String nombre, BigDecimal montoMinimo, CategoriaFondo categoria) {
        this.id = id;
        this.nombre = nombre;
        this.montoMinimo = montoMinimo;
        this.categoria = categoria;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getMontoMinimo() {
        return montoMinimo;
    }

    public void setMontoMinimo(BigDecimal montoMinimo) {
        this.montoMinimo = montoMinimo;
    }

    public CategoriaFondo getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaFondo categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Fondo fondo)) {
            return false;
        }
        return Objects.equals(id, fondo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
