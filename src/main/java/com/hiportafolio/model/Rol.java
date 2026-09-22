package com.hiportafolio.model;

import java.io.Serializable;

/**
 * Rol - Entidad que representa un rol del sistema.
 *
 * Roles disponibles: ADMIN, USUARIO.
 * Principio POO aplicado: Encapsulamiento (atributos privados + getters/setters).
 *
 * @author HiPortafolio
 * @version 1.0
 */
public class Rol implements Serializable {

    private static final long serialVersionUID = 1L;

    private int    idRol;
    private String nombre;
    private String descripcion;
    private boolean estado;

    public Rol() {}

    public Rol(int idRol, String nombre, String descripcion, boolean estado) {
        this.idRol       = idRol;
        this.nombre      = nombre;
        this.descripcion = descripcion;
        this.estado      = estado;
    }

    // Getters y Setters
    public int    getIdRol()       { return idRol; }
    public void   setIdRol(int v)  { this.idRol = v; }

    public String getNombre()           { return nombre; }
    public void   setNombre(String v)   { this.nombre = v; }

    public String getDescripcion()          { return descripcion; }
    public void   setDescripcion(String v)  { this.descripcion = v; }

    public boolean isEstado()          { return estado; }
    public void    setEstado(boolean v){ this.estado = v; }

    @Override
    public String toString() {
        return "Rol{idRol=" + idRol + ", nombre='" + nombre + "'}";
    }
}
