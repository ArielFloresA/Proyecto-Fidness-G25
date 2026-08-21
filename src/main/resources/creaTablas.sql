/*
    ===========================================================
    UNIVERSIDAD
    Desarrollo de Aplicaciones Web y Patrones

    Proyecto: FIDNESS
    Entrega Final

    Descripción:
    Script de creación de la base de datos del proyecto FIDNESS.
    Incluye las tablas principales del sistema y sus respectivos
    datos de prueba.

    Autor:
    Ariel Flores
    ===========================================================
*/

DROP DATABASE IF EXISTS fidness;

CREATE DATABASE fidness
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE fidness;


-- ===========================================================
-- Tabla: ejercicio
-- ===========================================================

CREATE TABLE ejercicio (

    id_ejercicio INT NOT NULL AUTO_INCREMENT,

    nombre VARCHAR(60) NOT NULL,

    descripcion VARCHAR(300) NOT NULL,

    instrucciones VARCHAR(1000),

    recomendaciones VARCHAR(1000),

    grupo_muscular VARCHAR(40) NOT NULL,

    nivel VARCHAR(20) NOT NULL,

    tipo_entrenamiento VARCHAR(40) NOT NULL,

    series INT NOT NULL,

    repeticiones INT NOT NULL,

    imagen VARCHAR(1024),

    activo BOOLEAN NOT NULL,

    PRIMARY KEY (id_ejercicio)

);


-- ===========================================================
-- Datos de prueba: ejercicio
-- ===========================================================

INSERT INTO ejercicio
(
    nombre,
    descripcion,
    instrucciones,
    recomendaciones,
    grupo_muscular,
    nivel,
    tipo_entrenamiento,
    series,
    repeticiones,
    imagen,
    activo
)
VALUES

(
'Press de banca',
'Ejercicio para desarrollar fuerza y volumen en el pecho.',
'Realiza el movimiento de forma controlada, mantén una postura estable y completa cada repetición sin movimientos bruscos.',
'Utiliza un peso adecuado a tu nivel, realiza un calentamiento previo y descansa entre series.',
'Pecho',
'Intermedio',
'Fuerza',
4,
10,
'https://images.pexels.com/photos/416717/pexels-photo-416717.jpeg',
TRUE
),

(
'Press inclinado con mancuernas',
'Trabaja la parte superior del pecho.',
'Realiza el movimiento de forma controlada, mantén una postura estable y completa cada repetición sin movimientos bruscos.',
'Utiliza un peso adecuado a tu nivel, realiza un calentamiento previo y descansa entre series.',
'Pecho',
'Intermedio',
'Fuerza',
4,
12,
'https://images.pexels.com/photos/1552242/pexels-photo-1552242.jpeg',
TRUE
),

(
'Sentadilla',
'Ejercicio compuesto para fortalecer piernas y glúteos.',
'Mantén la espalda estable, controla el movimiento y completa el rango de movimiento de acuerdo con tu capacidad.',
'Realiza un calentamiento de piernas y utiliza una carga que permita mantener una técnica correcta.',
'Piernas',
'Intermedio',
'Fuerza',
4,
12,
'https://images.pexels.com/photos/841130/pexels-photo-841130.jpeg',
TRUE
),

(
'Prensa de piernas',
'Fortalece cuádriceps y glúteos.',
'Mantén la espalda estable, controla el movimiento y completa el rango de movimiento de acuerdo con tu capacidad.',
'Realiza un calentamiento de piernas y utiliza una carga que permita mantener una técnica correcta.',
'Piernas',
'Principiante',
'Fuerza',
3,
15,
'https://images.pexels.com/photos/2261485/pexels-photo-2261485.jpeg',
TRUE
),

(
'Peso muerto',
'Ejercicio para espalda baja y piernas.',
'Realiza el movimiento de forma controlada, mantén la espalda estable y evita movimientos bruscos.',
'Utiliza una carga adecuada y prioriza siempre una técnica correcta.',
'Espalda',
'Avanzado',
'Fuerza',
4,
8,
'https://images.pexels.com/photos/949126/pexels-photo-949126.jpeg',
TRUE
),

