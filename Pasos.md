# Comercio

## Descripción

Comercio que vende productos, cada uno tiene su presentación.

## Pasos

1. Crear el proyecto usando Spring Initializr en VS Code.

2. Crear base de datos vacía llamada "comercio" en MySQL.

3. Rellenar src\main\resources\application.properties.

4. Siguiendo patrón de diseño Facade: Crear paquete para entidades y dentro las clases (Producto y Presentacion). Cada entidad será una tabla en BD.

5. Relacionar bidireccionalmente las entidades/tablas BD Producto y Presentacion: many (Producto) to one (Presentacion)

6. Ejecutar el proyecto. Se crearán las tablas "producto" y "presentacion" en la BD "comercio" en MySQL. Comprobarlo. Para ejecutar el proyecto Spring Boot, previamente hay que tener las variables de entorno en Windows de Java ("JAVA_HOME" apuntado al JDK) y Maven ("MAVEN_HOME" apuntado al directorio donde hemos descargado Maven).

7. Añadir la capa DAO.
