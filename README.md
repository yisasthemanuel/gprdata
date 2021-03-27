# GPRDATA
=========================

## Ejecución de la imagen

```shell
docker run -d -e entorno=I -e DOCKER_TIMEZONE=Europe/Madrid -p 8080:8080 --name yisas-gprdata --rm yisasthemanuel/gprdata:1.4.3.RELEASE
```

** Banner generado con la fuente alligator2 (https://devops.datenkollektiv.de/banner.txt/index.html)

Lo siguente es genérico y hay que adaptarlo

## Introducción

Proyecto básico que modela las características básicas de ejecución, compilación, empaquetado y monitorización, entre otros.

## Desarrollo

## Prerequisitos

* Un IDE con soporte al proyecto Lombok (<https://projectlombok.org/>): Eclipse, IntelliJ, Visual Studio Code.
* La JVM OpenJ9 instalada (<https://adoptopenjdk.net/installation.html#linux-pkg>, <https://adoptopenjdk.net/releases.html?variant=openjdk8&jvmVariant=openj9>)
** Ejemplo para linux (Debian/Ubuntu) / también WSL
** `wget -qO - https://adoptopenjdk.jfrog.io/adoptopenjdk/api/gpg/key/public | sudo apt-key add -`
** `sudo add-apt-repository --yes https://adoptopenjdk.jfrog.io/adoptopenjdk/deb/`
** `sudo apt-get install adoptopenjdk-11-openj9`
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

## Referencias [EN]

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.1.RELEASE/maven-plugin/)
* [Spring Boot Actuator](https://docs.spring.io/spring-boot/docs/2.2.1.RELEASE/reference/htmlsingle/#production-ready)
* [Spring Boot Monitor metrics with Prometheus](https://www.callicoder.com/spring-boot-actuator-metrics-monitoring-dashboard-prometheus-grafana/)
* [Spring Boot Thin Launcher](https://github.com/spring-projects-experimental/spring-boot-thin-launcher)
* [Spring Boot Thin Launcher & Docker](https://dev.to/bufferings/spring-boot-thin-launcher-anddocker-2oa7)

## Guías [EN]

* [Building a RESTful Web Service with Spring Boot Actuator](https://spring.io/guides/gs/actuator-service/)

## TODOs

* Hay que quitar algunas dependencias que obligan a que el contexto de la aplicación se llame gprdata
