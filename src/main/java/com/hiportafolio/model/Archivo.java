package com.hiportafolio.model;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Archivo - Entidad que representa los metadatos de un archivo subido al sistema.
 *
 * El archivo físico se almacena en uploads/ y sus metadatos en MySQL.
 * Separar metadatos del archivo físico es una buena práctica de arquitectura:
 * permite mover/reorganizar archivos sin romper referencias en la DB.
 *
 * @author HiPortafolio
 * @version 1.0
 */
public class Archivo implements Serializable {

    private static final long serialVersionUID = 1L;

    private int           idArchivo;
    private String        nombreOriginal;   // nombre que subió el usuario
    private String        nombreSistema;    // nombre UUID interno
    private String        ruta;             // ruta relativa en el servidor
    private String        tipoMime;
    private long          tamano;           // en bytes
    private int           idUsuario;
    private String        nombreUsuario;    // campo calculado (JOIN)
    private boolean       estado;
    private LocalDateTime fechaSubida;

    public Archivo() {}

    public Archivo(int idArchivo, String nombreOriginal, String nombreSistema,
                   String ruta, String tipoMime, long tamano, int idUsuario,
                   boolean estado, LocalDateTime fechaSubida) {
        this.idArchivo      = idArchivo;
        this.nombreOriginal = nombreOriginal;
        this.nombreSistema  = nombreSistema;
        this.ruta           = ruta;
        this.tipoMime       = tipoMime;
        this.tamano         = tamano;
        this.idUsuario      = idUsuario;
        this.estado         = estado;
        this.fechaSubida    = fechaSubida;
    }

    /**
     * Retorna el tamaño formateado en unidades legibles (B, KB, MB).
     */
    public String getTamanoFormateado() {
        if (tamano < 1024)        return tamano + " B";
        if (tamano < 1024 * 1024) return String.format("%.1f KB", tamano / 1024.0);
        return String.format("%.1f MB", tamano / (1024.0 * 1024));
    }

    /**
     * Verifica si el archivo es una imagen según su MIME type.
     */
    public boolean esImagen() {
        return tipoMime != null && tipoMime.startsWith("image/");
    }

    /**
     * Verifica si el archivo es un PDF.
     */
    public boolean esPdf() {
        return "application/pdf".equalsIgnoreCase(tipoMime);
    }

    /**
     * Retorna la extensión del archivo basándose en el nombre original.
     */
    public String getExtension() {
        if (nombreOriginal == null) return "";
        int idx = nombreOriginal.lastIndexOf('.');
        return idx >= 0 ? nombreOriginal.substring(idx + 1).toLowerCase() : "";
    }

    /**
     * Retorna el ícono Bootstrap/FontAwesome apropiado según el tipo de archivo.
     */
    public String getIconoTipo() {
        if (esImagen()) return "bi bi-file-image";
        if (esPdf())    return "bi bi-file-pdf";
        String ext = getExtension();
        return switch (ext) {
            case "doc", "docx" -> "bi bi-file-word";
            case "xls", "xlsx" -> "bi bi-file-excel";
            case "ppt", "pptx" -> "bi bi-file-ppt";
            case "zip"         -> "bi bi-file-zip";
            case "txt"         -> "bi bi-file-text";
            default            -> "bi bi-file-earmark";
        };
    }

    // Getters y Setters
    public int    getIdArchivo()           { return idArchivo; }
    public void   setIdArchivo(int v)      { this.idArchivo = v; }

    public String getNombreOriginal()           { return nombreOriginal; }
    public void   setNombreOriginal(String v)   { this.nombreOriginal = v; }

    public String getNombreSistema()            { return nombreSistema; }
    public void   setNombreSistema(String v)    { this.nombreSistema = v; }

    public String getRuta()              { return ruta; }
    public void   setRuta(String v)      { this.ruta = v; }

    public String getTipoMime()          { return tipoMime; }
    public void   setTipoMime(String v)  { this.tipoMime = v; }

    public long   getTamano()            { return tamano; }
    public void   setTamano(long v)      { this.tamano = v; }

    public int    getIdUsuario()         { return idUsuario; }
    public void   setIdUsuario(int v)    { this.idUsuario = v; }

    public String getNombreUsuario()          { return nombreUsuario; }
    public void   setNombreUsuario(String v)  { this.nombreUsuario = v; }

    public boolean isEstado()            { return estado; }
    public void    setEstado(boolean v)  { this.estado = v; }

    public LocalDateTime getFechaSubida()               { return fechaSubida; }
    public void          setFechaSubida(LocalDateTime v){ this.fechaSubida = v; }

    @Override
    public String toString() {
        return "Archivo{id=" + idArchivo + ", nombre='" + nombreOriginal +
               "', mime='" + tipoMime + "', tamano=" + getTamanoFormateado() + "}";
    }
}
