# ProyectoFinal
App para el proyecto final

# ⚓ AnchorMind Pro - Sistema de Apoyo Emocional con IA

Plataforma integral diseñada para la gestión, seguimiento y análisis de episodios de ansiedad, utilizando modelos de procesamiento de lenguaje natural para brindar soporte personalizado y trazabilidad clínica.

# 🚀 Estado del Proyecto: Fase 3 (Identidad & Persistencia Relacional)
Actualmente, el sistema ha evolucionado de un motor de análisis aislado a una plataforma multiusuario con persistencia robusta y versionado de base de datos.

### 🛠️ Stack Tecnológico
* **Lenguaje:** Java 17
* **Framework:** Spring Boot 3.x
* **IA:** Google Gemini 3 Flash API (v1beta)
* **Base de Datos:** PostgreSQL 15+
* **Migraciones:** Flyway (Database Version Control)
* **Comunicación:** Spring WebFlux (WebClient)
* **Persistencia:** Spring Data JPA + Hibernate

---

## 🧠 Arquitectura de Análisis & Identidad
El sistema implementa un flujo de "Texto a Datos Vinculados":
1.  **Identificación:** El UserService valida la existencia y el estado activo del usuario.
2.  **Ingesta:** El controlador recibe el relato (rawInput) y el identificador de usuario.
3.  **Procesamiento IA:** Interacción con Gemini para identificar niveles de ansiedad y técnicas de mitigación.
4.  **Vinculación Relacional:** El AnxietyRecord se persiste asociándolo mediante una clave foránea (user_id) al usuario correspondiente. 

---

## 📋 Bitácora de Logros (Log de Progreso)

### ✅ Fase 1: Infraestructura Base
* Implementación de **Git Flow** profesional (`main`, `develop`, `features`).
* Configuración de seguridad mediante `.gitignore` y variables de entorno para protección de API Keys.
* Modelado inicial de la base de datos en PostgreSQL.

### ✅ Fase 2: El Cerebro del Sistema (IA)
* **Conexión Exitosa:** Integración funcional con Gemini API.
* **Sanitización de Datos:** Lógica de limpieza para bloques de código Markdown en las respuestas JSON.
* **Unificación Lingüística:** Estandarización de variables al inglés (`anxietyLevel`, `triggerIdentified`, `technique`, `applicability`) para coherencia en el código.

### ✅ Fase 3: Identidad y Persistencia (Hito Actual)
* **Modelo de Identidad:** Entidad User con roles (PATIENT, PROFESSIONAL) y soporte para Soft Delete (active).

* **Control de Versiones de DB:** Implementación de Flyway para gestionar migraciones de esquema (V1__Initial_Schema.sql).

* **Relaciones JPA:** Vinculación @ManyToOne entre episodios de ansiedad y perfiles de usuario.

* **Desacoplamiento:** Creación de servicios especializados (UserService, AIServiceR) para una arquitectura limpia.

---

## ⚙️ Configuración para Desarrolladores
Para ejecutar este proyecto localmente, es **necesario** configurar las siguientes variables de entorno en tu IDE o archivo `.env` (No subir este archivo a repositorios públicos):

| Variable | Propósito |
| :--- | :--- |
| `DB_HOST` | Dirección del servidor PostgreSQL |
| `DB_NAME` | Nombre de la base de datos |
| `DB_USER` | Usuario de la DB |
| `DB_PASSWORD` | Contraseña de la DB |
| `SERVER_PORT` | Puerto de ejecución (ej. 8080) |
| `GEMINI_API_KEY` | Clave privada de Google AI Studio |

> **Nota de Seguridad:** El archivo `application.properties` utiliza estas variables mediante la sintaxis `${VARIABLE}`, asegurando que ninguna credencial quede expuesta en el historial de Git.

---
