package clases;

public class Usuario {
    private int idUsuario;
    private String nombre;
    private String correo;
    private String password;
    private String rol;

    public Usuario(int idUsuario, String nombre, String correo, String password, String rol) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del usuario es obligatorio.");
        }
        if (correo == null || !correo.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new IllegalArgumentException("Formato de correo electronico no valido.");
        }
        if (password == null || password.length() < 4) {
            throw new IllegalArgumentException("La contrasena debe tener al menos 4 caracteres.");
        }
        this.idUsuario = idUsuario;
        this.nombre = nombre.trim();
        this.correo = correo.trim().toLowerCase();
        this.password = password;
        this.rol = (rol == null || rol.trim().isEmpty()) ? "Miembro" : rol;
    }

    public boolean iniciarSesion(String correoIngresado, String passwordIngresada) {
        if (correoIngresado == null || passwordIngresada == null) return false;
        return this.correo.equalsIgnoreCase(correoIngresado.trim()) && this.password.equals(passwordIngresada);
    }

    public void cambiarRol(String nuevoRol) {
        if ("Administrador".equals(nuevoRol) || "Lider".equals(nuevoRol) || "Miembro".equals(nuevoRol)) {
            this.rol = nuevoRol;
        } else {
            throw new IllegalArgumentException("Rol no permitido. Use: Administrador, Lider o Miembro.");
        }
    }

    public int getIdUsuario() { return idUsuario; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public String getRol() { return rol; }

    @Override
    public String toString() {
        return "[" + idUsuario + "] " + nombre + " (" + rol + ") - " + correo;
    }
}