package clases;

public class Comentario {
    private int idComentario;
    private int idTarea;
    private String autor;
    private String contenido;
    private String fechaHora;

    public Comentario(int idComentario, int idTarea, String autor, String contenido, String fechaHora) {
        if (contenido == null || contenido.trim().isEmpty()) {
            throw new IllegalArgumentException("Error: El comentario no puede estar vacio.");
        }
        this.idComentario = idComentario;
        this.idTarea = idTarea;
        this.autor = autor;
        this.contenido = contenido;
        this.fechaHora = fechaHora;
    }

    public int getIdComentario() { return idComentario; }
    public int getIdTarea() { return idTarea; }
    public String getAutor() { return autor; }
    public String getContenido() { return contenido; }
    public void setContenido(String contenido) { this.contenido = contenido; }
    public String getFechaHora() { return fechaHora; }

    @Override
    public String toString() {
        return "[" + fechaHora + "] " + autor + " (Tarea #" + idTarea + "): " + contenido;
    }
}