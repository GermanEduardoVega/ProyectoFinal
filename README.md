# ProyectoFinal
App para el proyecto final

# ⚓ AnchorMind Pro - Sistema de Apoyo Emocional con IA

Plataforma integral diseñada para la gestión, seguimiento y análisis de episodios de ansiedad, utilizando modelos de procesamiento de lenguaje natural para brindar soporte personalizado y trazabilidad clínica.

# 🚀 Estado del Proyecto: Fase 4 (Estructura Multi-Institucional & Integridad)
El sistema ha madurado hacia una arquitectura profesional y versionada, permitiendo ahora la vinculación de usuarios a instituciones (Clínicas) y garantizando que el código Java y la base de datos PostgreSQL sean un espejo exacto gracias a procesos de migración controlados.

### 🛠️ Stack Tecnológico
* **Lenguaje:** Java 17
* **Framework:** Spring Boot 3.4+ /4.0
* **IA:** Google Gemini 3 Flash API (v1beta)
* **Base de Datos:** PostgreSQL 15+
* **Migraciones:** Flyway (Database Version Control)
* **Comunicación:** Spring WebFlux (WebClient)
* **Persistencia:** Spring Data JPA + Hibernate (modo validate)
* **Productividad:** Lombok (Uso intensivo de @Builder y @Data)

---

## 🧠 Arquitectura Institucional & Relacional
El sistema implementa una jerarquía de datos expandida:
1.  **Escalabilidad Clínica:** Introducción de la entidad Clinic. Los usuarios poseen una vinculación @ManyToOne (Lazy Loading) con instituciones.
2.  **Persistencia Inmutable:**Migración del modelo automático a un sistema de control de versiones de base de datos con Flyway.
3.  **Sincronización Java-SQL:** Mapeo exacto de campos técnicos (triggerIdentified, applicability, aiResponseJson) validados mediante Hibernate.
4.  **Validación Relacional:** Integración física de llaves foráneas reales (clinic_id) para trazabilidad multi-inquilino.

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

### ✅ Fase 3: Identidad y Persistencia 
* **Modelo de Identidad:** Entidad User con roles (PATIENT, PROFESSIONAL) y soporte para Soft Delete (active).

* **Control de Versiones de DB:** Implementación de Flyway para gestionar migraciones de esquema (V1__Initial_Schema.sql).

* **Relaciones JPA:** Vinculación @ManyToOne entre episodios de ansiedad y perfiles de usuario.

* **Desacoplamiento:** Creación de servicios especializados (UserService, AIServiceR) para una arquitectura limpia.

### ✅ Fase 4: Infraestructura Institucional (Hilo Actual)
* **Escalabilidad Multi-Clínica:** Creación de la entidad Clinic y su repositorio.

* **Database Version Control:** protegiendo tu base de datos. Implementación exitosa de scripts Flyway está configurado y sincronizado:

    * V1__Initial_Schema.sql: Estructura base de usuarios y registros de ansiedad sincronizada.

    * V2__Create_Clinic_Table.sql: Evolución del esquema para soportar instituciones.

* **Tests de Humo (Integration Tests):** Uso de CommandLineRunners para validar la creación de clínicas y la vinculación física de usuarios mediante llaves foráneas en el arranque. Relaciones JPA están funcionando (un Usuario ya puede "pertenecer" a una Clínica)

* **Sincronización Java-SQL:** Resolución de inconsistencias en el mapeo de campos complejos (triggerIdentified, applicability, aiResponseJson).

* **Protección de Esquema:** Activación de ddl-auto=validate, obligando a que la base de datos sea el espejo exacto de las entidades Java.

* **Optimización Relacional:** Implementación de FetchType.LAZY para mejorar el rendimiento y gestión de memoria del backend.

⚠️ Notas Técnicas de la Fase
-   **Integridad de Datos:** No se permite la creación de tablas por parte de Hibernate. Cualquier cambio estructural debe realizarse mediante un nuevo script V3__...sql en Flyway.

-   **Lazy Initialization:** El error LazyInitializationException detectado en esta fase confirma la necesidad de transicionar hacia el uso de DTOs (Records) para la transferencia de datos a la capa de presentación.
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