(
'Dominadas',
'Fortalece espalda y bíceps.',
'Realiza el movimiento de forma controlada y mantén el cuerpo estable durante cada repetición.',
'Evita balancearte y realiza únicamente las repeticiones que puedas completar con buena técnica.',
'Espalda',
'Avanzado',
'Fuerza',
4,
10,
'https://images.pexels.com/photos/416778/pexels-photo-416778.jpeg',
TRUE
),

(
'Curl de bíceps',
'Ejercicio aislado para bíceps.',
'Mantén los codos estables y realiza la flexión de manera controlada.',
'Evita utilizar impulso y selecciona un peso apropiado.',
'Bíceps',
'Principiante',
'Fuerza',
3,
12,
'https://images.pexels.com/photos/1552106/pexels-photo-1552106.jpeg',
TRUE
),

(
'Extensión de tríceps',
'Fortalece el músculo tríceps.',
'Mantén una posición estable y controla completamente la extensión de los brazos.',
'Evita utilizar cargas excesivas y mantén los codos controlados.',
'Tríceps',
'Principiante',
'Fuerza',
3,
15,
'https://images.pexels.com/photos/1431282/pexels-photo-1431282.jpeg',
TRUE
),

(
'Elevaciones laterales',
'Desarrolla los músculos del hombro.',
'Eleva los brazos de manera controlada manteniendo una ligera flexión en los codos.',
'Utiliza un peso ligero o moderado para conservar una técnica correcta.',
'Hombros',
'Principiante',
'Fuerza',
3,
15,
'https://images.pexels.com/photos/2294361/pexels-photo-2294361.jpeg',
TRUE
),

(
'Plancha abdominal',
'Ejercicio isométrico para fortalecer el abdomen.',
'Mantén el abdomen contraído y conserva una línea estable entre hombros, cadera y piernas.',
'Mantén una respiración constante y detén el ejercicio si pierdes la postura correcta.',
'Abdomen',
'Principiante',
'Core',
3,
60,
'https://images.pexels.com/photos/3076516/pexels-photo-3076516.jpeg',
TRUE
),

(
'Mountain Climbers',
'Ejercicio cardiovascular de alta intensidad.',
'Alterna las piernas de forma continua manteniendo las manos apoyadas y el abdomen estable.',
'Mantente hidratado y controla la intensidad de acuerdo con tu nivel de entrenamiento.',
'Cardio',
'Intermedio',
'Cardio',
4,
20,
'https://images.pexels.com/photos/1552252/pexels-photo-1552252.jpeg',
TRUE
),

(
'Burpees',
'Ejercicio funcional para todo el cuerpo.',
'Realiza cada fase del movimiento de forma continua y controlada manteniendo una buena postura.',
'Adapta la velocidad a tu condición física y toma descansos cuando sea necesario.',
'Cardio',
'Avanzado',
'Cardio',
5,
15,
'https://images.pexels.com/photos/4498606/pexels-photo-4498606.jpeg',
TRUE
),

(
'Remo con barra',
'Ejercicio para desarrollar la espalda.',
'Inclina ligeramente el torso y acerca la barra al cuerpo manteniendo la espalda estable.',
'Utiliza un peso que permita conservar una postura correcta durante todas las repeticiones.',
'Espalda',
'Intermedio',
'Fuerza',
4,
10,
'https://images.pexels.com/photos/949132/pexels-photo-949132.jpeg',
FALSE
),

(
'Fondos en paralelas',
'Fortalece pecho y tríceps.',
'Desciende de manera controlada y extiende los brazos manteniendo estable el cuerpo.',
'No fuerces el rango de movimiento y mantén una técnica adecuada.',
'Pecho',
'Avanzado',
'Fuerza',
4,
12,
'https://images.pexels.com/photos/1552249/pexels-photo-1552249.jpeg',
FALSE
),

