package clases;

public class Proyecto {
    private int idProyecto;
    private String nombre;
    private String descripcion;
    private String fechaInicio;
    private String fechaFin;
    private Tablero tableroPrincipal;
    private int contadorTareas = 1;

    public Proyecto(int idProyecto, String nombre, String descripcion, String fechaInicio, String fechaFin) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Error: El nombre del proyecto es obligatorio.");
        }
        this.idProyecto = idProyecto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.tableroPrincipal = new Tablero(1, "Tablero - " + nombre);
    }

    public Tarea crearTarea(String titulo, String descripcion, String fechaInicio, String fechaVencimiento, String prioridad) {
        Tarea nueva = new Tarea(contadorTareas++, titulo, descripcion, fechaInicio, fechaVencimiento, prioridad);
        tableroPrincipal.agregarTarea(nueva);
        return nueva;
    }

    public int getIdProyecto() { return idProyecto; }
    public String getNombre() { return nombre; }
    public String getDescripcion() { return descripcion; }
    public String getFechaInicio() { return fechaInicio; }
    public String getFechaFin() { return fechaFin; }
    public Tablero getTableroPrincipal() { return tableroPrincipal; }
}