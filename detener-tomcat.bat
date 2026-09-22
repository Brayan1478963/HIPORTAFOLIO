@echo off
echo Deteniendo Tomcat...
set JAVA_HOME=C:\Program Files\Java\jdk-22
set PATH=%JAVA_HOME%\bin;%PATH%
call C:\tomcat\bin\shutdown.bat
echo Tomcat detenido.
pause
