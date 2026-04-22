# ProyectoFinal
App para el proyecto final

# ⚓ AnchorMind Pro - Sistema de Apoyo Emocional con IA

Plataforma integral diseñada para la gestión, seguimiento y análisis de episodios de ansiedad, utilizando modelos de procesamiento de lenguaje natural para brindar soporte personalizado y trazabilidad clínica.
# 🚀 Estado del Proyecto: Fase 8 (Gestión de Identidad & Mappers Jerárquicos)
El sistema ha alcanzado una madurez arquitectónica superior mediante la implementación del patrón DTO (Data Transfer Objects) y la separación estricta de responsabilidades. Se ha eliminado la exposición de entidades JPA, garantizando transacciones seguras y respuestas de IA enriquecidas con protocolos de acción terapéutica.

### 🛠️ Stack Tecnológico
* **Lenguaje:** Java 17(Uso de Records para inmutabilidad)
* **Framework:** Spring Boot 3.4+ /4.0
* **IA:** Google Gemini 2.5 Flash API (v1beta)
* **Base de Datos:** PostgreSQL 15+
* **Migraciones:** Flyway (Database Version Control)
* **Persistencia:**  Spring Data JPA + Hibernate (modo validate)
* **Mapeo:** Arquitectura Manual de DTOs para control total de la carga perezosa (Lazy).
* **Productividad:** Lombok (@RequiredArgsConstructor, @Data, @Getter)
* **Mapeo:** Arquitectura de Mappers Componentizados (Inyección de mappers para objetos anidados).

---

## 🧠 Arquitectura de Capas y Flujo de Datos
El sistema implementa una Arquitectura Multicapa para garantizar el desacoplamiento:

1.  **Capa de Presentación (REST Controllers):** Maneja únicamente DTOs. Inyección de dependencias por constructor para máxima testeabilidad.
2.  **Capa de Servicio (Business Logic):**Orquestación de procesos. Uso de @Transactional para gestionar la persistencia y la hidratación de objetos relacionados.
3.  **Capa de Dominio (Entities):** Modelos JPA que representan fielmente las tablas de PostgreSQL, protegidos tras la capa de servicio.
4.  **Capa de Transferencia (DTOs/Records):**Objetos inmutables que definen el contrato de comunicación con el Frontend, evitando la recursión infinita y errores de sesión.
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

### ✅ Fase 5: Lógica de Negocio & Desacoplamiento (Hito Completado)
* **Implementación de Patrón DTO:** Creación de AnxietyRecordDTO, UserDTO y ClinicDTO utilizando Java 17 Records. Esto garantiza que los datos que viajan al cliente sean inmutables y seguros.

* **Resolución de LazyInitializationException:** Se dominó el contexto transaccional para permitir la navegación por el grafo de objetos (Registro -> Usuario -> Clínica) sin perder la sesión de Hibernate.

* **Enriquecimiento de Análisis de IA:** El prompt de Gemini se optimizó para devolver una estructura JSON compleja que incluye:

- awarenessMessage: Mensaje de validación emocional.

- actionSteps: Array de pasos técnicos (ej: técnica 5-4-3-2-1) para intervención inmediata.

* **Refactorización de Controladores:** Migración hacia el uso de ResponseEntity y eliminación de inyecciones de campo (@Autowired) en favor de la inyección por constructor, siguiendo las mejores prácticas de Spring.

* **Manejo de Tipos Complejos:** Sincronización exitosa de arrays de strings (text[]) de PostgreSQL con colecciones de Java para los pasos de acción.

### ✅ Fase 6: Inicialización y Consistencia de Datos
* Data Seeding Profesional: Implementación de un escenario multi-clínica mediante CommandLineRunners para garantizar datos de prueba consistentes en cada arranque.

* Validación de Existencia: Lógica de pre-verificación para evitar la duplicidad de registros base (testUser1, testUser2, etc.) durante el despliegue.

* Integridad Referencial: Consolidación de las relaciones entre usuarios, clínicas y registros de ansiedad en el entorno de desarrollo.

### ✅ Fase 7: Blindaje del Sistema (Global Exception Handling)
* El Escudo Arquitectónico: Implementación de un @ControllerAdvice centralizado para capturar y estandarizar errores en toda la API.

* Normalización de Respuestas de Error: Creación de una estructura ErrorResponse uniforme (timestamp, status, error, message, path) para mejorar la experiencia de consumo del Frontend.

* Manejo de Excepciones Específicas: * Captura de EntityNotFoundException para recursos inexistentes (HTTP 404).

- Gestión de IllegalArgumentException para errores de validación de negocio (HTTP 400).

- Control genérico de excepciones para evitar fugas de información técnica en errores 500.

### ✅ Fase 8: Gestión de Identidad y Flujo de Usuario (Actual)
* Expansión del Perfil de Usuario: Evolución de la entidad User para incluir campos críticos: fullName, email, password y createdAt.

* Control de Versiones de DB (Flyway V3 & V4): * V3: Migración manual para detalles de perfil.

- V4: Incorporación de columna de credenciales (password).

- Resolución de Conflictos: Sincronización técnica exitosa entre Hibernate (modo validate) y Flyway mediante técnicas de baseline manual y gestión de checksums.

* Arquitectura de Mappers Jerárquicos:

- Creación de UserMapper y AnxietyRecordMapper como componentes Spring (@Component).

- Implementación de Inyección de Dependencias entre Mappers: AnxietyRecordMapper ahora delega el procesamiento del usuario a su respectivo experto, eliminando duplicidad de código.

* Ciclo de Creación (POST): Implementación del flujo completo de registro de usuarios utilizando un UserCreateDTO específico, garantizando que el createdAt se gestione automáticamente mediante @PrePersist.

* Validación de Negocio Preventiva: El servicio ahora valida la unicidad del username y email antes de intentar la persistencia, disparando excepciones controladas.

[!IMPORTANT]
Logro Arquitectónico: El sistema ahora es "Type Safe". La base de datos puede evolucionar independientemente de la API pública gracias al mapeo manual de DTOs.

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
