package com.hiportafolio.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Contenido - Entidad que representa el contenido educativo de una semana.
 *
 * Cada semana puede tener múltiples contenidos organizados por orden.
 * Almacena el texto del tema, aprendizajes, reflexión y referencias.
 *
 * @author HiPortafolio
 * @version 1.0
 */
public class Contenido implements Serializable {

    private static final long serialVersionUID = 1L;

    private int           idContenido;
    private int           idSemana;
    private String        titulo;
    private String        descripcion;
    private String        contenido;       // texto principal (puede ser Markdown)
    private String        aprendizaje;     // aprendizaje obtenido
    private String        reflexion;       // reflexión personal
    private String        referencias;     // bibliografía
    private int           orden;
    private boolean       estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;

    /** Semana a la que pertenece (campo calculado, no columna directa) */
    private String        tituloSemana;

    public Contenido() {}

    public Contenido(int idContenido, int idSemana, String titulo, String descripcion,
                     String contenido, String aprendizaje, String reflexion,
                     String referencias, int orden, boolean estado) {
        this.idContenido  = idContenido;
        this.idSemana     = idSemana;
        this.titulo       = titulo;
        this.descripcion  = descripcion;
        this.contenido    = contenido;
        this.aprendizaje  = aprendizaje;
        this.reflexion    = reflexion;
        this.referencias  = referencias;
        this.orden        = orden;
        this.estado       = estado;
    }

    /**
     * Verifica si el contenido tiene reflexión personal definida.
     */
    public boolean tieneReflexion() {
        return reflexion != null && !reflexion.trim().isEmpty();
    }

    /**
     * Verifica si el contenido tiene referencias bibliográficas.
     */
    public boolean tieneReferencias() {
        return referencias != null && !referencias.trim().isEmpty();
    }

    // Getters y Setters
    public int    getIdContenido()       { return idContenido; }
    public void   setIdContenido(int v)  { this.idContenido = v; }

    public int    getIdSemana()          { return idSemana; }
    public void   setIdSemana(int v)     { this.idSemana = v; }

    public String getTitulo()            { return titulo; }
    public void   setTitulo(String v)    { this.titulo = v; }

    public String getDescripcion()           { return descripcion; }
    public void   setDescripcion(String v)   { this.descripcion = v; }

    public String getContenido()             { return contenido; }
    public void   setContenido(String v)     { this.contenido = v; }

    public String getAprendizaje()           { return aprendizaje; }
    public void   setAprendizaje(String v)   { this.aprendizaje = v; }

    public String getReflexion()             { return reflexion; }
    public void   setReflexion(String v)     { this.reflexion = v; }

    public String getReferencias()           { return referencias; }
    public void   setReferencias(String v)   { this.referencias = v; }

    public int    getOrden()             { return orden; }
    public void   setOrden(int v)        { this.orden = v; }

    public boolean isEstado()            { return estado; }
    public void    setEstado(boolean v)  { this.estado = v; }

    public LocalDateTime getFechaCreacion()               { return fechaCreacion; }
    public void          setFechaCreacion(LocalDateTime v){ this.fechaCreacion = v; }

    public LocalDateTime getFechaActualizacion()               { return fechaActualizacion; }
    public void          setFechaActualizacion(LocalDateTime v){ this.fechaActualizacion = v; }

    public String getTituloSemana()           { return tituloSemana; }
    public void   setTituloSemana(String v)   { this.tituloSemana = v; }

    @Override
    public String toString() {
        return "Contenido{id=" + idContenido + ", semana=" + idSemana +
               ", titulo='" + titulo + "', orden=" + orden + "}";
    }
}
