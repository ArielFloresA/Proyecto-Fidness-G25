# FIDNESS

Proyecto desarrollado para el curso **Desarrollo de Aplicaciones Web y Patrones**.

FIDNESS es una plataforma web orientada a la administración de ejercicios y rutinas de entrenamiento, permitiendo organizar planes de entrenamiento mediante una interfaz moderna desarrollada con Spring Boot.

---

# Avance #2

En este segundo avance se desarrolló aproximadamente el 50% de la funcionalidad planificada del proyecto, implementando la arquitectura base y los módulos principales relacionados con ejercicios y rutinas.

## Funcionalidades implementadas

- Gestión de ejercicios.
    - Listado de ejercicios.
    - Registro de ejercicios.
    - Consulta de información.
    - Administración mediante Spring Data JPA.

- Gestión de rutinas.
    - Listado de rutinas.
    - Registro de rutinas.
    - Visualización del detalle.
    - Estructura preparada para futuras mejoras.

- Integración con MySQL.

- Integración con Firebase Storage para el almacenamiento de imágenes.

- Arquitectura MVC utilizando Spring Boot.

- Interfaz desarrollada con Thymeleaf y Bootstrap.

---

# Estructura del proyecto

```
src
 ├── controller
 ├── domain
 ├── repository
 ├── service
 ├── config
 ├── templates
 ├── static
 └── resources
```

---

# Base de datos

El proyecto utiliza MySQL.

El script de creación de la base de datos se encuentra en:

```
src/main/resources/creaTablas.sql
```

Este script crea:

- Base de datos **fidness**
- Tabla **ejercicio**
- Tabla **rutina**
- Datos de prueba

---

# Firebase Storage

Las imágenes de los ejercicios se almacenan en Firebase Storage.

Por razones de seguridad **el archivo de credenciales de Firebase no se incluye en este repositorio**. PERO SI EN EL .ZIP en el Campus.

Para habilitar esta funcionalidad debe colocarse el archivo JSON de credenciales dentro de:

```
src/main/resources/firebase/
```

Además, debe configurarse correctamente el archivo:

```
application.properties
```

con los parámetros correspondientes al proyecto Firebase.

---

# Configuración de MySQL

Antes de ejecutar el proyecto configure las credenciales de su servidor MySQL dentro de:

```
src/main/resources/application.properties
```

Ejemplo:

```properties
spring.datasource.username=SU_USUARIO
spring.datasource.password=SU_CONTRASEÑA
```

---

# Cómo ejecutar

1. Crear la base de datos utilizando:

```
creaTablas.sql
```

2. Configurar las credenciales de MySQL.

3. Agregar el archivo JSON de Firebase en:

```
src/main/resources/firebase/
```

4. Ejecutar el proyecto desde NetBeans o mediante Maven.

---

# Estado del proyecto

✔ Avance #2

Actualmente se encuentran implementados los módulos principales correspondientes a:

- Administración de ejercicios.
- Administración de rutinas.
- Persistencia con MySQL.
- Almacenamiento de imágenes en Firebase Storage.

Las funcionalidades restantes serán desarrolladas en los siguientes avances del proyecto.

---

# Autor Ariel Flores

Grupo 25

Curso Desarrollo de Aplicaciones Web y Patrones

Universidad Fidelitas