(
'Crunch abdominal',
'Ejercicio básico para abdomen.',
'Eleva ligeramente el torso utilizando la contracción abdominal y evita tirar del cuello.',
'Mantén una respiración constante y realiza el movimiento lentamente.',
'Abdomen',
'Principiante',
'Core',
3,
20,
'https://images.pexels.com/photos/414029/pexels-photo-414029.jpeg',
FALSE
);


-- ===========================================================
-- Tabla: usuario
-- ===========================================================

CREATE TABLE usuario (

    id_usuario INT NOT NULL AUTO_INCREMENT,

    username VARCHAR(50) NOT NULL UNIQUE,

    password VARCHAR(512) NOT NULL,

    nombre VARCHAR(60) NOT NULL,

    apellidos VARCHAR(80) NOT NULL,

    correo VARCHAR(100) NOT NULL UNIQUE,

    telefono VARCHAR(25),

    ruta_imagen TEXT,

    activo BOOLEAN NOT NULL,

    PRIMARY KEY (id_usuario)

);


-- ===========================================================
-- Datos de prueba: usuario
--
-- Contraseña de ambos usuarios:
-- 123
-- ===========================================================

INSERT INTO usuario
(
    username,
    password,
    nombre,
    apellidos,
    correo,
    telefono,
    ruta_imagen,
    activo
)
VALUES

(
'admin',
'$2a$10$P1.w58XvnaYQUQgZUCk4aO/RTRl8EValluCqB3S2VMLTbRt.tlre.',
'Administrador',
'FIDNESS',
'admin@fidness.com',
'8888-8888',
'https://cdn-icons-png.flaticon.com/512/149/149071.png',
TRUE
),

(
'ariel',
'$2a$10$P1.w58XvnaYQUQgZUCk4aO/RTRl8EValluCqB3S2VMLTbRt.tlre.',
'Ariel',
'Flores',
'ariel@fidness.com',
'8888-1111',
'https://cdn-icons-png.flaticon.com/512/149/149071.png',
TRUE
);


-- ===========================================================
-- Tabla: rol
-- ===========================================================

CREATE TABLE rol (

    id_rol INT NOT NULL AUTO_INCREMENT,

    nombre VARCHAR(20) NOT NULL,

    id_usuario INT NOT NULL,

    PRIMARY KEY (id_rol),

    CONSTRAINT fk_rol_usuario
        FOREIGN KEY (id_usuario)
        REFERENCES usuario(id_usuario)

);


-- ===========================================================
-- Datos de prueba: rol
-- ===========================================================

INSERT INTO rol
(
    nombre,
    id_usuario
)
VALUES

(
'ROLE_ADMIN',
1
),

(
'ROLE_USER',
1
),

(
'ROLE_USER',
2
);


-- ===========================================================
-- Tabla: rutina
-- ===========================================================

CREATE TABLE rutina (

    id_rutina INT NOT NULL AUTO_INCREMENT,

    id_usuario INT NOT NULL,

    nombre VARCHAR(60) NOT NULL,

    objetivo VARCHAR(100) NOT NULL,

    nivel VARCHAR(20) NOT NULL,

    duracion INT NOT NULL,

    activo BOOLEAN NOT NULL,

    PRIMARY KEY (id_rutina),

    CONSTRAINT fk_rutina_usuario
        FOREIGN KEY (id_usuario)
        REFERENCES usuario(id_usuario)

);


-- ===========================================================
-- Datos de prueba: rutina
--
-- Las rutinas iniciales pertenecen al usuario Ariel (ID 2).
-- ===========================================================

INSERT INTO rutina
(
    id_usuario,
    nombre,
    objetivo,
    nivel,
    duracion,
    activo
)
VALUES

(
2,
'Rutina de Pecho',
'Aumentar fuerza y volumen del pecho',
'Intermedio',
45,
TRUE
),

