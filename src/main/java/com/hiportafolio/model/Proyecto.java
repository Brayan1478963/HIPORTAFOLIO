package com.hiportafolio.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Proyecto - Entidad que representa un proyecto académico del portafolio.
 *
 * Un proyecto puede estar asociado a múltiples tecnologías
 * mediante la tabla intermedia proyecto_tecnologia (relación N:M).
 *
 * @author HiPortafolio
 * @version 1.0
 */
public class Proyecto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int           idProyecto;
    private String        nombre;
    private String        descripcion;
    private String        objetivo;
    private String        problema;
    private String        arquitectura;
    private String        funcionalidades;
    private String        repositorio;
    private String        demo;
    private String        imagen;
    private String        estado;           // en_desarrollo, completado, pausado
    private LocalDate     fechaInicio;
    private LocalDate     fechaFin;
    private int           orden;
    private boolean       activo;
    private LocalDateTime fechaCreacion;

    /** Tecnologías del proyecto (relación N:M, cargada bajo demanda) */
    private List<Tecnologia> tecnologias;

    public Proyecto() {
        this.tecnologias = new ArrayList<>();
    }

    public Proyecto(int idProyecto, String nombre, String descripcion,
                    String objetivo, String arquitectura, String estado,
                    String repositorio, String demo, boolean activo) {
        this.idProyecto   = idProyecto;
        this.nombre       = nombre;
        this.descripcion  = descripcion;
        this.objetivo     = objetivo;
        this.arquitectura = arquitectura;
        this.estado       = estado;
        this.repositorio  = repositorio;
        this.demo         = demo;
        this.activo       = activo;
        this.tecnologias  = new ArrayList<>();
    }

    /**
     * Retorna la etiqueta de estado formateada para la UI.
     */
    public String getEstadoEtiqueta() {
        if (estado == null) return "Sin estado";
        return switch (estado.toLowerCase()) {
            case "completado"    -> "Completado";
            case "en_desarrollo" -> "En Desarrollo";
            case "pausado"       -> "Pausado";
            default              -> estado;
        };
    }

    /**
     * Retorna la clase CSS del badge de estado para Bootstrap.
     */
    public String getEstadoBadgeClase() {
        if (estado == null) return "bg-secondary";
        return switch (estado.toLowerCase()) {
            case "completado"    -> "bg-success";
            case "en_desarrollo" -> "bg-primary";
            case "pausado"       -> "bg-warning text-dark";
            default              -> "bg-secondary";
        };
    }

    /**
     * Verifica si el proyecto tiene repositorio definido.
     */
    public boolean tieneRepositorio() {
        return repositorio != null && !repositorio.trim().isEmpty();
    }

    /**
     * Verifica si el proyecto tiene demo disponible.
     */
    public boolean tieneDemo() {
        return demo != null && !demo.trim().isEmpty();
    }

    /**
     * Verifica si el proyecto tiene imagen de portada.
     */
    public boolean tieneImagen() {
        return imagen != null && !imagen.trim().isEmpty();
    }

    // Getters y Setters
    public int    getIdProyecto()          { return idProyecto; }
    public void   setIdProyecto(int v)     { this.idProyecto = v; }

    public String getNombre()              { return nombre; }
    public void   setNombre(String v)      { this.nombre = v; }

    public String getDescripcion()             { return descripcion; }
    public void   setDescripcion(String v)     { this.descripcion = v; }

    public String getObjetivo()            { return objetivo; }
    public void   setObjetivo(String v)    { this.objetivo = v; }

    public String getProblema()            { return problema; }
    public void   setProblema(String v)    { this.problema = v; }

    public String getArquitectura()            { return arquitectura; }
    public void   setArquitectura(String v)    { this.arquitectura = v; }

    public String getFuncionalidades()             { return funcionalidades; }
    public void   setFuncionalidades(String v)     { this.funcionalidades = v; }

    public String getRepositorio()             { return repositorio; }
    public void   setRepositorio(String v)     { this.repositorio = v; }

    public String getDemo()                { return demo; }
    public void   setDemo(String v)        { this.demo = v; }

    public String getImagen()              { return imagen; }
    public void   setImagen(String v)      { this.imagen = v; }

    public String getEstado()              { return estado; }
    public void   setEstado(String v)      { this.estado = v; }

    public LocalDate getFechaInicio()              { return fechaInicio; }
    public void      setFechaInicio(LocalDate v)   { this.fechaInicio = v; }

    public LocalDate getFechaFin()                 { return fechaFin; }
    public void      setFechaFin(LocalDate v)      { this.fechaFin = v; }

    public int    getOrden()               { return orden; }
    public void   setOrden(int v)          { this.orden = v; }

    public boolean isActivo()              { return activo; }
    public void    setActivo(boolean v)    { this.activo = v; }

    public LocalDateTime getFechaCreacion()               { return fechaCreacion; }
    public void          setFechaCreacion(LocalDateTime v){ this.fechaCreacion = v; }

    public List<Tecnologia> getTecnologias()               { return tecnologias; }
    public void             setTecnologias(List<Tecnologia> v){ this.tecnologias = v; }

    @Override
    public String toString() {
        return "Proyecto{id=" + idProyecto + ", nombre='" + nombre +
               "', estado='" + estado + "'}";
    }
}
