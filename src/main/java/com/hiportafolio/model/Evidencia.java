package com.hiportafolio.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Evidencia - Entidad que representa una evidencia de aprendizaje.
 *
 * Las evidencias pertenecen a una semana y pueden tener un archivo adjunto.
 * Tipos posibles: imagen, pdf, documento, diagrama, captura.
 *
 * @author HiPortafolio
 * @version 1.0
 */
public class Evidencia implements Serializable {

    private static final long serialVersionUID = 1L;

    private int           idEvidencia;
    private int           idSemana;
    private int           idArchivo;
    private String        titulo;
    private String        descripcion;
    private String        tipo;
    private LocalDate     fechaEvidencia;
    private boolean       estado;
    private LocalDateTime fechaCreacion;

    /** Campos calculados mediante JOIN */
    private String        tituloSemana;
    private int           numeroSemana;
    private String        tituloUnidad;
    private Archivo       archivo;          // archivo adjunto (puede ser null)

    public Evidencia() {}

    public Evidencia(int idEvidencia, int idSemana, int idArchivo,
                     String titulo, String descripcion, String tipo,
                     LocalDate fechaEvidencia, boolean estado) {
        this.idEvidencia    = idEvidencia;
        this.idSemana       = idSemana;
        this.idArchivo      = idArchivo;
        this.titulo         = titulo;
        this.descripcion    = descripcion;
        this.tipo           = tipo;
        this.fechaEvidencia = fechaEvidencia;
        this.estado         = estado;
    }

    /**
     * Verifica si esta evidencia tiene un archivo adjunto.
     */
    public boolean tieneArchivo() {
        return archivo != null || idArchivo > 0;
    }

    /**
     * Retorna la etiqueta de tipo formateada para mostrar en UI.
     */
    public String getTipoEtiqueta() {
        if (tipo == null) return "Otro";
        return switch (tipo.toLowerCase()) {
            case "imagen"    -> "Imagen";
            case "pdf"       -> "PDF";
            case "documento" -> "Documento";
            case "diagrama"  -> "Diagrama";
            case "captura"   -> "Captura de pantalla";
            default          -> tipo;
        };
    }

    /**
     * Retorna la clase CSS Badge de Bootstrap según el tipo.
     */
    public String getBadgeClase() {
        if (tipo == null) return "bg-secondary";
        return switch (tipo.toLowerCase()) {
            case "imagen"    -> "bg-success";
            case "pdf"       -> "bg-danger";
            case "documento" -> "bg-primary";
            case "diagrama"  -> "bg-warning text-dark";
            case "captura"   -> "bg-info text-dark";
            default          -> "bg-secondary";
        };
    }

    // Getters y Setters
    public int    getIdEvidencia()        { return idEvidencia; }
    public void   setIdEvidencia(int v)   { this.idEvidencia = v; }

    public int    getIdSemana()           { return idSemana; }
    public void   setIdSemana(int v)      { this.idSemana = v; }

    public int    getIdArchivo()          { return idArchivo; }
    public void   setIdArchivo(int v)     { this.idArchivo = v; }

    public String getTitulo()             { return titulo; }
    public void   setTitulo(String v)     { this.titulo = v; }

    public String getDescripcion()            { return descripcion; }
    public void   setDescripcion(String v)    { this.descripcion = v; }

    public String getTipo()               { return tipo; }
    public void   setTipo(String v)       { this.tipo = v; }

    public LocalDate getFechaEvidencia()              { return fechaEvidencia; }
    public void      setFechaEvidencia(LocalDate v)   { this.fechaEvidencia = v; }

    public boolean isEstado()             { return estado; }
    public void    setEstado(boolean v)   { this.estado = v; }

    public LocalDateTime getFechaCreacion()               { return fechaCreacion; }
    public void          setFechaCreacion(LocalDateTime v){ this.fechaCreacion = v; }

    public String getTituloSemana()           { return tituloSemana; }
    public void   setTituloSemana(String v)   { this.tituloSemana = v; }

    public int    getNumeroSemana()           { return numeroSemana; }
    public void   setNumeroSemana(int v)      { this.numeroSemana = v; }

    public String getTituloUnidad()           { return tituloUnidad; }
    public void   setTituloUnidad(String v)   { this.tituloUnidad = v; }

    public Archivo getArchivo()           { return archivo; }
    public void    setArchivo(Archivo v)  { this.archivo = v; }

    @Override
    public String toString() {
        return "Evidencia{id=" + idEvidencia + ", semana=" + idSemana +
               ", titulo='" + titulo + "', tipo='" + tipo + "'}";
    }
}
