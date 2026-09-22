package com.hiportafolio.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Usuario - Entidad que representa un usuario del sistema.
 *
 * Principios POO aplicados:
 *   - Encapsulamiento: atributos privados accesibles solo mediante métodos
 *   - Abstracción: expone comportamiento relevante sin revelar implementación
 *
 * Se almacena en la sesión HTTP (implementa Serializable).
 * NUNCA almacenar la contraseña en texto plano aquí ni en ningún lugar del sistema.
 *
 * @author HiPortafolio
 * @version 1.0
 */
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private int           idUsuario;
    private String        nombre;
    private String        apellido;
    private String        correo;
    private String        passwordHash;      // hash BCrypt, nunca texto plano
    private int           idRol;
    private String        nombreRol;         // campo calculado: JOIN con roles
    private boolean       estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    public Usuario() {}

    public Usuario(int idUsuario, String nombre, String apellido,
                   String correo, int idRol, String nombreRol, boolean estado) {
        this.idUsuario  = idUsuario;
        this.nombre     = nombre;
        this.apellido   = apellido;
        this.correo     = correo;
        this.idRol      = idRol;
        this.nombreRol  = nombreRol;
        this.estado     = estado;
    }

    // =========================================================
    // Métodos de comportamiento (no solo getters/setters)
    // =========================================================

    /**
     * Retorna el nombre completo del usuario (nombre + apellido).
     * Abstracción: el llamador no necesita saber cómo se construye.
     */
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    /**
     * Verifica si el usuario tiene el rol de administrador.
     */
    public boolean esAdmin() {
        return "ADMIN".equalsIgnoreCase(nombreRol);
    }

    /**
     * Verifica si el usuario está activo y puede iniciar sesión.
     */
    public boolean puedeIniciarSesion() {
        return estado;
    }

    // =========================================================
    // Getters y Setters (Encapsulamiento)
    // =========================================================

    public int    getIdUsuario()        { return idUsuario; }
    public void   setIdUsuario(int v)   { this.idUsuario = v; }

    public String getNombre()           { return nombre; }
    public void   setNombre(String v)   { this.nombre = v; }

    public String getApellido()         { return apellido; }
    public void   setApellido(String v) { this.apellido = v; }

    public String getCorreo()           { return correo; }
    public void   setCorreo(String v)   { this.correo = v; }

    public String getPasswordHash()         { return passwordHash; }
    public void   setPasswordHash(String v) { this.passwordHash = v; }

    public int    getIdRol()            { return idRol; }
    public void   setIdRol(int v)       { this.idRol = v; }

    public String getNombreRol()            { return nombreRol; }
    public void   setNombreRol(String v)    { this.nombreRol = v; }

    public boolean isEstado()           { return estado; }
    public void    setEstado(boolean v) { this.estado = v; }

    public LocalDateTime getFechaCreacion()              { return fechaCreacion; }
    public void          setFechaCreacion(LocalDateTime v){ this.fechaCreacion = v; }

    public LocalDateTime getFechaActualizacion()               { return fechaActualizacion; }
    public void          setFechaActualizacion(LocalDateTime v){ this.fechaActualizacion = v; }

    @Override
    public String toString() {
        return "Usuario{id=" + idUsuario + ", correo='" + correo +
               "', rol='" + nombreRol + "', estado=" + estado + "}";
    }
}
