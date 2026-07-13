/*
    ===========================================================
    UNIVERSIDAD
    Desarrollo de Aplicaciones Web y Patrones

    Proyecto: FIDNESS
    Avance #2

    Descripción:
    Script de creación de la base de datos del proyecto FIDNESS.
    Incluye las tablas principales desarrolladas durante el
    Avance #2 y sus respectivos datos de prueba.

    Autor:
    Ariel Flores
    ===========================================================
*/

DROP DATABASE IF EXISTS fidness;
CREATE DATABASE fidness;
USE fidness;

-- ===========================================================
-- Tabla: ejercicio
-- ===========================================================

CREATE TABLE ejercicio (

    id_ejercicio INT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(60) NOT NULL,
    descripcion VARCHAR(300) NOT NULL,
    grupo_muscular VARCHAR(40) NOT NULL,
    nivel VARCHAR(20) NOT NULL,
    series INT NOT NULL,
    repeticiones INT NOT NULL,
    imagen VARCHAR(1024),
    activo BOOLEAN NOT NULL,

    PRIMARY KEY (id_ejercicio)

);

-- ===========================================================
-- Datos de prueba
-- ===========================================================

INSERT INTO ejercicio
(nombre, descripcion, grupo_muscular, nivel, series, repeticiones, imagen, activo)
VALUES

(
'Press de banca',
'Ejercicio para desarrollar fuerza y volumen en el pecho.',
'Pecho',
'Intermedio',
4,
10,
'https://images.pexels.com/photos/416717/pexels-photo-416717.jpeg',
TRUE
),

(
'Press inclinado con mancuernas',
'Trabaja la parte superior del pecho.',
'Pecho',
'Intermedio',
4,
12,
'https://images.pexels.com/photos/1552242/pexels-photo-1552242.jpeg',
TRUE
),

(
'Sentadilla',
'Ejercicio compuesto para fortalecer piernas y glúteos.',
'Piernas',
'Intermedio',
4,
12,
'https://images.pexels.com/photos/841130/pexels-photo-841130.jpeg',
TRUE
),

(
'Prensa de piernas',
'Fortalece cuádriceps y glúteos.',
'Piernas',
'Principiante',
3,
15,
'https://images.pexels.com/photos/2261485/pexels-photo-2261485.jpeg',
TRUE
),

(
'Peso muerto',
'Ejercicio para espalda baja y piernas.',
'Espalda',
'Avanzado',
4,
8,
'https://images.pexels.com/photos/949126/pexels-photo-949126.jpeg',
TRUE
),

(
'Dominadas',
'Fortalece espalda y bíceps.',
'Espalda',
'Avanzado',
4,
10,
'https://images.pexels.com/photos/416778/pexels-photo-416778.jpeg',
TRUE
),

(
'Curl de bíceps',
'Ejercicio aislado para bíceps.',
'Bíceps',
'Principiante',
3,
12,
'https://images.pexels.com/photos/1552106/pexels-photo-1552106.jpeg',
TRUE
),

(
'Extensión de tríceps',
'Fortalece el músculo tríceps.',
'Tríceps',
'Principiante',
3,
15,
'https://images.pexels.com/photos/1431282/pexels-photo-1431282.jpeg',
TRUE
),

(
'Elevaciones laterales',
'Desarrolla los músculos del hombro.',
'Hombros',
'Principiante',
3,
15,
'https://images.pexels.com/photos/2294361/pexels-photo-2294361.jpeg',
TRUE
),

(
'Plancha abdominal',
'Ejercicio isométrico para fortalecer el abdomen.',
'Abdomen',
'Principiante',
3,
60,
'https://images.pexels.com/photos/3076516/pexels-photo-3076516.jpeg',
TRUE
),

(
'Mountain Climbers',
'Ejercicio cardiovascular de alta intensidad.',
'Cardio',
'Intermedio',
4,
20,
'https://images.pexels.com/photos/1552252/pexels-photo-1552252.jpeg',
TRUE
),

(
'Burpees',
'Ejercicio funcional para todo el cuerpo.',
'Cardio',
'Avanzado',
5,
15,
'https://images.pexels.com/photos/4498606/pexels-photo-4498606.jpeg',
TRUE
),

(
'Remo con barra',
'Ejercicio para desarrollar la espalda.',
'Espalda',
'Intermedio',
4,
10,
'https://images.pexels.com/photos/949132/pexels-photo-949132.jpeg',
FALSE
),

(
'Fondos en paralelas',
'Fortalece pecho y tríceps.',
'Pecho',
'Avanzado',
4,
12,
'https://images.pexels.com/photos/1552249/pexels-photo-1552249.jpeg',
FALSE
),

(
'Crunch abdominal',
'Ejercicio básico para abdomen.',
'Abdomen',
'Principiante',
3,
20,
'https://images.pexels.com/photos/414029/pexels-photo-414029.jpeg',
FALSE
);

-- ===========================================================
-- Tabla: rutina
-- ===========================================================

CREATE TABLE rutina (

    id_rutina INT NOT NULL AUTO_INCREMENT,

    nombre VARCHAR(60) NOT NULL,

    objetivo VARCHAR(100) NOT NULL,

    nivel VARCHAR(20) NOT NULL,

    duracion INT NOT NULL,

    activo BOOLEAN NOT NULL,

    PRIMARY KEY (id_rutina)

);

-- ===========================================================
-- Datos de prueba
-- ===========================================================

INSERT INTO rutina
(nombre, objetivo, nivel, duracion, activo)
VALUES

('Rutina de Pecho',
'Aumentar fuerza y volumen del pecho',
'Intermedio',
45,
TRUE),

('Rutina de Piernas',
'Fortalecer tren inferior',
'Principiante',
60,
TRUE),

('Rutina Espalda',
'Desarrollar espalda y postura',
'Avanzado',
50,
TRUE),

('Rutina Cardio',
'Mejorar resistencia cardiovascular',
'Principiante',
30,
TRUE),

('Rutina Full Body',
'Entrenamiento de cuerpo completo',
'Intermedio',
70,
FALSE);