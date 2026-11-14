package clases;

public class RegistroActividad {
    private int idRegistro;
    private String usuarioResponsable;
    private String accionRealizada;
    private String moduloAfectado;
    private String fechaHora;

    public RegistroActividad(int idRegistro, String usuarioResponsable, String accionRealizada, String moduloAfectado, String fechaHora) {
        this.idRegistro = idRegistro;
        this.usuarioResponsable = usuarioResponsable;
        this.accionRealizada = accionRealizada;
        this.moduloAfectado = moduloAfectado;
        this.fechaHora = fechaHora;
    }

    public int getIdRegistro() { return idRegistro; }
    public String getUsuarioResponsable() { return usuarioResponsable; }
    public String getAccionRealizada() { return accionRealizada; }
    public String getModuloAfectado() { return moduloAfectado; }
    public String getFechaHora() { return fechaHora; }

    @Override
    public String toString() {
        return "[" + fechaHora + "] [" + moduloAfectado + "] " + usuarioResponsable + ": " + accionRealizada;
    }
}