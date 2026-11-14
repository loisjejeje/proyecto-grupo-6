package clases;

public class Notificacion {
    private int idNotificacion;
    private String destinatario;
    private String mensaje;
    private String fechaEnvio;
    private boolean leida;

    public Notificacion(int idNotificacion, String destinatario, String mensaje, String fechaEnvio) {
        if (destinatario == null || destinatario.trim().isEmpty()) {
            throw new IllegalArgumentException("Error: El destinatario es obligatorio.");
        }
        if (mensaje == null || mensaje.trim().isEmpty()) {
            throw new IllegalArgumentException("Error: El mensaje de notificacion no puede estar vacio.");
        }
        this.idNotificacion = idNotificacion;
        this.destinatario = destinatario;
        this.mensaje = mensaje;
        this.fechaEnvio = fechaEnvio;
        this.leida = false;
    }

    public void marcarComoLeida() {
        this.leida = true;
    }

    public int getIdNotificacion() { return idNotificacion; }
    public String getDestinatario() { return destinatario; }
    public String getMensaje() { return mensaje; }
    public String getFechaEnvio() { return fechaEnvio; }
    public boolean isLeida() { return leida; }

    @Override
    public String toString() {
        String estado = leida ? "Leida" : "No leida";
        return "[" + idNotificacion + "] Para: " + destinatario + " | " + mensaje + " (" + fechaEnvio + " - " + estado + ")";
    }
}