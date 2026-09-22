package com.hiportafolio.model;

import java.io.Serializable;

/**
 * Tecnologia - Entidad que representa una tecnología del portafolio.
 *
 * Las tecnologías se muestran en la sección pública del portafolio
 * y se asocian a los proyectos mediante la tabla proyecto_tecnologia.
 *
 * @author HiPortafolio
 * @version 1.0
 */
public class Tecnologia implements Serializable {

    private static final long serialVersionUID = 1L;

    private int    idTecnologia;
    private int    idCategoria;
    private String nombre;
    private String descripcion;
    private String icono;         // clase CSS del ícono (ej: devicon-java-plain)
    private String nivel;         // Básico, Intermedio, Avanzado
    private boolean estado;
    private int    orden;

    /** Nombre de la categoría (campo calculado, JOIN) */
    private String nombreCategoria;

    public Tecnologia() {}

    public Tecnologia(int idTecnologia, int idCategoria, String nombre,
                      String descripcion, String icono, String nivel,
                      boolean estado, int orden) {
        this.idTecnologia = idTecnologia;
        this.idCategoria  = idCategoria;
        this.nombre       = nombre;
        this.descripcion  = descripcion;
        this.icono        = icono;
        this.nivel        = nivel;
        this.estado       = estado;
        this.orden        = orden;
    }

    /**
     * Retorna la clase CSS del badge de nivel para Bootstrap.
     */
    public String getNivelBadgeClase() {
        if (nivel == null) return "bg-secondary";
        return switch (nivel.toLowerCase()) {
            case "básico"      -> "bg-info text-dark";
            case "intermedio"  -> "bg-primary";
            case "avanzado"    -> "bg-success";
            default            -> "bg-secondary";
        };
    }

    // Getters y Setters
    public int    getIdTecnologia()        { return idTecnologia; }
    public void   setIdTecnologia(int v)   { this.idTecnologia = v; }

    public int    getIdCategoria()         { return idCategoria; }
    public void   setIdCategoria(int v)    { this.idCategoria = v; }

    public String getNombre()              { return nombre; }
    public void   setNombre(String v)      { this.nombre = v; }

    public String getDescripcion()             { return descripcion; }
    public void   setDescripcion(String v)     { this.descripcion = v; }

    public String getIcono()               { return icono; }
    public void   setIcono(String v)       { this.icono = v; }

    public String getNivel()               { return nivel; }
    public void   setNivel(String v)       { this.nivel = v; }

    public boolean isEstado()              { return estado; }
    public void    setEstado(boolean v)    { this.estado = v; }

    public int    getOrden()               { return orden; }
    public void   setOrden(int v)          { this.orden = v; }

    public String getNombreCategoria()             { return nombreCategoria; }
    public void   setNombreCategoria(String v)     { this.nombreCategoria = v; }

    @Override
    public String toString() {
        return "Tecnologia{id=" + idTecnologia + ", nombre='" + nombre +
               "', nivel='" + nivel + "'}";
    }
}
