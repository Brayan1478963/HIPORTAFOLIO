-- HiPortafolio: compatibilidad no destructiva con los DAO Java actuales.
-- Ejecutar una sola vez en Supabase > SQL Editor.
-- No elimina tablas ni registros. No ejecutar database.sql completo porque contiene DROP TABLE.

BEGIN;

-- Contenidos: columnas que el modelo y ContenidoDAO esperan.
ALTER TABLE public.contenidos ADD COLUMN IF NOT EXISTS descripcion TEXT;
ALTER TABLE public.contenidos ADD COLUMN IF NOT EXISTS contenido TEXT;
ALTER TABLE public.contenidos ADD COLUMN IF NOT EXISTS aprendizaje TEXT;
ALTER TABLE public.contenidos ADD COLUMN IF NOT EXISTS reflexion TEXT;
ALTER TABLE public.contenidos ADD COLUMN IF NOT EXISTS referencias TEXT;
ALTER TABLE public.contenidos ADD COLUMN IF NOT EXISTS estado SMALLINT NOT NULL DEFAULT 1;
ALTER TABLE public.contenidos ADD COLUMN IF NOT EXISTS fecha_creacion TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP;
ALTER TABLE public.contenidos ADD COLUMN IF NOT EXISTS fecha_actualizacion TIMESTAMP WITHOUT TIME ZONE;

-- Archivos: conservar nombres de columnas originales y agregar los alias esperados por Java.
ALTER TABLE public.archivos ADD COLUMN IF NOT EXISTS nombre_sistema VARCHAR(255);
ALTER TABLE public.archivos ADD COLUMN IF NOT EXISTS tipo_mime VARCHAR(100);
ALTER TABLE public.archivos ADD COLUMN IF NOT EXISTS tamano BIGINT;
ALTER TABLE public.archivos ADD COLUMN IF NOT EXISTS id_usuario INTEGER;
ALTER TABLE public.archivos ADD COLUMN IF NOT EXISTS estado SMALLINT NOT NULL DEFAULT 1;

UPDATE public.archivos
SET nombre_sistema = COALESCE(nombre_sistema, nombre_guardado),
    tipo_mime = COALESCE(tipo_mime, tipo),
    tamano = COALESCE(tamano, tamanio)
WHERE nombre_sistema IS NULL OR tipo_mime IS NULL OR tamano IS NULL;

-- Evidencias: columnas que espera EvidenciaDAO.
ALTER TABLE public.evidencias ADD COLUMN IF NOT EXISTS id_archivo INTEGER;
ALTER TABLE public.evidencias ADD COLUMN IF NOT EXISTS tipo VARCHAR(100);
ALTER TABLE public.evidencias ADD COLUMN IF NOT EXISTS fecha_evidencia DATE;

-- Crear FK solo si todavía no existe.
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM pg_constraint
        WHERE conname = 'evidencias_id_archivo_fkey'
          AND conrelid = 'public.evidencias'::regclass
    ) THEN
        ALTER TABLE public.evidencias
            ADD CONSTRAINT evidencias_id_archivo_fkey
            FOREIGN KEY (id_archivo)
            REFERENCES public.archivos(id_archivo)
            ON DELETE SET NULL;
    END IF;
END $$;

COMMIT;
