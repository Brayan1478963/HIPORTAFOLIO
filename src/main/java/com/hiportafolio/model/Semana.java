package com.hiportafolio.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Semana - Entidad que representa una semana académica del curso.
 *
 * El portafolio tiene exactamente 16 semanas (4 por cada unidad).
 * Cada semana pertenece a una unidad y contiene contenidos y evidencias.
 *
 * @author HiPortafolio
 * @version 1.0
 */
public class Semana implements Serializable {

    private static final long serialVersionUID = 1L;

    private int           idSemana;
    private int           idUnidad;
    private int           numero;
    private String        titulo;
    private String        descripcion;
    private String        objetivo;
    private boolean       estado;
    private LocalDateTime fechaCreacion;

    /** Unidad a la que pertenece (cargada bajo demanda) */
    private Unidad        unidad;

    /** Contenidos de esta semana (cargados bajo demanda) */
    private List<Contenido> contenidos;

    /** Evidencias de esta semana (cargadas bajo demanda) */
    private List<Evidencia>  evidencias;

    public Semana() {
        this.contenidos = new ArrayList<>();
        this.evidencias  = new ArrayList<>();
    }

    public Semana(int idSemana, int idUnidad, int numero, String titulo,
                  String descripcion, String objetivo, boolean estado) {
        this.idSemana    = idSemana;
        this.idUnidad    = idUnidad;
        this.numero      = numero;
        this.titulo      = titulo;
        this.descripcion = descripcion;
        this.objetivo    = objetivo;
        this.estado      = estado;
        this.contenidos  = new ArrayList<>();
        this.evidencias   = new ArrayList<>();
    }

    /**
     * Retorna el título formateado con el número de semana.
     * Ejemplo: "Semana 01 - Introducción a la Arquitectura de Software"
     */
    public String getTituloCompleto() {
        return String.format("Semana %02d - %s", numero, titulo);
    }

    /**
     * Retorna el número de semana con formato de dos dígitos.
     * Ejemplo: 1 → "01", 12 → "12"
     */
    public String getNumeroFormateado() {
        return String.format("%02d", numero);
    }

    /**
     * Verifica si esta semana tiene contenidos cargados.
     */
    public boolean tieneContenidos() {
        return contenidos != null && !contenidos.isEmpty();
    }

    /**
     * Verifica si esta semana tiene evidencias cargadas.
     */
    public boolean tieneEvidencias() {
        return evidencias != null && !evidencias.isEmpty();
    }

    // Getters y Setters
    public int    getIdSemana()        { return idSemana; }
    public void   setIdSemana(int v)   { this.idSemana = v; }

    public int    getIdUnidad()        { return idUnidad; }
    public void   setIdUnidad(int v)   { this.idUnidad = v; }

    public int    getNumero()          { return numero; }
    public void   setNumero(int v)     { this.numero = v; }

    public String getTitulo()          { return titulo; }
    public void   setTitulo(String v)  { this.titulo = v; }

    public String getDescripcion()         { return descripcion; }
    public void   setDescripcion(String v) { this.descripcion = v; }

    public String getObjetivo()            { return objetivo; }
    public void   setObjetivo(String v)    { this.objetivo = v; }

    public boolean isEstado()          { return estado; }
    public void    setEstado(boolean v){ this.estado = v; }

    public LocalDateTime getFechaCreacion()               { return fechaCreacion; }
    public void          setFechaCreacion(LocalDateTime v){ this.fechaCreacion = v; }

    public Unidad getUnidad()            { return unidad; }
    public void   setUnidad(Unidad v)    { this.unidad = v; }

    public List<Contenido> getContenidos()                { return contenidos; }
    public void            setContenidos(List<Contenido> v){ this.contenidos = v; }

    public List<Evidencia>  getEvidencias()                 { return evidencias; }
    public void             setEvidencias(List<Evidencia> v){ this.evidencias = v; }

    @Override
    public String toString() {
        return "Semana{id=" + idSemana + ", numero=" + numero + ", titulo='" + titulo + "'}";
    }
}
