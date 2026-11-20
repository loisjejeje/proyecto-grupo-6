import clases.Proyecto;
import clases.Tarea;

import javax.swing.*;
import java.awt.*;

public class InterfazTablero extends JFrame {

    public static final String[] ESTADOS = {"Pendiente", "En Proceso", "Terminada"};

    private final Proyecto proyecto;
    private final DefaultListModel<Tarea>[] modelos;
    private final JList<Tarea>[] listas;
    private final JLabel lblDetalle = new JLabel(" ");

    @SuppressWarnings("unchecked")
    public InterfazTablero(Proyecto proyecto) {
        this.proyecto = proyecto;

        setTitle("Tablero de tareas - " + proyecto.getNombre());
        setSize(900, 540);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        modelos = new DefaultListModel[ESTADOS.length];
        listas = new JList[ESTADOS.length];

        JPanel columnas = new JPanel(new GridLayout(1, ESTADOS.length, 10, 10));
        columnas.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));

        for (int i = 0; i < ESTADOS.length; i++) {
            final int indice = i;
            modelos[i] = new DefaultListModel<>();
            listas[i] = new JList<>(modelos[i]);
            listas[i].setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            listas[i].setCellRenderer(new DefaultListCellRenderer() {
                @Override
                public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                              boolean isSelected, boolean cellHasFocus) {
                    super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                    if (value instanceof Tarea) {
                        Tarea t = (Tarea) value;
                        setText("#" + t.getIdTarea() + "  " + t.getTitulo() + "  [" + t.getPrioridad() + "]");
                    }
                    return this;
                }
            });

            listas[i].addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting() && !listas[indice].isSelectionEmpty()) {
                    for (int j = 0; j < listas.length; j++) {
                        if (j != indice) listas[j].clearSelection();
                    }
                    mostrarDetalle(listas[indice].getSelectedValue());
                }
            });

            JScrollPane scroll = new JScrollPane(listas[i]);
            scroll.setBorder(BorderFactory.createTitledBorder(ESTADOS[i]));
            columnas.add(scroll);
        }

        JButton btnNueva = new JButton("Nueva tarea");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnIzq = new JButton("< Mover");
        JButton btnDer = new JButton("Mover >");

        btnNueva.addActionListener(e -> nuevaTarea());
        btnEditar.addActionListener(e -> editarTarea());
        btnEliminar.addActionListener(e -> eliminarTarea());
        btnIzq.addActionListener(e -> moverTarea(-1));
        btnDer.addActionListener(e -> moverTarea(1));

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        botones.add(btnNueva);
        botones.add(btnEditar);
        botones.add(btnEliminar);
        botones.add(btnIzq);
        botones.add(btnDer);

        lblDetalle.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        JPanel sur = new JPanel(new BorderLayout());
        sur.add(lblDetalle, BorderLayout.NORTH);
        sur.add(botones, BorderLayout.CENTER);

        add(columnas, BorderLayout.CENTER);
        add(sur, BorderLayout.SOUTH);

        refrescar();
    }

    public static Proyecto crearProyectoDemo() {
        Proyecto p = new Proyecto(1, "Proyecto Grupo 6", "Sistema de gestion de tareas",
                "13/11/2026", "21/11/2026");

        Tarea t1 = p.crearTarea("Disenar interfaz de login", "Pantalla de inicio de sesion",
                "13/11/2026", "16/11/2026", "Alta");
        t1.cambiarEstado("Terminada");

        Tarea t2 = p.crearTarea("Crear vista del tablero", "Tres columnas con tareas",
                "14/11/2026", "20/11/2026", "Alta");
        t2.cambiarEstado("En Proceso");

        p.crearTarea("Escribir pruebas manuales", "Llenar el Anexo C",
                "15/11/2026", "20/11/2026", "Media");
        p.crearTarea("Redactar informe del Hito 3", "Compilar secciones del grupo",
                "18/11/2026", "21/11/2026", "Media");
        return p;
    }

    private int indiceEstado(String estado) {
        for (int i = 0; i < ESTADOS.length; i++) {
            if (ESTADOS[i].equals(estado)) return i;
        }
        return -1;
    }

    private void refrescar() {
        for (DefaultListModel<Tarea> m : modelos) {
            m.clear();
        }
        for (Tarea t : proyecto.getTableroPrincipal().getTareas()) {
            int col = indiceEstado(t.getEstado());
            if (col >= 0) modelos[col].addElement(t);
        }
        lblDetalle.setText(" ");
    }

    private void mostrarDetalle(Tarea t) {
        lblDetalle.setText("Descripcion: " + t.getDescripcion()
                + "   |   Inicio: " + t.getFechaInicio()
                + "   |   Vence: " + t.getFechaVencimiento()
                + "   |   Prioridad: " + t.getPrioridad());
    }

    private Tarea tareaSeleccionada() {
        for (JList<Tarea> lista : listas) {
            if (!lista.isSelectionEmpty()) return lista.getSelectedValue();
        }
        JOptionPane.showMessageDialog(this, "Selecciona una tarea primero",
                "Aviso", JOptionPane.WARNING_MESSAGE);
        return null;
    }

    private void seleccionar(Tarea t) {
        int col = indiceEstado(t.getEstado());
        if (col >= 0) listas[col].setSelectedValue(t, true);
    }

    private void nuevaTarea() {
        InterfazTareas dialogo = new InterfazTareas(this, proyecto, null);
        dialogo.setVisible(true);
        if (dialogo.fueGuardado()) {
            refrescar();
        }
    }

    private void editarTarea() {
        Tarea t = tareaSeleccionada();
        if (t == null) return;
        InterfazTareas dialogo = new InterfazTareas(this, proyecto, t);
        dialogo.setVisible(true);
        if (dialogo.fueGuardado()) {
            refrescar();
            seleccionar(t);
        }
    }

    private void eliminarTarea() {
        Tarea t = tareaSeleccionada();
        if (t == null) return;
        int opcion = JOptionPane.showConfirmDialog(this,
                "Eliminar la tarea \"" + t.getTitulo() + "\"?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            proyecto.getTableroPrincipal().eliminarTarea(t.getIdTarea());
            refrescar();
        }
    }

    private void moverTarea(int direccion) {
        Tarea t = tareaSeleccionada();
        if (t == null) return;
        int destino = indiceEstado(t.getEstado()) + direccion;
        if (destino < 0 || destino >= ESTADOS.length) {
            JOptionPane.showMessageDialog(this, "La tarea no se puede mover mas en esa direccion",
                    "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        t.cambiarEstado(ESTADOS[destino]);
        refrescar();
        seleccionar(t);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InterfazTablero(crearProyectoDemo()).setVisible(true);
        });
    }
}
