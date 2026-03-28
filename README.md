# ProyectoFinal
App para el proyecto final

# ⚓ AnchorMind Pro - Sistema de Apoyo Emocional con IA

Plataforma integral diseñada para la gestión, seguimiento y análisis de episodios de ansiedad, utilizando modelos de procesamiento de lenguaje natural para brindar soporte personalizado.

## 🚀 Estado del Proyecto: Fase 2 (Backend Core & IA)
Actualmente, el sistema cuenta con un motor de backend funcional capaz de transformar relatos de usuario en datos clínicos estructurados mediante Inteligencia Artificial.

### 🛠️ Stack Tecnológico
* **Lenguaje:** Java 17
* **Framework:** Spring Boot 3.x
* **IA:** Google Gemini 2.5 Flash API (v1beta)
* **Base de Datos:** PostgreSQL
* **Comunicación:** Spring WebFlux (WebClient)
* **Persistencia:** Spring Data JPA + Hibernate

---

## 🧠 Arquitectura de Análisis de IA
El sistema implementa un flujo de "Texto a Datos" (Text-to-Data):
1.  **Ingesta:** El controlador recibe un `rawInput` (relato del usuario).
2.  **Prompt Engineering:** Se construye una instrucción estructurada para garantizar respuestas en formato JSON puro.
3.  **Procesamiento:** El servicio interactúa con Gemini 2.5 para identificar niveles de ansiedad y técnicas de mitigación.
4.  **Mapeo:** Uso de DTOs (`AIAnalysisResponse`) para deserializar la respuesta de la IA y persistirla en columnas específicas de la DB.

---

## 📋 Bitácora de Logros (Log de Progreso)

### ✅ Fase 1: Infraestructura Base
* Implementación de **Git Flow** profesional (`main`, `develop`, `features`).
* Configuración de seguridad mediante `.gitignore` y variables de entorno para protección de API Keys.
* Modelado inicial de la base de datos en PostgreSQL.

### ✅ Fase 2: El Cerebro del Sistema (IA)
* **Conexión Exitosa:** Superación de errores 404 mediante la actualización a la API v1beta de Gemini 2.5 Flash.
* **Sanitización de Datos:** Implementación de lógica para limpiar delimitadores Markdown (`json ... `) de las respuestas del modelo.
* **Unificación Lingüística:** Estandarización de variables al inglés (`anxietyLevel`, `triggerIdentified`, `technique`, `applicability`) para coherencia en el código.

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

## 📬 Próximos Pasos (Fase 3)
* **Identidad y Roles:** Implementación de entidad `User` para diferenciar Pacientes y Profesionales.
* **Soft Delete:** Lógica de baja lógica para preservar integridad referencial médica.
* **Frontend:** Inicio de la interfaz en React para sustituir pruebas manuales en Insomnia.