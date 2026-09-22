package com.hiportafolio.service;

import com.hiportafolio.dao.ArchivoDAO;
import com.hiportafolio.exception.ApplicationException;
import com.hiportafolio.exception.DatabaseException;
import com.hiportafolio.model.Archivo;
import com.hiportafolio.util.Constants;
import com.hiportafolio.util.FileUtil;
import com.hiportafolio.util.ValidationUtil;

import jakarta.servlet.http.Part;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * ArchivoService - Lógica de negocio para gestión de archivos.
 *
 * Responsabilidad: Orquestar la subida, validación y eliminación de archivos.
 * Coordina entre FileUtil (manejo físico) y ArchivoDAO (metadatos en DB).
 *
 * @author HiPortafolio
 * @version 1.0
 */
public class ArchivoService {

    private static final Logger LOGGER = Logger.getLogger(ArchivoService.class.getName());
    private final ArchivoDAO archivoDAO = new ArchivoDAO();

    public List<Archivo> listar() throws ApplicationException {
        try {
            return archivoDAO.listar();
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al listar archivos", e);
            throw new ApplicationException("Error al obtener los archivos.", e);
        }
    }

    public Archivo buscarPorId(int idArchivo) throws ApplicationException {
        try {
            Archivo a = archivoDAO.buscarPorId(idArchivo);
            if (a == null) {
                throw new ApplicationException(
                    ApplicationException.ENTITY_NOT_FOUND,
                    "No se encontró el archivo con ID: " + idArchivo
                );
            }
            return a;
        } catch (ApplicationException e) {
            throw e;
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar archivo", e);
            throw new ApplicationException("Error al buscar el archivo.", e);
        }
    }

    /**
     * Sube un archivo al servidor y guarda sus metadatos en la base de datos.
     *
     * Flujo:
     *   1. Validar extensión y MIME type
     *   2. Validar tamaño
     *   3. Guardar archivo físico en uploads/
     *   4. Guardar metadatos en MySQL
     *
     * @param part        parte multipart del formulario
     * @param uploadPath  ruta absoluta del directorio destino
     * @param idUsuario   ID del usuario que sube el archivo
     * @param subdir      subdirectorio relativo (evidencias, proyectos, documentos)
     * @return objeto Archivo guardado con su ID
     */
    public Archivo subirArchivo(Part part, String uploadPath, int idUsuario, String subdir)
            throws ApplicationException {

        // 1. Obtener datos del archivo
        String nombreOriginal = FileUtil.obtenerNombreOriginal(part);
        String mimeType       = part.getContentType();
        long   tamano         = part.getSize();

        // 2. Validar extensión permitida
        if (!ValidationUtil.esExtensionPermitida(nombreOriginal)) {
            throw new ApplicationException(
                ApplicationException.FILE_INVALID_TYPE,
                Constants.ERR_FILE_TIPO + " Extensiones permitidas: pdf, doc, docx, xls, jpg, png, zip..."
            );
        }

        // 3. Validar tipo MIME
        if (!ValidationUtil.esMimePermitido(mimeType)) {
            throw new ApplicationException(
                ApplicationException.FILE_INVALID_TYPE,
                Constants.ERR_FILE_TIPO
            );
        }

        // 4. Validar tamaño
        if (tamano > Constants.FILE_MAX_SIZE_BYTES) {
            throw new ApplicationException(
                ApplicationException.FILE_TOO_LARGE,
                Constants.ERR_FILE_TAMANO
            );
        }

        try {
            // 5. Guardar físicamente
            String nombreSistema = FileUtil.guardarArchivo(part, uploadPath);
            String ruta          = subdir + "/" + nombreSistema;

            // 6. Persistir metadatos
            Archivo archivo = new Archivo();
            archivo.setNombreOriginal(nombreOriginal);
            archivo.setNombreSistema(nombreSistema);
            archivo.setRuta(ruta);
            archivo.setTipoMime(mimeType);
            archivo.setTamano(tamano);
            archivo.setIdUsuario(idUsuario);
            archivo.setEstado(true);

            int idGenerado = archivoDAO.guardar(archivo);
            archivo.setIdArchivo(idGenerado);
            LOGGER.info("Archivo subido: " + nombreOriginal + " → " + nombreSistema);
            return archivo;

        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error al escribir archivo en disco", e);
            throw new ApplicationException("Error al guardar el archivo en el servidor.", e);
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al guardar metadatos de archivo", e);
            throw new ApplicationException("Error al registrar el archivo en la base de datos.", e);
        }
    }

    /**
     * Elimina un archivo: borra el registro de la BD y el archivo físico del disco.
     *
     * @param idArchivo  ID del archivo a eliminar
     * @param uploadRoot ruta raíz del directorio uploads en el servidor
     */
    public void eliminarArchivo(int idArchivo, String uploadRoot) throws ApplicationException {
        try {
            Archivo archivo = buscarPorId(idArchivo);

            // Eliminar físicamente del disco
            String rutaAbsoluta = uploadRoot + archivo.getRuta().replace("/", java.io.File.separator);
            boolean eliminado = FileUtil.eliminarArchivo(rutaAbsoluta);
            if (!eliminado) {
                LOGGER.warning("Archivo físico no encontrado al eliminar: " + rutaAbsoluta);
            }

            // Eliminar metadatos de la BD
            archivoDAO.eliminar(idArchivo);
            LOGGER.info("Archivo eliminado: id=" + idArchivo);

        } catch (ApplicationException e) {
            throw e;
        } catch (DatabaseException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar archivo de BD", e);
            throw new ApplicationException("Error al eliminar el archivo.", e);
        }
    }

    public int contar() throws ApplicationException {
        try {
            return archivoDAO.contar();
        } catch (DatabaseException e) {
            throw new ApplicationException("Error al contar archivos.", e);
        }
    }
}
