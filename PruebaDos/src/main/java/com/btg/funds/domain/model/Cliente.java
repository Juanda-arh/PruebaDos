package com.btg.funds.domain.model;

import com.btg.funds.domain.exception.SaldoInsuficienteException;
import com.btg.funds.domain.exception.SuscripcionDuplicadaException;
import com.btg.funds.domain.exception.SuscripcionNoActivaException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Cliente {
    public static final BigDecimal SALDO_INICIAL = new BigDecimal("500000");

    private String id;
    private String email;
    private String password;
    private String nombre;
    private BigDecimal saldo;
    private PreferenciaNotificacion preferenciaNotificacion;
    private String telefono;
    private List<String> roles = new ArrayList<>();
    private List<Suscripcion> suscripcionesActivas = new ArrayList<>();

    public Cliente() {
    }

    public Cliente(String id, String email, String password, String nombre, BigDecimal saldo,
            PreferenciaNotificacion preferenciaNotificacion, String telefono, List<String> roles,
            List<Suscripcion> suscripcionesActivas) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nombre = nombre;
        this.saldo = saldo;
        this.preferenciaNotificacion = preferenciaNotificacion;
        this.telefono = telefono;
        this.roles = roles == null ? new ArrayList<>() : new ArrayList<>(roles);
        this.suscripcionesActivas = suscripcionesActivas == null ? new ArrayList<>() : new ArrayList<>(suscripcionesActivas);
    }

    public boolean tieneSuscripcionActiva(String idFondo) {
        return suscripcionesActivas.stream().anyMatch(suscripcion -> suscripcion.getIdFondo().equals(idFondo));
    }

    public Suscripcion suscribir(Fondo fondo, String idTransaccion, LocalDateTime fecha) {
        if (tieneSuscripcionActiva(fondo.getId())) {
            throw new SuscripcionDuplicadaException("Ya existe una suscripción activa al fondo " + fondo.getNombre());
        }
        if (saldo.compareTo(fondo.getMontoMinimo()) < 0) {
            throw new SaldoInsuficienteException(fondo.getNombre());
        }
        saldo = saldo.subtract(fondo.getMontoMinimo());
        Suscripcion suscripcion = new Suscripcion(
                fondo.getId(),
                fondo.getNombre(),
                fondo.getMontoMinimo(),
                fecha,
                idTransaccion);
        suscripcionesActivas.add(suscripcion);
        return suscripcion;
    }

    public Suscripcion cancelarSuscripcion(String idFondo) {
        Optional<Suscripcion> suscripcion = suscripcionesActivas.stream()
                .filter(activa -> activa.getIdFondo().equals(idFondo))
                .findFirst();
        if (suscripcion.isEmpty()) {
            throw new SuscripcionNoActivaException("No existe una suscripción activa al fondo " + idFondo);
        }
        Suscripcion activa = suscripcion.get();
        suscripcionesActivas.remove(activa);
        saldo = saldo.add(activa.getMontoVinculado());
        return activa;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public PreferenciaNotificacion getPreferenciaNotificacion() {
        return preferenciaNotificacion;
    }

    public void setPreferenciaNotificacion(PreferenciaNotificacion preferenciaNotificacion) {
        this.preferenciaNotificacion = preferenciaNotificacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles == null ? new ArrayList<>() : new ArrayList<>(roles);
    }

    public List<Suscripcion> getSuscripcionesActivas() {
        return suscripcionesActivas;
    }

    public void setSuscripcionesActivas(List<Suscripcion> suscripcionesActivas) {
        this.suscripcionesActivas = suscripcionesActivas == null ? new ArrayList<>() : new ArrayList<>(suscripcionesActivas);
    }
}
