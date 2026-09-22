@echo off
echo Actualizando contraseñas en MySQL...
"C:\Program Files\MySQL\MySQL Server 8.0\bin\mysql.exe" -u root -p hiportafolio < "%~dp0actualizar-passwords.sql"
if errorlevel 1 (
    echo ERROR al ejecutar SQL
) else (
    echo EXITO - Contraseñas actualizadas
    echo.
    echo Ahora puedes iniciar sesion con:
    echo   Admin:   admin@hiportafolio.com / Admin123!
    echo   Usuario: usuario@hiportafolio.com / Usuario123!
)
pause
