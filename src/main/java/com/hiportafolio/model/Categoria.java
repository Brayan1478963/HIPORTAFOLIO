package com.hiportafolio.model;

import java.io.Serializable;

/**
 * Categoria - Entidad que clasifica las tecnologías del portafolio.
 *
 * Categorías: Backend, Frontend, Base de datos, Herramientas, DevOps.
 *
 * @author HiPortafolio
 * @version 1.0
 */
public class Categoria implements Serializable {

    private static final long serialVersionUID = 1L;

    private int    idCategoria;
    private String nombre;
    private String descripcion;

    public Categoria() {}

    public Categoria(int idCategoria, String nombre, String descripcion) {
        this.idCategoria = idCategoria;
        this.nombre      = nombre;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public int    getIdCategoria()        { return idCategoria; }
    public void   setIdCategoria(int v)   { this.idCategoria = v; }

    public String getNombre()             { return nombre; }
    public void   setNombre(String v)     { this.nombre = v; }

    public String getDescripcion()            { return descripcion; }
    public void   setDescripcion(String v)    { this.descripcion = v; }

    @Override
    public String toString() {
        return "Categoria{id=" + idCategoria + ", nombre='" + nombre + "'}";
    }
}
