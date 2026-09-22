@echo off
echo Ejecutando fix de datos en MySQL...
"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -p hiportafolio < "%~dp0fix-datos.sql"
if errorlevel 1 (
    echo ERROR al ejecutar el script SQL
    pause
) else (
    echo.
    echo EXITO - Datos corregidos:
    echo   - Tecnologias activadas
    echo   - Proyectos activados
    echo   - Unidades y semanas activadas
    echo   - Contrasenas actualizadas
    echo.
    echo Credenciales:
    echo   admin@hiportafolio.com   ^| Admin123!
    echo   usuario@hiportafolio.com ^| Usuario123!
)
pause
