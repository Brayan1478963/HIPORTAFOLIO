package com.hiportafolio.util;

import jakarta.servlet.http.Part;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * FileUtil - Utilidades para la gestión de archivos subidos al servidor.
 *
 * Responsabilidad: Encapsular toda la lógica de manejo físico de archivos:
 * guardado, eliminación, generación de nombres únicos y obtención de rutas.
 *
 * Los archivos se almacenan en el directorio uploads/ organizado por tipo.
 * El nombre interno siempre es único (UUID + timestamp) para evitar colisiones.
 *
 * @author HiPortafolio
 * @version 1.0
 */
public final class FileUtil {

    private static final Logger LOGGER = Logger.getLogger(FileUtil.class.getName());

    private FileUtil() {
        throw new UnsupportedOperationException("Clase utilitaria, no instanciar.");
    }

    /**
     * Guarda un archivo recibido desde un formulario multipart en el servidor.
     *
     * @param part           parte del formulario (archivo subido)
     * @param uploadDirPath  ruta absoluta del directorio destino
     * @return nombre único generado para el archivo (para guardar en DB)
     * @throws IOException si ocurre un error al escribir el archivo
     */
    public static String guardarArchivo(Part part, String uploadDirPath) throws IOException {
        // Crear directorio si no existe
        File uploadDir = new File(uploadDirPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Generar nombre único: UUID + timestamp + extensión original
        String nombreOriginal = obtenerNombreOriginal(part);
        String extension      = ValidationUtil.obtenerExtension(nombreOriginal);
        String nombreSistema  = generarNombreUnico(extension);

        // Guardar el archivo
        Path destino = Paths.get(uploadDirPath, nombreSistema);
        try (InputStream inputStream = part.getInputStream()) {
            Files.copy(inputStream, destino, StandardCopyOption.REPLACE_EXISTING);
        }

        LOGGER.info("Archivo guardado: " + destino.toString());
        return nombreSistema;
    }

    /**
     * Elimina un archivo del sistema de archivos del servidor.
     *
     * @param rutaAbsoluta ruta completa del archivo a eliminar
     * @return true si se eliminó correctamente, false si no existía o hubo error
     */
    public static boolean eliminarArchivo(String rutaAbsoluta) {
        try {
            Path path = Paths.get(rutaAbsoluta);
            return Files.deleteIfExists(path);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, "Error al eliminar archivo: " + rutaAbsoluta, e);
            return false;
        }
    }

    /**
     * Genera un nombre único para almacenar el archivo en el servidor.
     * Formato: UUID + "_" + timestamp + "." + extensión
     *
     * @param extension extensión del archivo (sin punto)
     * @return nombre único para el archivo
     */
    public static String generarNombreUnico(String extension) {
        String uuid      = UUID.randomUUID().toString().replace("-", "");
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        if (extension == null || extension.isEmpty()) {
            return uuid + "_" + timestamp;
        }
        return uuid + "_" + timestamp + "." + extension;
    }

    /**
     * Obtiene el nombre original del archivo subido desde el header Content-Disposition.
     *
     * @param part parte multipart del formulario
     * @return nombre original del archivo, o "archivo_sin_nombre" si no se puede determinar
     */
    public static String obtenerNombreOriginal(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        if (contentDisposition == null) return "archivo_sin_nombre";

        for (String token : contentDisposition.split(";")) {
            if (token.trim().startsWith("filename")) {
                String nombre = token.substring(token.indexOf('=') + 1).trim()
                                     .replace("\"", "");
                // Retornar solo el nombre del archivo (sin ruta completa en Windows)
                return Paths.get(nombre).getFileName().toString();
            }
        }
        return "archivo_sin_nombre";
    }

    /**
     * Construye la ruta absoluta del directorio de uploads en el servidor.
     *
     * @param realPath    ruta real de la webapp (obtenida con servletContext.getRealPath("/"))
     * @param subDirectorio subdirectorio dentro de uploads (ej: "evidencias")
     * @return ruta absoluta del directorio
     */
    public static String getRutaUpload(String realPath, String subDirectorio) {
        return realPath + File.separator + "uploads" + File.separator + subDirectorio + File.separator;
    }

    /**
     * Verifica que un archivo existe en el sistema.
     *
     * @param rutaAbsoluta ruta completa del archivo
     * @return true si existe y es un archivo regular
     */
    public static boolean existeArchivo(String rutaAbsoluta) {
        if (rutaAbsoluta == null || rutaAbsoluta.isEmpty()) return false;
        File file = new File(rutaAbsoluta);
        return file.exists() && file.isFile();
    }

    /**
     * Determina el directorio de upload según el tipo de evidencia.
     *
     * @param tipo tipo de evidencia ("imagen", "pdf", "documento", etc.)
     * @return subdirectorio apropiado dentro de uploads/
     */
    public static String getSubdirectorioPorTipo(String tipo) {
        if (tipo == null) return "documentos";
        return switch (tipo.toLowerCase()) {
            case "imagen", "captura", "diagrama" -> "evidencias";
            case "pdf", "documento"              -> "documentos";
            default                              -> "documentos";
        };
    }

    /**
     * Formatea el tamaño de un archivo en unidades legibles (B, KB, MB).
     *
     * @param bytes tamaño en bytes
     * @return String con formato legible (ej: "2.3 MB")
     */
    public static String formatearTamano(long bytes) {
        if (bytes < 1024)        return bytes + " B";
        if (bytes < 1024 * 1024) return String.format("%.1f KB", bytes / 1024.0);
        return String.format("%.1f MB", bytes / (1024.0 * 1024));
    }

    /**
     * Determina si un tipo MIME corresponde a una imagen.
     *
     * @param mimeType tipo MIME del archivo
     * @return true si es imagen
     */
    public static boolean esImagen(String mimeType) {
        return mimeType != null && mimeType.startsWith("image/");
    }

    /**
     * Determina si un tipo MIME corresponde a un PDF.
     *
     * @param mimeType tipo MIME del archivo
     * @return true si es PDF
     */
    public static boolean esPdf(String mimeType) {
        return "application/pdf".equalsIgnoreCase(mimeType);
    }
}
