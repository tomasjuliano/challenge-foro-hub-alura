# 📌 Foro Hub - Challenge Back End

Este proyecto forma parte del **Challenge Back End** del curso de **Alura Latam**.  
La idea es construir el backend de un **foro** donde los usuarios puedan crear, leer, actualizar y eliminar tópicos, siguiendo las mejores prácticas de **API REST**.

---

## 🚀 ¿Qué hace esta API?

La API permite a los usuarios:

- 🆕 **Crear** un nuevo tópico
- 📜 **Listar** todos los tópicos creados
- 🔍 **Consultar** un tópico específico
- ✏️ **Actualizar** un tópico
- 🗑️ **Eliminar** un tópico

Todo esto con validaciones, persistencia en base de datos y un sistema de autenticación/autorización para proteger la información.

---

## 🛠️ Tecnologías utilizadas

- ☕ **Java 17**
- 🌱 **Spring Boot**
- 🗄️ **MySQL**
- 📦 **Maven**
- 🧩 **JPA / Hibernate**
- 🐬 **Flyway** (migraciones de base de datos)
- 🔒 **Spring Security** (JWT)
- 📊 **SpringDoc** (documentación OpenAPI)
- 📌 **Lombok**
- 🟣 **Insomnia** (para pruebas de API)
- 🧪 **JUnit** (tests automatizados)

---

## 🔑 Autenticación

El acceso a los endpoints está protegido con **JWT**.  
Para obtener un token, el usuario debe autenticarse vía `/login`.  
Una vez recibido, se debe enviar en el header de cada request: 

---

## 📌 Proceso de desarrollo

El proyecto se desarrolla siguiendo una metodología ágil con **Trello** para gestión de tareas.

---

## ✨ Mi experiencia

Este proyecto me sirvió para afianzar conocimientos en **Spring Boot**, especialmente en temas como autenticación con JWT, manejo de migraciones con Flyway y buenas prácticas de API REST.  
También reforcé conceptos de relaciones en base de datos y validaciones con anotaciones.

---

**Desarrollado en 2025**, por **Tomas Juliano** 🦦