(
2,
'Rutina de Piernas',
'Fortalecer tren inferior',
'Principiante',
60,
TRUE
),

(
2,
'Rutina Espalda',
'Desarrollar espalda y postura',
'Avanzado',
50,
TRUE
),

(
2,
'Rutina Cardio',
'Mejorar resistencia cardiovascular',
'Principiante',
30,
TRUE
),

(
2,
'Rutina Full Body',
'Entrenamiento de cuerpo completo',
'Intermedio',
70,
FALSE
);


-- ===========================================================
-- Tabla: rutina_ejercicio
-- Relación N:M entre rutinas y ejercicios
-- ===========================================================

CREATE TABLE rutina_ejercicio (

    id_rutina INT NOT NULL,

    id_ejercicio INT NOT NULL,

    PRIMARY KEY (
        id_rutina,
        id_ejercicio
    ),

    CONSTRAINT fk_rutina_ejercicio_rutina
        FOREIGN KEY (id_rutina)
        REFERENCES rutina(id_rutina)
        ON DELETE CASCADE,

    CONSTRAINT fk_rutina_ejercicio_ejercicio
        FOREIGN KEY (id_ejercicio)
        REFERENCES ejercicio(id_ejercicio)

);


-- ===========================================================
-- Datos de prueba: rutina_ejercicio
-- ===========================================================

INSERT INTO rutina_ejercicio
(
    id_rutina,
    id_ejercicio
)
VALUES

(1, 1),
(1, 2),

(2, 3),
(2, 4),

(3, 5),
(3, 6),

(4, 11),
(4, 12),

(5, 1),
(5, 3),
(5, 5),
(5, 10);


-- ===========================================================
-- Tabla: membresia
-- ===========================================================

CREATE TABLE membresia (

    id_membresia INT NOT NULL AUTO_INCREMENT,

    nombre VARCHAR(60) NOT NULL,

    descripcion VARCHAR(300) NOT NULL,

    beneficios VARCHAR(1000) NOT NULL,

    precio DECIMAL(10,2) NOT NULL,

    duracion_meses INT NOT NULL,

    activo BOOLEAN NOT NULL,

    PRIMARY KEY (id_membresia)

);


-- ===========================================================
-- Datos de prueba: membresia
-- ===========================================================

INSERT INTO membresia
(
    nombre,
    descripcion,
    beneficios,
    precio,
    duracion_meses,
    activo
)
VALUES

(
'Plan Básico',
'Plan ideal para comenzar a entrenar en FIDNESS.',
'Acceso al gimnasio, catálogo de ejercicios y creación de rutinas personalizadas.',
15000.00,
1,
TRUE
),

(
'Plan Plus',
'Plan para usuarios que buscan una experiencia de entrenamiento más completa.',
'Acceso al gimnasio, catálogo de ejercicios, rutinas personalizadas y acceso a clases grupales.',
25000.00,
1,
TRUE
),

(
'Plan Premium',
'Plan completo para aprovechar todas las opciones de entrenamiento disponibles.',
'Acceso al gimnasio, catálogo de ejercicios, rutinas personalizadas, clases grupales y seguimiento de progreso.',
40000.00,
1,
TRUE
);


-- ===========================================================
-- Tabla: clase_grupal
-- ===========================================================

CREATE TABLE clase_grupal (

    id_clase INT NOT NULL AUTO_INCREMENT,

    nombre VARCHAR(60) NOT NULL,

    descripcion VARCHAR(300) NOT NULL,

    instructor VARCHAR(80) NOT NULL,

    fecha DATE NOT NULL,

    hora TIME NOT NULL,

    duracion INT NOT NULL,

    capacidad INT NOT NULL,

    activo BOOLEAN NOT NULL,

    PRIMARY KEY (id_clase)

);


