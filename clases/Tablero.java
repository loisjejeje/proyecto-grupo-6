package clases;

import java.util.ArrayList;
import java.util.List;

public class Tablero {
    private int idTablero;
    private String nombre;
    private List<Tarea> tareas;

    public Tablero(int idTablero, String nombre) {
        this.idTablero = idTablero;
        this.nombre = nombre;
        this.tareas = new ArrayList<>();
    }

    public void agregarTarea(Tarea tarea) {
        if (tarea != null) {
            this.tareas.add(tarea);
        }
    }

    public boolean eliminarTarea(int idTarea) {
        return tareas.removeIf(t -> t.getIdTarea() == idTarea);
    }

    public Tarea buscarTarea(int idTarea) {
        for (Tarea t : tareas) {
            if (t.getIdTarea() == idTarea) {
                return t;
            }
        }
        return null;
    }

    public void mostrarTareas() {
        System.out.println("=== TABLERO: " + nombre.toUpperCase() + " ===");
        if (tareas.isEmpty()) {
            System.out.println("No hay tareas registradas.");
            return;
        }
        for (Tarea t : tareas) {
            System.out.println(t.toString());
        }
    }

    public int getIdTablero() { return idTablero; }
    public String getNombre() { return nombre; }
    public List<Tarea> getTareas() { return tareas; }
}