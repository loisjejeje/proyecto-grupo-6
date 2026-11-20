import clases.Proyecto;
import clases.Tarea;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

public class InterfazTareas extends JDialog {

    public static final int MAX_TITULO = 50;
    private static final String[] PRIORIDADES = {"Alta", "Media", "Baja"};
    private static final DateTimeFormatter FORMATO_FECHA =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    private final Proyecto proyecto;
    private final Tarea existente;
    private boolean guardado = false;

    private JTextField txtTitulo;
    private JTextField txtInicio;
    private JTextField txtVencimiento;
    private JComboBox<String> cmbPrioridad;
    private JComboBox<String> cmbEstado;
    private JTextArea txtDescripcion;

    public InterfazTareas(JFrame padre, Proyecto proyecto, Tarea existente) {
        super(padre, existente == null ? "Nueva tarea" : "Editar tarea", true);
        this.proyecto = proyecto;
        this.existente = existente;

        setSize(460, 470);
        setLocationRelativeTo(padre);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        txtTitulo = new JTextField();
        txtInicio = new JTextField(LocalDate.now().format(FORMATO_FECHA));
        txtVencimiento = new JTextField();
        cmbPrioridad = new JComboBox<>(PRIORIDADES);
        cmbPrioridad.setSelectedItem("Media");
        cmbEstado = new JComboBox<>(InterfazTablero.ESTADOS);

        JPanel campos = new JPanel(new GridLayout(5, 2, 10, 8));
        campos.add(new JLabel("Titulo:"));
        campos.add(txtTitulo);
        campos.add(new JLabel("Inicio (dd/mm/aaaa):"));
        campos.add(txtInicio);
        campos.add(new JLabel("Vence (dd/mm/aaaa):"));
        campos.add(txtVencimiento);
        campos.add(new JLabel("Prioridad:"));
        campos.add(cmbPrioridad);
        campos.add(new JLabel("Estado:"));
        campos.add(cmbEstado);

        txtDescripcion = new JTextArea(4, 20);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(txtDescripcion);
        scroll.setBorder(BorderFactory.createTitledBorder("Descripcion"));

        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        btnGuardar.addActionListener(e -> guardar());
        btnCancelar.addActionListener(e -> dispose());

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botones.add(btnCancelar);
        botones.add(btnGuardar);

        panel.add(campos, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);
        add(panel);

        if (existente == null) {
            cmbEstado.setSelectedItem("Pendiente");
            cmbEstado.setEnabled(false);
        } else {
            txtTitulo.setText(existente.getTitulo());
            txtInicio.setText(existente.getFechaInicio());
            txtVencimiento.setText(existente.getFechaVencimiento());
            cmbPrioridad.setSelectedItem(existente.getPrioridad());
            cmbEstado.setSelectedItem(existente.getEstado());
            txtDescripcion.setText(existente.getDescripcion());
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

    public static String validarFechas(String inicio, String vencimiento) {
        LocalDate fechaInicio = parsearFecha(inicio);
        if (fechaInicio == null) {
            return "La fecha de inicio debe ser una fecha valida con formato dd/mm/aaaa";
        }
        LocalDate fechaVence = parsearFecha(vencimiento);
        if (fechaVence == null) {
            return "La fecha de vencimiento debe ser una fecha valida con formato dd/mm/aaaa";
        }
        if (fechaVence.isBefore(fechaInicio)) {
            return "La fecha de vencimiento no puede ser anterior a la de inicio";
        }
        return null;
    }

    public static LocalDate parsearFecha(String texto) {
        if (texto == null) return null;
        try {
            return LocalDate.parse(texto.trim(), FORMATO_FECHA);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private void guardar() {
        String titulo = txtTitulo.getText().trim();
        String inicio = txtInicio.getText().trim();
        String vence = txtVencimiento.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String prioridad = (String) cmbPrioridad.getSelectedItem();
        String estado = (String) cmbEstado.getSelectedItem();

        String error = validarTitulo(titulo);
        if (error == null) {
            error = validarFechas(inicio, vence);
        }
        if (error != null) {
            JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if (existente == null) {
                proyecto.crearTarea(titulo, descripcion, inicio, vence, prioridad);
            } else {
                existente.setTitulo(titulo);
                existente.setDescripcion(descripcion);
                existente.setFechaInicio(inicio);
                existente.setFechaVencimiento(vence);
                existente.setPrioridad(prioridad);
                existente.cambiarEstado(estado);
            }
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        guardado = true;
        dispose();
    }

    public boolean fueGuardado() {
        return guardado;
    }
}
