package clases;

import java.util.ArrayList;
import java.util.List;

public class GestorReportes {
    private List<Notificacion> notificaciones;
    private List<RegistroActividad> historial;

    public GestorReportes() {
        this.notificaciones = new ArrayList<>();
        this.historial = new ArrayList<>();
    }

    public void enviarNotificacion(Notificacion notificacion) {
        if (notificacion != null) {
            notificaciones.add(notificacion);
        }
    }

    public void registrarEvento(RegistroActividad evento) {
        if (evento != null) {
            historial.add(evento);
        }
    }

    public void mostrarNotificacionesPendientes(String destinatario) {
        System.out.println("=== NOTIFICACIONES PARA: " + destinatario.toUpperCase() + " ===");
        for (Notificacion n : notificaciones) {
            if (n.getDestinatario().equalsIgnoreCase(destinatario)) {
                System.out.println(n);
            }
        }
    }

    public double calcularPorcentajeAvance(int totalTareas, int tareasCompletadas) {
        if (totalTareas < 0 || tareasCompletadas < 0) {
            throw new IllegalArgumentException("Los valores de tareas no pueden ser negativos.");
        }
        if (tareasCompletadas > totalTareas) {
            throw new IllegalArgumentException("Las tareas completadas no pueden superar el total.");
        }
        return (totalTareas > 0) ? (tareasCompletadas * 100.0 / totalTareas) : 0.0;
    }

    public void generarReporteAvance(String nombreProyecto, int totalTareas, int tareasCompletadas) {
        double porcentaje = calcularPorcentajeAvance(totalTareas, tareasCompletadas);
        System.out.println("=== REPORTE DE AVANCE DEL PROYECTO ===");
        System.out.println("Proyecto: " + nombreProyecto);
        System.out.println("Tareas Completadas: " + tareasCompletadas + " de " + totalTareas);
        System.out.printf("Progreso General: %.2f%%\n", porcentaje);
    }

    public void mostrarHistorial() {
        System.out.println("=== HISTORIAL DE ACTIVIDAD DEL SISTEMA ===");
        for (RegistroActividad r : historial) {
            System.out.println(r);
        }
    }

    public List<Notificacion> getNotificaciones() { return notificaciones; }
    public List<RegistroActividad> getHistorial() { return historial; }
}