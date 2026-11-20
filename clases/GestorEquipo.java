package clases;

import java.util.ArrayList;
import java.util.List;

public class GestorEquipo {
    private List<Usuario> usuarios;
    private List<Comentario> comentarios;

    public GestorEquipo() {
        this.usuarios = new ArrayList<>();
        this.comentarios = new ArrayList<>();
    }

    public void registrarUsuario(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo.");
        }
        for (Usuario u : usuarios) {
            if (u.getCorreo().equalsIgnoreCase(usuario.getCorreo())) {
                throw new IllegalArgumentException("Ya existe un usuario registrado con ese correo.");
            }
        }
        usuarios.add(usuario);
    }

    public Usuario validarAcceso(String correo, String password) {
        for (Usuario u : usuarios) {
            if (u.iniciarSesion(correo, password)) {
                return u;
            }
        }
        return null;
    }

    public void agregarComentario(Comentario comentario) {
        if (comentario != null) {
            comentarios.add(comentario);
        }
    }

    public void mostrarUsuarios() {
        System.out.println("=== MIEMBROS DEL EQUIPO ===");
        for (Usuario u : usuarios) {
            System.out.println(u);
        }
    }

    public void mostrarComentariosPorTarea(int idTarea) {
        System.out.println("=== COMENTARIOS DE LA TAREA #" + idTarea + " ===");
        for (Comentario c : comentarios) {
            if (c.getIdTarea() == idTarea) {
                System.out.println(c);
            }
        }
    }

    public List<Usuario> getUsuarios() { return usuarios; }
    public List<Comentario> getComentarios() { return comentarios; }
}