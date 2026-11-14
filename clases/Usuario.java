package clases;

public class Usuario {
    private int idUsuario;
    private String nombre;
    private String correo;
    private String password;
    private String rol;

    public Usuario(int idUsuario, String nombre, String correo, String password, String rol) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Error: El nombre de usuario es obligatorio.");
        }
        if (correo == null || !correo.contains("@")) {
            throw new IllegalArgumentException("Error: Correo electronico no valido.");
        }
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
        this.password = password;
        this.rol = (rol == null || rol.trim().isEmpty()) ? "Miembro" : rol;
    }

    public boolean iniciarSesion(String correoIngresado, String passwordIngresada) {
        return this.correo.equalsIgnoreCase(correoIngresado) && this.password.equals(passwordIngresada);
    }

    public void cambiarRol(String nuevoRol) {
        if (nuevoRol != null && (nuevoRol.equals("Administrador") || nuevoRol.equals("Lider") || nuevoRol.equals("Miembro"))) {
            this.rol = nuevoRol;
        } else {
            throw new IllegalArgumentException("Rol no valido. Use: Administrador, Lider o Miembro.");
        }
    }

    public int getIdUsuario() { return idUsuario; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getRol() { return rol; }

    @Override
    public String toString() {
        return "[" + idUsuario + "] " + nombre + " (" + rol + ") - " + correo;
    }
}