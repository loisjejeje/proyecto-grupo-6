package clases;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Tarea {
    private static final List<String> ESTADOS_VALIDOS = Arrays.asList("Pendiente", "En Proceso", "Terminada");
    private static final List<String> PRIORIDADES_VALIDAS = Arrays.asList("Alta", "Media", "Baja");

    private int idTarea;
    private String titulo;
    private String descripcion;
    private String fechaInicio;
    private String fechaVencimiento;
    private String prioridad;
    private String estado;
    private List<String> responsables;

    public Tarea(int idTarea, String titulo, String descripcion, String fechaInicio, String fechaVencimiento, String prioridad) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El titulo de la tarea es obligatorio.");
        }
        this.idTarea = idTarea;
        this.titulo = titulo.trim();
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaVencimiento = fechaVencimiento;
        setPrioridad((prioridad == null || prioridad.trim().isEmpty()) ? "Media" : prioridad);
        this.estado = "Pendiente";
        this.responsables = new ArrayList<>();
    }

    public void cambiarEstado(String nuevoEstado) {
        if (nuevoEstado == null || !ESTADOS_VALIDOS.contains(nuevoEstado)) {
            throw new IllegalArgumentException("Estado no valido. Use: Pendiente, En Proceso o Terminada.");
        }
        this.estado = nuevoEstado;
    }

    public void asignarResponsable(String usuario) {
        if (usuario != null && !usuario.trim().isEmpty() && !responsables.contains(usuario)) {
            responsables.add(usuario);
        }
    }

    public int getIdTarea() { return idTarea; }
    public String getTitulo() { return titulo; }
    public String getDescripcion() { return descripcion; }
    public String getFechaInicio() { return fechaInicio; }
    public String getFechaVencimiento() { return fechaVencimiento; }
    public String getPrioridad() { return prioridad; }
    public String getEstado() { return estado; }
    public List<String> getResponsables() { return responsables; }

    public void setTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("El titulo de la tarea es obligatorio.");
        }
        this.titulo = titulo.trim();
    }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public void setFechaInicio(String fechaInicio) { this.fechaInicio = fechaInicio; }
    public void setFechaVencimiento(String fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public void setPrioridad(String prioridad) {
        if (prioridad == null || !PRIORIDADES_VALIDAS.contains(prioridad)) {
            throw new IllegalArgumentException("Prioridad no valida. Use: Alta, Media o Baja.");
        }
        this.prioridad = prioridad;
    }

    @Override
    public String toString() {
        return "[" + idTarea + "] " + titulo + " (" + estado + ") | Prioridad: " + prioridad + " | Responsables: " + responsables;
    }
}