# 🎧 SoundMap BCN – Backend

## Objetivo

**SoundMap BCN** es la API backend encargada de gestionar usuarios, autenticación, subida de sonidos, almacenamiento en filesystem y recuperación de datos geolocalizados.  
Permite que el frontend pueda mostrar, reproducir y gestionar paisajes sonoros reales de Barcelona.

Construido con **Java 21 + Spring Boot 3.4**, siguiendo arquitectura MVC, seguridad JWT y buenas prácticas.

---

## Competencias Técnicas

- **Diseño de API RESTful**
- **Autenticación con JWT**
- **Gestión de ficheros (multipart)**
- **Persistencia con PostgreSQL + JPA**
- **MapStruct + DTOs**
- **Arquitectura en 3 capas**
- **Manejo de excepciones**
- **Testing con Postman / JUnit / Mockito**

---

## Tecnologías y Herramientas

- **Lenguaje:** Java 21  
- **Framework:** Spring Boot 3.4  
- **Persistencia:** Spring Data JPA  
- **Seguridad:** Spring Security + JWT  
- **Mapeo:** MapStruct  
- **Base de Datos:** PostgreSQL  
- **Testing:** JUnit, Mockito, Postman  
- **Gestión:** Git + GitHub, Trello  

---

## Funcionalidades

### Público (sin autenticación)
- Obtener todos los sonidos
- Ver detalle de un sonido
- Reproducir audio

### Autenticación
- Registro de usuaria
- Login con JWT
- Validación automática de token

### Perfil de usuaria
- Obtener datos de la usuaria
- Ver sonidos subidos por ella

### Gestión de sonidos
- Subida de archivo `.mp3` o `.wav` mediante Multipart
- Guardado del fichero en filesystem
- Guardado de información en PostgreSQL
- Listar sonidos por usuaria
- Ver detalles de sonido

---

##  Relaciones

- **Usuario 1:N Sonido**  
  Una usuaria puede subir muchos sonidos.  

- **Sonido N:1 Usuario**  
  Cada sonido pertenece a una usuaria.  

---

#  Cómo iniciar el proyecto

## Requisitos previos
- Java 21  
- Maven  
- PostgreSQL  
- Postman (opcional para tests)

---

## 1. Clonar repositorio

```bash
git clone https://github.com/AngelaBello-creator/SoundMap-Backend.git
cd soundmap-backend
