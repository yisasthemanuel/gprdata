# GPRDATA
=========================

## Ejecución de la imagen

```shell
docker run -d -e entorno=I -e DOCKER_TIMEZONE=Europe/Madrid -p 8080:8080 --name yisas-gprdata --rm yisasthemanuel/gprdata:1.5.0.1.RELEASE
```

** Banner generado con la fuente alligator2 (https://devops.datenkollektiv.de/banner.txt/index.html)

Lo siguente es genérico y hay que adaptarlo

## Introducción

Proyecto básico que modela las características básicas de ejecución, compilación, empaquetado y monitorización, entre otros.

## Desarrollo

## Prerequisitos

* Un IDE con soporte al proyecto Lombok (<https://projectlombok.org/>): Eclipse, IntelliJ, Visual Studio Code.
* La JVM OpenJ17 instalada (<https://adoptium.net/es/temurin/releases/?os=linux&version=17&package=jdk>)
* Maven: No necesario, está integrado en el proyecto mediante "maven wrapper" / mvnw (<https://github.com/takari/maven-wrapper>)
* Docker: para construir y ejecutar imágenes Docker -
** Windows / Mac: <https://www.docker.com/products/docker-desktop>
** WSL (Windows Subsystem Linux) + Docker Desktop: <https://nickjanetakis.com/blog/setting-up-docker-for-windows-and-wsl-to-work-flawlessly>
** WSL + Remote Docker Server: <https://dev.to/sebagomez/installing-the-docker-client-on-windows-subsystem-for-linux-ubuntu-3cgd>
** Ubuntu: <https://docs.docker.com/install/linux/docker-ce/ubuntu/> - Ubuntu

## Configuración

Este proyecto hace uso de Spring Cloud Config para ganar acceso a las variables de configuración. La configruración de Cloud Config se inyecta como variables de entorno en la aplicación, que las recoge en el fichero bootstrap.properties

## Variables de entorno y valores por defecto

Se deben tener las siguientes variables de entorno para poder arrancar la aplicación

* CONFIG_ENABLED -> true
* CONFIG_SERVER -> <http://localhost:8888>
* CONFIG_SERVER_USER -> user
* CONFIG_SERVER_PASSWORD -> password
* CONFIG_SERVER_LABEL -> master
* CONFIG_FAIL_FAST -> true
* SPRING_PROFILES_ACTIVE -> default

## Ejecución en desarrollo

Se deben tener las variables de entorno configuradas y ejecutar, dentro del proyecto, el siguiente comando:

```shell
CONFIG_ENABLED=true CONFIG_SERVER=http://192.168.0.38:8888 SPRING_PROFILES_ACTIVE=dev ./mvnw spring-boot:run
```

## Docker

### Variables Docker

El dockerfile incluye las mismas variables de entorno para poder arrancar Cloud Config

* CONFIG_ENABLED -> true
* CONFIG_SERVER -> <http://localhost:8888>
* CONFIG_SERVER_USER -> user
* CONFIG_SERVER_PASSWORD -> password
* CONFIG_SERVER_LABEL -> master
* CONFIG_FAIL_FAST -> true
* SPRING_PROFILES_ACTIVE -> default

### Construcción de imagen Docker

```shell
cd base_ci_cd
./mvnw clean package
docker build -t base_ci_cd .
```

### Ejecución de la imagen

```shell
docker run -d -p 1234:8080 -e CONFIG_SERVER=http://192.168.0.38:8888 -e SPRING_PROFILES_ACTIVE=dev --name base_ci_cd base_ci_cd
```

## Guías [EN]

* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)

## Changelog [ES]

* **1.4.6 (14/10/2022)** - Se aplican las alertas de seguridad enviadas por Github: Improper Input Validation and Injection in Apache Log4j2, Remote Code Execution in Spring Framework, Uncontrolled Resource Consumption in FasterXML jackson-databind, Uncontrolled Resource Consumption in Jackson-databind and Deeply nested json in jackson-databind.

* **1.4.6.1 (06/11/2022)** - Se corrige la llamada al servicio de actualización de la posición de un manager en la temporada desde la pantalla de resultados.

* **1.4.6.2 (06/11/2022)** - Se corrige la URL de la llamada al servicio de actualización de la posición de un manager.

* **1.5.0.0 (30/03/2024)** - Se sube a la versión 6.0 de spring para elimingar varias vulnerabilidades de seguridad. Como consecuencia de lo anterior, también se sube a la versión temurin 17 de Java y a la versión 10 tomcat (imagen tomcat:jdk17-temurin)

* **1.5.0.1 (30/03/2024)** - Se incluye un nuevo link en la página de la copa para consultar las reglas de la competición
Se corrigen tres vulnerabilidades: 1) Access Control Bypass in Spring Security (se pasa a usar la versión 6.0.5 de spring security) 2) Spring Framework URL Parsing with Host Validation Vulnerability (se pasa a usar la versión 6.0.18 de spring web) 3) Spring Framework vulnerable to denial of service (se pasa a usar la versión 6.0.14 de spring webmvc

## TODOs

* Hay que quitar algunas dependencias que obligan a que el contexto de la aplicación se llame gprdata
* Incorporar un control adecuado de excepciones
* Mejorar los controles de navegación entre temporadas (pantalla de copa y de resultados)

## Referencias [EN]

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.1.RELEASE/maven-plugin/)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/2.2.1.RELEASE/reference/htmlsingle/#production-ready)
* [Spring Boot Monitor metrics with Prometheus](https://www.callicoder.com/spring-boot-actuator-metrics-monitoring-dashboard-prometheus-grafana/)
* [Spring Boot Thin Launcher](https://github.com/spring-projects-experimental/spring-boot-thin-launcher)
* [Spring Boot Thin Launcher & Docker](https://dev.to/bufferings/spring-boot-thin-launcher-anddocker-2oa7)