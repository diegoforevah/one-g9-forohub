# Foro Hub

<p align="center">  
  <img width="250" height="250" alt="Badge-Spring" src="https://github.com/user-attachments/assets/cb44ae30-ad61-4592-9306-f5ce16099b7f"/>
</p>
<p align="center">
  <img src="https://img.shields.io/badge/Estado-Finalizado-purple">
  <img src="https://img.shields.io/badge/Fecha%20de%20lanzamiento-Marzo%202026-gold">
</p>

## Índice
- [Descripción del proyecto](#descripción-del-proyecto)
- [Características de la aplicación y demostración](#características-de-la-aplicación-y-demostración)
- [Acceso al proyecto](#acceso-al-proyecto)
- [Tecnologías utilizadas](#tecnologías-utilizadas)
- [Licencia](#licencia)

---

## Descripción del proyecto

Proyecto hecho para el curso de 'Java y Spring Framework' ONE G9.

## Características de la aplicación y demostración

Una REST API que permite crear foros, editarlos, buscarlos y borrarlos de una base de datos local.
Contamos con los siguientes endpoints:
- /login - POST
- /topicos - GET
- /topicos - POST
- /topicos - DELETE
- /topicos - PUT

Algunos ejemplos:

- Post al endpoint /login, el cual nos devolverá un token JWT si nuestras credenciales son correctas
<img width="941" height="451" alt="image" src="https://github.com/user-attachments/assets/24eae2a3-8cab-4a1c-aa55-e733e4bd0924" />

- Get al endpoint /topicos para listar todos los topicos donde deberemos adjuntar nuestro token para poder hacer las demás consultas, de no hacerlo no podremos realizar ninguna acción.

<img width="936" height="339" alt="image" src="https://github.com/user-attachments/assets/bffb5004-5c3d-4fc3-8e69-bdceab8b9c73" />
<img width="933" height="613" alt="image" src="https://github.com/user-attachments/assets/d44d784b-70f5-4396-96aa-5a05319fc398" />

Consulta sin token (403 Forbidden):

<img width="664" height="335" alt="image" src="https://github.com/user-attachments/assets/044feaf3-ae21-4fae-a9cf-07afca7843df" />

- Get al endpoint /topicos buscando por Curso y año
<img width="934" height="607" alt="image" src="https://github.com/user-attachments/assets/53a96b05-29d9-46b3-b218-9d848a1ea42f" />

- Post al endpoint /topicos creando un nuevo tópico
(Nótese como en el cuerpo del post no incluimos quién es el autor del tópico creado, pero el sistema intuye correctamente el autor gracias al token que estamos incluyendo en cada petición)
<img width="937" height="595" alt="image" src="https://github.com/user-attachments/assets/018e01c2-e829-4641-931b-de47cb562e9e" />

- Delete al endpoint /topicos borrando un topico
(Para este caso, el tópico no se elimina de la base de datos, solo se actualiza su status a false para que nadie pueda listarlo)
<img width="661" height="460" alt="image" src="https://github.com/user-attachments/assets/61771e28-d14b-49bd-894f-9e7a1f8a6de9" />

- Put al endpoint /topicos actualizando un topico
<img width="934" height="598" alt="image" src="https://github.com/user-attachments/assets/5ca0bcca-fa8b-4eb7-b844-2f0af0106182" />

## Acceso al proyecto

Pre-requisitos:
- IntelliJ IDEA
- PostgreSQL (Instalación, nombre de usuario y contraseña, creación de una base de datos y creación de usuarios de prueba)

Ejemplo de usuarios:
* Se debe guardar la clave del usuario encriptada directamente en la base de datos
* Los usuarios puede tener dos roles ROLE_ADMIN o ROLE_USER
<img width="1274" height="323" alt="image" src="https://github.com/user-attachments/assets/f02afc47-a956-4d84-b874-a663a3ef0c2a" />
* Los usuarios con rol de ADMIN podrán ver tópicos que estén deshabilitados, editar tópicos de otros usuarios y eliminar tópicos de otros usuarios. Mientras que los usuarios con rol de USER solo podrán ver, editar y eliminar sus propios tópicos.
</br>
Para acceder a la aplicación debes seguir estos pasos:

- Descarga el comprimido del proyecto y alójalo donde gustes.
- Abrimos el proyecto usando el IDE de tu preferencia. Para este caso se usará IntelliJ IDEA.
- Una vez abierto el proyecto, debemos abrir el archivo src>main>resources>application.properties. Allí veremos varias variables, debemos modificar
y colocar nuestras credenciales en las variables spring.datasource.url (Aquí colocamos el nombre de nuestra base de datos), spring.datasource.username (Aquí colocamos nuestro usuario) y spring.datasource.password (Aquí colocamos nuestra contraseña).

Este es un ejemplo de mis credenciales:
<img width="735" height="295" alt="image" src="https://github.com/user-attachments/assets/40b25481-3c12-44d4-bef9-425bbf331f9b" />

Luego de colocar nuestras credenciales, ya podremos iniciar y probar la aplicación que correrá en localhost:8080.

## Tecnologías utilizadas

Para la creación de este proyecto, se usó:

- JAVA con Spring Boot
- PostgreSQL
- JWT

## Licencia

El proyecto es libre, no cuenta con licencia.
