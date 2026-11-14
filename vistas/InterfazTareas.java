import javax.swing.*;
import java.awt.*;

public class InterfazTareas extends JDialog {

    public static final String PENDIENTE = "Pendiente";
    public static final String EN_PROCESO = "En Proceso";
    public static final String TERMINADA = "Terminada";
    public static final String[] ESTADOS = {PENDIENTE, EN_PROCESO, TERMINADA};
    public static final int MAX_TITULO = 50;

    public static class TareaSimple {
        private String titulo;
        private String descripcion;
        private String estado;

        public TareaSimple(String titulo, String descripcion, String estado) {
            this.titulo = titulo;
            this.descripcion = descripcion;
            this.estado = estado;
        }

        public String getTitulo() { return titulo; }
        public void setTitulo(String titulo) { this.titulo = titulo; }
        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
        public String getEstado() { return estado; }
        public void setEstado(String estado) { this.estado = estado; }

        @Override
        public String toString() { return titulo; }
    }

    private JTextField txtTitulo;
    private JTextArea txtDescripcion;
    private JComboBox<String> cmbEstado;
    private TareaSimple tarea;
    private TareaSimple resultado;

    public InterfazTareas(JFrame padre, TareaSimple existente) {
        super(padre, existente == null ? "Nueva tarea" : "Editar tarea", true);
        this.tarea = existente;

        setSize(420, 350);
        setLocationRelativeTo(padre);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel campos = new JPanel(new GridLayout(3, 1, 5, 5));

        JPanel filaTitulo = new JPanel(new BorderLayout(5, 5));
        filaTitulo.add(new JLabel("Titulo:"), BorderLayout.NORTH);
        txtTitulo = new JTextField();
        filaTitulo.add(txtTitulo, BorderLayout.CENTER);

        JPanel filaEstado = new JPanel(new BorderLayout(5, 5));
        filaEstado.add(new JLabel("Estado:"), BorderLayout.NORTH);
        cmbEstado = new JComboBox<>(ESTADOS);
        filaEstado.add(cmbEstado, BorderLayout.CENTER);

        campos.add(filaTitulo);
        campos.add(filaEstado);
        campos.add(new JLabel("Descripcion:"));

        txtDescripcion = new JTextArea(4, 20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);

        JPanel centro = new JPanel(new BorderLayout(5, 5));
        centro.add(campos, BorderLayout.NORTH);
        centro.add(new JScrollPane(txtDescripcion), BorderLayout.CENTER);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        btnGuardar.addActionListener(e -> guardar());
        btnCancelar.addActionListener(e -> dispose());

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botones.add(btnCancelar);
        botones.add(btnGuardar);

        panel.add(centro, BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);
        add(panel);

        if (existente != null) {
            txtTitulo.setText(existente.getTitulo());
            txtDescripcion.setText(existente.getDescripcion());
            cmbEstado.setSelectedItem(existente.getEstado());
        }
    }

    public static String validarTitulo(String titulo) {
        if (titulo == null || titulo.trim().isEmpty()) {
            return "El titulo es obligatorio";
        }
        if (titulo.trim().length() > MAX_TITULO) {
            return "El titulo no puede superar " + MAX_TITULO + " caracteres";
        }
        if (!titulo.trim().matches("[\\p{L}0-9 .,_\\-]+")) {
            return "El titulo contiene caracteres no permitidos (solo letras, numeros, espacios y . , _ -)";
        }
        return null;
    }

    private void guardar() {
        String titulo = txtTitulo.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String estado = (String) cmbEstado.getSelectedItem();

        String error = validarTitulo(titulo);
        if (error != null) {
            JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (tarea == null) {
            resultado = new TareaSimple(titulo, descripcion, estado);
        } else {
            tarea.setTitulo(titulo);
            tarea.setDescripcion(descripcion);
            tarea.setEstado(estado);
            resultado = tarea;
        }
        dispose();
    }

    public TareaSimple getResultado() {
        return resultado;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InterfazTareas d = new InterfazTareas(null, null);
            d.setVisible(true);
            System.exit(0);
        });
    }
}
