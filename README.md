# FIDNESS

Proyecto desarrollado para el curso **Desarrollo de Aplicaciones Web y Patrones** de la Universidad Fidélitas.

FIDNESS es una plataforma web orientada a la gestión y seguimiento de actividades de entrenamiento. El sistema permite administrar ejercicios, crear rutinas personalizadas, consultar membresías, reservar clases grupales y registrar el progreso de los usuarios.

---

# Funcionalidades principales

- Registro e inicio de sesión de usuarios.
- Confirmación de cuenta mediante correo electrónico.
- Recuperación y cambio de contraseña.
- Opción "Recuérdame" durante el inicio de sesión.
- Perfil de usuario con actualización de datos y fotografía.
- Autenticación y autorización mediante Spring Security.
- Roles de usuario y administrador.
- Administración de usuarios y roles.
- Gestión completa de ejercicios.
- Filtrado de ejercicios por diferentes características.
- Creación y administración de rutinas personalizadas.
- Asociación de ejercicios a rutinas.
- Gestión y consulta de membresías.
- Gestión de clases grupales.
- Reserva de clases por parte de los usuarios.
- Registro y seguimiento del progreso.
- Panel de administración.
- Internacionalización en español e inglés.
- Interfaz responsive desarrollada con Bootstrap.
- Almacenamiento de imágenes mediante Firebase Storage.
- Persistencia de información mediante MySQL y Spring Data JPA.

---

# Tecnologías utilizadas

- Java
- Spring Boot
- Spring MVC
- Spring Security
- Spring Data JPA
- Thymeleaf
- Bootstrap
- MySQL
- Firebase Storage
- Maven

---

# Arquitectura

El proyecto utiliza una arquitectura MVC organizada principalmente en:

```text
src/main/java/com/fidness
 ├── config
 ├── controller
 ├── domain
 ├── repository
 └── service

src/main/resources
 ├── static
 ├── templates
 ├── messages.properties
 ├── messages_en.properties
 ├── application.properties
 └── creaTablas.sql
```

---

# Base de datos

FIDNESS utiliza **MySQL** para almacenar la información del sistema.

El script necesario para crear la base de datos, sus tablas, relaciones y datos iniciales se encuentra en:

```text
src/main/resources/creaTablas.sql
```

Antes de ejecutar el proyecto se debe ejecutar este script en MySQL.

El sistema utiliza diferentes relaciones entre sus entidades, incluyendo la asociación de ejercicios con rutinas y las relaciones necesarias para usuarios, roles, clases, reservas, membresías y progreso.

---

# Seguridad y roles

El proyecto utiliza **Spring Security** para controlar la autenticación y autorización.

Se manejan principalmente los roles:

```text
ROLE_USER
ROLE_ADMIN
```

Los usuarios tienen acceso a las funcionalidades generales de FIDNESS, mientras que el administrador dispone de opciones adicionales para la gestión del sistema.

Las contraseñas se almacenan de forma cifrada utilizando BCrypt.

---

# Internacionalización

FIDNESS cuenta con soporte para:

- 🇨🇷 Español
- 🇺🇸 Inglés

El usuario puede cambiar el idioma desde la interfaz del sistema.

Los textos internacionalizados se encuentran en:

```text
messages.properties
messages_en.properties
```

---

# Firebase Storage

Firebase Storage se utiliza para almacenar las imágenes utilizadas por el sistema, incluyendo fotografías de perfil e imágenes asociadas a diferentes registros.

Por razones de seguridad, **las credenciales privadas de Firebase no se incluyen en el repositorio público de GitHub**.

El archivo correspondiente debe colocarse en la ubicación configurada dentro del proyecto.

Las credenciales necesarias sí se incluyen en la entrega correspondiente realizada mediante el Campus Virtual cuando sea requerido.

---

# Configuración de MySQL

Antes de ejecutar el proyecto se deben configurar las credenciales de MySQL en:

```text
src/main/resources/application.properties
```

Ejemplo:

```properties
spring.datasource.username=SU_USUARIO
spring.datasource.password=SU_CONTRASEÑA
```

---

# Cómo ejecutar el proyecto

1. Clonar o descargar el proyecto.
2. Ejecutar `creaTablas.sql` en MySQL.
3. Configurar las credenciales de MySQL en `application.properties`.
4. Configurar las credenciales necesarias para Firebase.
5. Realizar **Clean and Build**.
6. Ejecutar el proyecto desde NetBeans o mediante Maven.
7. Acceder a la aplicación desde el navegador.

---

# Estado del proyecto

**Entrega Final**

El sistema cuenta con los módulos principales desarrollados y funcionales, incluyendo autenticación, roles, ejercicios, rutinas, membresías, clases, reservas, progreso, perfil de usuario, administración, internacionalización y almacenamiento de imágenes.

---

# Autor

**Steven Ariel Flores Alvarez**

Grupo 25  
Curso: Desarrollo de Aplicaciones Web y Patrones  
Universidad Fidélitas

Universidad Fidelitas
