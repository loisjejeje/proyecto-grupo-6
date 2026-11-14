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
        if (usuario != null) {
            usuarios.add(usuario);
        }
    }

    public Usuario buscarUsuarioPorCorreo(String correo) {
        for (Usuario u : usuarios) {
            if (u.getCorreo().equalsIgnoreCase(correo)) {
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
            System.out.println(u.toString());
        }
    }

    public void mostrarComentariosPorTarea(int idTarea) {
        System.out.println("=== COMENTARIOS DE LA TAREA #" + idTarea + " ===");
        for (Comentario c : comentarios) {
            if (c.getIdTarea() == idTarea) {
                System.out.println(c.toString());
            }
        }
    }
}