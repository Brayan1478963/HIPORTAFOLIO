package com.hiportafolio.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Unidad - Entidad que representa una unidad académica del curso.
 *
 * El portafolio tiene exactamente 4 unidades.
 * Cada unidad contiene una lista de semanas (relación 1:N).
 *
 * Principios POO aplicados:
 *   - Encapsulamiento: atributos privados
 *   - Composición: contiene lista de Semanas
 *
 * @author HiPortafolio
 * @version 1.0
 */
public class Unidad implements Serializable {

    private static final long serialVersionUID = 1L;

    private int           idUnidad;
    private int           numero;
    private String        titulo;
    private String        descripcion;
    private boolean       estado;
    private LocalDateTime fechaCreacion;

    /** Semanas de esta unidad (cargadas bajo demanda) */
    private List<Semana>  semanas;

    public Unidad() {
        this.semanas = new ArrayList<>();
    }

    public Unidad(int idUnidad, int numero, String titulo, String descripcion, boolean estado) {
        this.idUnidad    = idUnidad;
        this.numero      = numero;
        this.titulo      = titulo;
        this.descripcion = descripcion;
        this.estado      = estado;
        this.semanas     = new ArrayList<>();
    }

    /**
     * Retorna el título formateado con el número de unidad.
     * Ejemplo: "UNIDAD I - Fundamentos de la Arquitectura..."
     */
    public String getTituloCompleto() {
        return "UNIDAD " + numeroARomano() + " - " + titulo;
    }

    /**
     * Convierte el número de unidad a su representación romana.
     * Disponible como getter para EL: ${unidad.numeroARomano}
     */
    public String getNumeroARomano() {
        return switch (numero) {
            case 1 -> "I";
            case 2 -> "II";
            case 3 -> "III";
            case 4 -> "IV";
            default -> String.valueOf(numero);
        };
    }

    /** @deprecated usar getNumeroARomano() */
    public String numeroARomano() { return getNumeroARomano(); }

    /**
     * Retorna la cantidad de semanas asociadas a esta unidad.
     */
    public int getCantidadSemanas() {
        return semanas != null ? semanas.size() : 0;
    }

    // Getters y Setters
    public int    getIdUnidad()        { return idUnidad; }
    public void   setIdUnidad(int v)   { this.idUnidad = v; }

    public int    getNumero()          { return numero; }
    public void   setNumero(int v)     { this.numero = v; }

    public String getTitulo()          { return titulo; }
    public void   setTitulo(String v)  { this.titulo = v; }

    public String getDescripcion()         { return descripcion; }
    public void   setDescripcion(String v) { this.descripcion = v; }

    public boolean isEstado()          { return estado; }
    public void    setEstado(boolean v){ this.estado = v; }

    public LocalDateTime getFechaCreacion()               { return fechaCreacion; }
    public void          setFechaCreacion(LocalDateTime v){ this.fechaCreacion = v; }

    public List<Semana> getSemanas()              { return semanas; }
    public void         setSemanas(List<Semana> v){ this.semanas = v; }

    @Override
    public String toString() {
        return "Unidad{id=" + idUnidad + ", numero=" + numero + ", titulo='" + titulo + "'}";
    }
}
