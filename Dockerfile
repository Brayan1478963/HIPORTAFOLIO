# =============================================
# HiPortafolio - Dockerfile
# Imagen: Tomcat 10.1 con Java 17
# =============================================

FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /build
COPY pom.xml .
RUN mvn dependency:go-offline -B
COPY src ./src
RUN mvn clean package -DskipTests -B

FROM tomcat:10.1-jdk17-temurin

LABEL maintainer="HiPortafolio - UPLA Arquitectura de Software"
LABEL description="Sistema Web de Portafolio Académico"

# Eliminar aplicaciones por defecto de Tomcat
RUN rm -rf /usr/local/tomcat/webapps/*

# Copiar el WAR generado en la etapa de compilación
COPY --from=build /build/target/HiPortafolio.war /usr/local/tomcat/webapps/ROOT.war

# Crear directorio de uploads con permisos
RUN mkdir -p /usr/local/tomcat/webapps/uploads/evidencias \
             /usr/local/tomcat/webapps/uploads/proyectos \
             /usr/local/tomcat/webapps/uploads/documentos && \
    chmod -R 755 /usr/local/tomcat/webapps/uploads

# Railway asigna PORT dinámicamente; 8080 queda como fallback local.
ENV PORT=8080

# Variables de entorno de base de datos (sobreescribir en docker-compose)
ENV DB_HOST=mysql
ENV DB_PORT=3306
ENV DB_NAME=hiportafolio
ENV DB_USER=hiportafolio_user
ENV DB_PASSWORD=hiportafolio_pass

# Exponer puerto 8080
EXPOSE 8080

# Iniciar Tomcat en el puerto asignado por Railway.
CMD ["sh", "-c", "sed -i \"s/port=\\\"8080\\\"/port=\\\"${PORT:-8080}\\\"/\" \"$CATALINA_HOME/conf/server.xml\" && exec catalina.sh run"]
