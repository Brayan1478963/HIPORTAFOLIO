@echo off
setlocal

echo =============================================
echo  HiPortafolio - Compilar y Desplegar
echo =============================================
echo.

:: ==================================================
:: CONFIGURACION
:: ==================================================

set "JAVA_HOME=C:\Program Files\Java\jdk-26.0.2.1"
set "MAVEN_HOME=C:\maven"
set "CATALINA_HOME=C:\tomcat"
if not defined DB_PASSWORD set /p "DB_PASSWORD=Contraseña de MySQL: "

set "PATH=%JAVA_HOME%\bin;%MAVEN_HOME%\bin;%PATH%"

:: ==================================================
:: COMPROBAR JAVA
:: ==================================================

if not exist "%JAVA_HOME%\bin\java.exe" (
    echo ERROR: No se encontro Java en:
    echo %JAVA_HOME%
    echo.
    pause
    exit /b 1
)

:: ==================================================
:: COMPROBAR MAVEN
:: ==================================================

if not exist "%MAVEN_HOME%\bin\mvn.cmd" (
    echo ERROR: No se encontro Maven en:
    echo %MAVEN_HOME%
    echo.
    pause
    exit /b 1
)

:: ==================================================
:: COMPROBAR TOMCAT
:: ==================================================

if not exist "%CATALINA_HOME%\bin\startup.bat" (
    echo ERROR: No se encontro Tomcat en:
    echo %CATALINA_HOME%
    echo.
    pause
    exit /b 1
)

:: ==================================================
:: VARIABLES PARA TOMCAT
:: ==================================================

echo Configurando variables de Tomcat...

(
echo @echo off
echo set "JAVA_HOME=C:\Program Files\Java\jdk-26.0.2.1"
echo set "DB_HOST=localhost"
echo set "DB_PORT=3307"
echo set "DB_NAME=hiportafolio"
echo set "DB_USER=root"
echo set "DB_PASSWORD=%DB_PASSWORD%"
) > "%CATALINA_HOME%\bin\setenv.bat"

echo     Variables configuradas OK
echo.

:: ==================================================
:: 1. COMPILAR CON MAVEN
:: ==================================================

echo [1] Compilando con Maven...

cd /d "%~dp0"

call "%MAVEN_HOME%\bin\mvn.cmd" clean package -DskipTests -q

if errorlevel 1 (
    echo.
    echo =============================================
    echo ERROR: Compilacion fallida
    echo =============================================
    echo.
    
    call "%MAVEN_HOME%\bin\mvn.cmd" clean package -DskipTests
    
    echo.
    pause
    exit /b 1
)

echo     BUILD OK
echo.

:: ==================================================
:: COMPROBAR WAR
:: ==================================================

if not exist "%~dp0target\HiPortafolio.war" (
    echo ERROR: No se encontro:
    echo target\HiPortafolio.war
    echo.
    pause
    exit /b 1
)

:: ==================================================
:: 2. DETENER TOMCAT
:: ==================================================

echo [2] Deteniendo Tomcat previo...

call "%CATALINA_HOME%\bin\shutdown.bat" >nul 2>&1

timeout /t 4 /nobreak >nul

echo     Tomcat detenido
echo.

:: ==================================================
:: 3. LIMPIAR DESPLIEGUE ANTERIOR
:: ==================================================

echo [3] Limpiando despliegue anterior...

if exist "%CATALINA_HOME%\webapps\ROOT" (
    rmdir /s /q "%CATALINA_HOME%\webapps\ROOT"
)

if exist "%CATALINA_HOME%\webapps\ROOT.war" (
    del /f /q "%CATALINA_HOME%\webapps\ROOT.war"
)

echo     Limpieza OK
echo.

:: ==================================================
:: 4. COPIAR NUEVO WAR
:: ==================================================

echo [4] Copiando nuevo WAR...

copy /y "%~dp0target\HiPortafolio.war" "%CATALINA_HOME%\webapps\ROOT.war" >nul

if errorlevel 1 (
    echo.
    echo ERROR: No se pudo copiar el WAR.
    echo.
    pause
    exit /b 1
)

echo     WAR copiado OK
echo.

:: ==================================================
:: 5. INICIAR TOMCAT
:: ==================================================

echo [5] Iniciando Tomcat...

call "%CATALINA_HOME%\bin\startup.bat"

if errorlevel 1 (
    echo.
    echo =============================================
    echo ERROR: Tomcat no pudo iniciar
    echo =============================================
    echo.
    echo JAVA_HOME:
    echo %JAVA_HOME%
    echo.
    pause
    exit /b 1
)

echo.
echo     Esperando 10 segundos...
timeout /t 10 /nobreak >nul

:: ==================================================
:: 6. ABRIR NAVEGADOR
:: ==================================================

echo.
echo =============================================
echo  LISTO - Abriendo navegador
echo =============================================
echo.

start "" "http://localhost:8080"

echo.
echo URL:
echo   http://localhost:8080
echo.
echo Credenciales:
echo   Admin:   admin@hiportafolio.com / Admin123!
echo   Usuario: usuario@hiportafolio.com / Usuario123!
echo.
echo =============================================
echo.

pause

endlocal