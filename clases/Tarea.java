package clases;

import java.util.ArrayList;
import java.util.List;

public class Tarea {
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
            throw new IllegalArgumentException("Error: El titulo de la tarea es obligatorio.");
        }
        this.idTarea = idTarea;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.fechaVencimiento = fechaVencimiento;
        this.prioridad = (prioridad == null || prioridad.trim().isEmpty()) ? "Media" : prioridad;
        this.estado = "Pendiente";
        this.responsables = new ArrayList<>();
    }

    public void cambiarEstado(String nuevoEstado) {
        if (nuevoEstado != null && (nuevoEstado.equals("Pendiente") || nuevoEstado.equals("En Proceso") || nuevoEstado.equals("Terminada"))) {
            this.estado = nuevoEstado;
        } else {
            throw new IllegalArgumentException("Estado no valido. Use: Pendiente, En Proceso o Terminada.");
        }
    }

    public void asignarResponsable(String nombreUsuario) {
        if (nombreUsuario != null && !nombreUsuario.trim().isEmpty() && !responsables.contains(nombreUsuario)) {
            responsables.add(nombreUsuario);
        }
    }

    public int getIdTarea() { return idTarea; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(String fechaInicio) { this.fechaInicio = fechaInicio; }
    public String getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(String fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }
    public String getPrioridad() { return prioridad; }
    public void setPrioridad(String prioridad) { this.prioridad = prioridad; }
    public String getEstado() { return estado; }
    public List<String> getResponsables() { return responsables; }

    @Override
    public String toString() {
        return "[" + idTarea + "] " + titulo + " (" + estado + ") | Prioridad: " + prioridad +
               " | Inicio: " + fechaInicio + " | Vence: " + fechaVencimiento + " | Responsables: " + responsables;
    }
}