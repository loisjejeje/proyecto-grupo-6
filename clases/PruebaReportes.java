
package clases;

public class PruebaReportes {

    public static void main(String[] args) {

        GestorReportes gestor = new GestorReportes();

        System.out.println("===== PRUEBA 1: CREAR NOTIFICACION =====");

        Notificacion n1 = new Notificacion(
            1, "Carlos",
            "Tienes una nueva tarea asignada",
            "19/11/2026"
        );

        gestor.enviarNotificacion(n1);
        System.out.println(n1);

        System.out.println("\n===== PRUEBA 2: MARCAR NOTIFICACION COMO LEIDA =====");

        n1.marcarComoLeida();
        System.out.println("¿Fue leida?: " + n1.isLeida());

        System.out.println("\n===== PRUEBA 3: MOSTRAR NOTIFICACIONES =====");

        Notificacion n2 = new Notificacion(
            2, "Carlos",
            "Se actualizo el proyecto",
            "19/11/2026"
        );

        gestor.enviarNotificacion(n2);
        gestor.mostrarNotificacionesPendientes("Carlos");

        System.out.println("\n===== PRUEBA 4: REGISTRAR ACTIVIDAD =====");

        RegistroActividad actividad = new RegistroActividad(
            1, "Carlos",
            "Creo una nueva tarea",
            "Modulo de tareas",
            "19/11/2026 21:00"
        );

        gestor.registrarEvento(actividad);
        gestor.mostrarHistorial();

        System.out.println("\n===== PRUEBA 5: REPORTE DE AVANCE =====");

        gestor.generarReporteAvance(
            "Sistema de gestion de tareas",
            10, 7
        );
    }
}
