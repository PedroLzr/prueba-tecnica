# Prueba Técnica
Prueba técnica para vacante de Lider Técnico.

## **Ejecutar en Docker**

Los siguientes comandos se deben ejecutar desde la ruta root del repositorio.

1. Construir la imagen:
```bash
docker build -t pruebatec-app .
```

2. Ejecutar el contenedor:
```bash
docker run -p 8080:8080 pruebatec-app
```

**URLs de interés:**

Swagger UI: http://localhost:8080/swagger-ui/index.html

ApiDocs: http://localhost:8080/v3/api-docs

Consola H2: http://localhost:8080/h2-console

## Tests

### Con Docker:

Desde la ruta root del repositorio:

**Ejecutar tests unitarios:**
```bash
docker run -it --rm -v $(pwd)/project:/app -w /app maven:3.9.5-eclipse-temurin-21 mvn test -Punit-tests
```

**Ejecutar tests funcionales:**
```bash
docker run -it --rm -v $(pwd)/project:/app -w /app maven:3.9.5-eclipse-temurin-21 mvn test -Pfunctional-tests
```

*Para que los tests E2E desde un contenedor puedan comunicarse con la aplicación en otro, sería necesaria una red personalizada en Docker, ya que los contenedores no comparten localhost.
Para evitar crear una red personalizada, a continuación se muestra como ejecutar todos los tests con mvn (sin usar Docker).

### Sin Docker:

Ejecutar estos comandos desde la ruta del proyecto de maven (`project`).

**Ejecutar tests unitarios:**

```bash
mvn test -Punit-tests
```

**Ejecutar tests funcionales:**

```bash
mvn test -Pfunctional-tests
```

**Ejecutar tests e2e:**

(Para ejecutar los tests e2e es necesario que la aplicación esté en ejecución)

1. Iniciar la aplicación
```bash
mvn spring-boot:run -DskipTests
```
2. Ejecutar tests e2e
```bash
mvn test -Pe2e-tests
```

**Ejecutar todos los tests a la vez:**
```bash
mvn test -Pall-tests
```
Este perfil requiere también que la aplicación esté en ejecución (porque ejecuta también los tests e2e)

## Reporte de cobertura de pruebas

Para generar el reporte de cobertura de JaCoCo, tenemos que ejecutar:

```sh
mvn clean verify
```

**Se nos generará un archivo con los datos de la cobertura en:** target/site/jacoco/index.html

## Más detalles

### Arquitectura del Proyecto

El proyecto sigue una arquitectura basada en **hexagonal/puertos y adaptadores**, que permite un diseño modular y desacoplado. Esto se refleja en la estructura de carpetas:

- **domain**: Contiene la lógica central del negocio (modelo `Price` y su repositorio).
- **application**: Implementa los casos de uso del negocio, como la aplicación de reglas para determinar el precio correcto.
- **infrastructure**: Contiene detalles técnicos como controladores REST, persistencia en la base de datos y DTOs.

Este enfoque asegura que el núcleo del negocio no dependa de detalles externos (por ejemplo, la base de datos o el framework web).

### Documentación de la API

La API está documentada utilizando **Springdoc OpenAPI**. Esto permite explorar y probar los endpoints directamente desde una interfaz web.

- **Swagger UI**: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- **API Docs (JSON)**: [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

### Base de Datos

El proyecto utiliza **H2 Database** como base de datos en memoria. Esto simplifica las pruebas y garantiza que la aplicación sea completamente autónoma.

- **URL de la consola H2**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
- **Configuración por defecto**:
  - URL: `jdbc:h2:mem:pricingdb`
  - Usuario: `sa`
  - Contraseña: *(vacía)*

La base de datos se inicializa automáticamente con datos de ejemplo para cumplir los requisitos de la prueba técnica. Se puede encontrar el script de inicialización en `src/main/resources/data.sql`.
Y el esquema de la tabla en `src/main/resources/schema.sql`

### **Tipos de Tests**
1. **Tests Unitarios**: Validan la lógica de negocio de manera aislada (clase PriceService).
2. **Tests Funcionales**: Validan los endpoints REST simulando solicitudes HTTP (clase PriceControllerTest).
3. **Tests End-to-End (E2E)**: Verifican el flujo completo desde la API hasta la base de datos (clase PriceE2ETest).

### Decisiones Técnicas

1. **Spring Boot**: Se pedía como requisito.
2. **Lombok**: Reduce el código repetitivo (getters, setters, etc.), mejorando la legibilidad. También aporta un logger.
3. **H2 Database**: Simplifica las pruebas al eliminar la necesidad de configurar una base de datos externa. (Sugerido en la tarea).
4. **RestAssured**: Para testing e2e. Permite pruebas e2e sencillas y efectivas para validar la integración y funcionalidad de APIs REST.
5. **Docker**: Se incluyó un Dockerfile para garantizar la portabilidad y facilitar el despliegue en cualquier entorno.
6. **OpenApi Generator**: Se ha utilizado OpenApi para generar automáticamente código para el funcionamiento de la api.
7. **JaCoCo**: Se ha integrado para medir la cobertura de pruebas.