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
                System.out.println(n.toString());
            }
        }
    }

    public void generarReporteAvance(String nombreProyecto, int totalTareas, int tareasCompletadas) {
        double porcentaje = (totalTareas > 0) ? (tareasCompletadas * 100.0 / totalTareas) : 0.0;
        System.out.println("=== REPORTE DE AVANCE DEL PROYECTO ===");
        System.out.println("Proyecto: " + nombreProyecto);
        System.out.println("Tareas Completadas: " + tareasCompletadas + " de " + totalTareas);
        System.out.printf("Progreso General: %.2f%%\n", porcentaje);
    }

    public void mostrarHistorial() {
        System.out.println("=== HISTORIAL DE ACTIVIDAD DEL SISTEMA ===");
        for (RegistroActividad r : historial) {
            System.out.println(r.toString());
        }
    }
}