-- ===========================================================
-- Datos de prueba: clase_grupal
--
-- Se utilizan fechas relativas al día en que se ejecute
-- el script para que siempre aparezcan próximas.
-- ===========================================================

INSERT INTO clase_grupal
(
    nombre,
    descripcion,
    instructor,
    fecha,
    hora,
    duracion,
    capacidad,
    activo
)
VALUES

(
'Spinning',
'Clase cardiovascular de alta intensidad en bicicleta estacionaria.',
'Carlos Ramírez',
DATE_ADD(CURDATE(), INTERVAL 2 DAY),
'18:00:00',
45,
15,
TRUE
),

(
'Yoga',
'Clase enfocada en movilidad, respiración y flexibilidad.',
'Laura Gómez',
DATE_ADD(CURDATE(), INTERVAL 3 DAY),
'17:00:00',
60,
20,
TRUE
),

(
'Funcional',
'Entrenamiento dinámico de cuerpo completo.',
'Andrés Mora',
DATE_ADD(CURDATE(), INTERVAL 4 DAY),
'19:00:00',
50,
12,
TRUE
),

(
'Zumba',
'Clase cardiovascular basada en baile y música.',
'María Fernández',
DATE_ADD(CURDATE(), INTERVAL 5 DAY),
'18:30:00',
60,
25,
TRUE
);


-- ===========================================================
-- Tabla: reserva
-- ===========================================================

CREATE TABLE reserva (

    id_reserva INT NOT NULL AUTO_INCREMENT,

    id_usuario INT NOT NULL,

    id_clase INT NOT NULL,

    fecha_reserva DATETIME NOT NULL
        DEFAULT CURRENT_TIMESTAMP,

    activo BOOLEAN NOT NULL,

    PRIMARY KEY (id_reserva),

    CONSTRAINT fk_reserva_usuario
        FOREIGN KEY (id_usuario)
        REFERENCES usuario(id_usuario),

    CONSTRAINT fk_reserva_clase
        FOREIGN KEY (id_clase)
        REFERENCES clase_grupal(id_clase),

    CONSTRAINT uk_usuario_clase
        UNIQUE (
            id_usuario,
            id_clase
        )

);


-- ===========================================================
-- Datos de prueba: reserva
-- ===========================================================

INSERT INTO reserva
(
    id_usuario,
    id_clase,
    fecha_reserva,
    activo
)
VALUES

(
2,
1,
NOW(),
TRUE
);


-- ===========================================================
-- Tabla: progreso
-- ===========================================================

CREATE TABLE progreso (

    id_progreso INT NOT NULL AUTO_INCREMENT,

    id_usuario INT NOT NULL,

    fecha DATE NOT NULL,

    peso DECIMAL(6,2) NOT NULL,

    cintura DECIMAL(6,2),

    pecho DECIMAL(6,2),

    brazo DECIMAL(6,2),

    pierna DECIMAL(6,2),

    observaciones VARCHAR(500),

    PRIMARY KEY (id_progreso),

    CONSTRAINT fk_progreso_usuario
        FOREIGN KEY (id_usuario)
        REFERENCES usuario(id_usuario)
        ON DELETE CASCADE

);


-- ===========================================================
-- Datos de prueba: progreso
-- Usuario Ariel (ID 2)
-- ===========================================================

INSERT INTO progreso
(
    id_usuario,
    fecha,
    peso,
    cintura,
    pecho,
    brazo,
    pierna,
    observaciones
)
VALUES

(
2,
DATE_SUB(CURDATE(), INTERVAL 30 DAY),
78.50,
88.00,
101.00,
35.00,
57.00,
'Inicio del seguimiento.'
),

(
2,
DATE_SUB(CURDATE(), INTERVAL 15 DAY),
77.40,
86.50,
102.00,
35.50,
57.50,
'Mejora en resistencia y técnica.'
),

(
2,
CURDATE(),
76.80,
85.50,
103.00,
36.00,
58.00,
'Buen progreso general durante el último mes.'
);