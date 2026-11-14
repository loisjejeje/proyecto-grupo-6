# Proyecto Grupo 6 
## Cómo ejecutar
### Requisitos
- Java JDK 17 o superior (ajusta a la versión que usen)
- IDE: NetBeans / IntelliJ IDEA / Eclipse

### Configuración
1. Clonar el repositorio:
   git clone https://github.com/loisjejeje/proyecto-grupo-6.git
2. Abrir la carpeta del proyecto en el IDE.
3. Verificar que las carpetas `clases` y `vistas` estén dentro de `src`.

### Pasos para ejecutar
1. Abrir la clase principal del proyecto.
2. Ejecutar con Run (Shift + F10 en IntelliJ, F6 en NetBeans).
3. Se abrirá el menú principal; entrar a "Tableros y Tareas".

## Pruebas manuales (Módulo Tableros y Tareas)

| N° | Caso de prueba | Datos de entrada | Resultado esperado | Resultado obtenido | ¿Cumple? |
|---|---|---|---|---|---|
| 1 | Crear tarea válida | Título: Estudiar Java, estado Pendiente | La tarea aparece en la columna Pendiente | La tarea se creó y mostró correctamente | Sí |
| 2 | Mover tarea entre columnas | Probar tablero, botón Mover > | La tarea pasa a la siguiente columna | La tarea cambió de estado y columna | Sí |
| 3 | Título vacío | Título vacío | Mensaje de error | «El título es obligatorio» | Sí |
| 4 | Caracteres no permitidos | @@## | Mensaje de error | «El título contiene caracteres no permitidos» | Sí |
| 5 | Título de 51 caracteres | 51 letras a | Mensaje de error por el límite | «El título supera el límite de 50 caracteres» | Sí |

