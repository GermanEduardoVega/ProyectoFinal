📔 Bitácora de Proyecto: AnchorMind Pro
Estado: Backend - Integración de IA Funcional
Fecha: 15 de Marzo, 2026

🚀 Logros Alcanzados
1. Infraestructura y Base de Datos
   PostgreSQL: Tabla anxiety_records creada y conectada exitosamente.

Spring Data JPA: Implementación de AnxietyRecordRepository para persistencia de datos.

Conectividad: Se verificó que los datos enviados desde Insomnia llegan correctamente a la DB.

2. Integración con Inteligencia Artificial (El "Cerebro")
   Google Gemini API: Superamos los errores 404 mediante:

Actualización al modelo Gemini 2.5 Flash (el modelo activo en 2026).

Corrección de la URL base a la versión v1beta.

Implementación de Variables de Entorno (@Value) para proteger la API Key.

AIService: Creación de un servicio capaz de:

Limpiar el texto del usuario.

Construir un prompt estructurado para obligar a la IA a responder en JSON.

Manejar la comunicación mediante WebClient.

3. Lógica de Deserialización (Análisis de Datos)
   Limpieza de Markdown: Implementamos lógica para remover los delimitadores ```json que devuelve la IA.

Jackson (ObjectMapper): Logramos convertir el String de la IA en un objeto Java (AIAnalysisResponse).

Segmentación de Datos: Ahora el controlador separa la respuesta en:

anxietyLevel (Integer) -> Para analítica futura.

triggerIdentified (String) -> Para el diagnóstico.

technique (String) -> Para la recomendación al usuario.

🛠️ Stack Técnico Actualizado
Backend: Java 17+, Spring Boot 3.x.

IA: Google Gemini 2.5 Flash API.

DB: PostgreSQL.

Cliente de Pruebas: Insomnia / Terminal (cURL).

📋 Pendientes para la próxima sesión
Manejo de Excepciones: Qué pasa si la IA no responde o manda un JSON mal formado.

Frontend (React): Empezar a conectar la interfaz para que el usuario no use Insomnia, sino una pantalla amigable.

Seguridad SSL: Ajustar el WebClient para que no falle con certificados en redes locales restrictivas.