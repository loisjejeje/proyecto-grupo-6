import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
public class InterfazTablero extends JFrame {

    private final List<InterfazTareas.TareaSimple> tareas = new ArrayList<>();
    private final DefaultListModel<InterfazTareas.TareaSimple>[] modelos;
    private final JList<InterfazTareas.TareaSimple>[] listas;

    @SuppressWarnings("unchecked")
    public InterfazTablero() {

        setTitle("Tablero de tareas");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] estados = InterfazTareas.ESTADOS;
        modelos = new DefaultListModel[estados.length];
        listas = new JList[estados.length];

        JPanel columnas = new JPanel(new GridLayout(1, 3, 10, 10));
        columnas.setBorder(BorderFactory.createEmptyBorder(15, 15, 5, 15));

        for (int i = 0; i < estados.length; i++) {
            final int indice = i;
            modelos[i] = new DefaultListModel<>();
            listas[i] = new JList<>(modelos[i]);
            listas[i].setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            // Solo una tarea seleccionada a la vez en todo el tablero
            listas[i].addListSelectionListener(e -> {
                if (!e.getValueIsAdjusting() && !listas[indice].isSelectionEmpty()) {
                    for (int j = 0; j < listas.length; j++) {
                        if (j != indice) listas[j].clearSelection();
                    }
                }
            });

            JScrollPane scroll = new JScrollPane(listas[i]);
            scroll.setBorder(BorderFactory.createTitledBorder(estados[i]));
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

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        botones.add(btnNueva);
        botones.add(btnEditar);
        botones.add(btnEliminar);
        botones.add(btnIzq);
        botones.add(btnDer);

        add(columnas, BorderLayout.CENTER);
        add(botones, BorderLayout.SOUTH);

        cargarDatosDePrueba();
        refrescar();
    }

    private void cargarDatosDePrueba() {
        tareas.add(new InterfazTareas.TareaSimple("Disenar interfaz de login", "Pantalla de inicio de sesion", InterfazTareas.TERMINADA));
        tareas.add(new InterfazTareas.TareaSimple("Crear vista del tablero", "Tres columnas con tareas", InterfazTareas.EN_PROCESO));
        tareas.add(new InterfazTareas.TareaSimple("Escribir pruebas manuales", "Llenar el Anexo C", InterfazTareas.PENDIENTE));
        tareas.add(new InterfazTareas.TareaSimple("Redactar informe del Hito 2", "Compilar secciones del grupo", InterfazTareas.PENDIENTE));
    }

    private void refrescar() {
        for (DefaultListModel<InterfazTareas.TareaSimple> m : modelos) {
            m.clear();
        }
        for (InterfazTareas.TareaSimple t : tareas) {
            int col = indiceEstado(t.getEstado());
            if (col >= 0) modelos[col].addElement(t);
        }
    }

    private int indiceEstado(String estado) {
        for (int i = 0; i < InterfazTareas.ESTADOS.length; i++) {
            if (InterfazTareas.ESTADOS[i].equals(estado)) return i;
        }
        return -1;
    }

    private InterfazTareas.TareaSimple tareaSeleccionada() {
        for (JList<InterfazTareas.TareaSimple> lista : listas) {
            if (!lista.isSelectionEmpty()) return lista.getSelectedValue();
        }
        JOptionPane.showMessageDialog(this, "Selecciona una tarea primero",
                "Aviso", JOptionPane.WARNING_MESSAGE);
        return null;
    }

    private void nuevaTarea() {
        InterfazTareas dialogo = new InterfazTareas(this, null);
        dialogo.setVisible(true);
        InterfazTareas.TareaSimple nueva = dialogo.getResultado();
        if (nueva != null) {
            tareas.add(nueva);
            refrescar();
        }
    }

    private void editarTarea() {
        InterfazTareas.TareaSimple t = tareaSeleccionada();
        if (t == null) return;
        InterfazTareas dialogo = new InterfazTareas(this, t);
        dialogo.setVisible(true);
        refrescar();
    }

    private void eliminarTarea() {
        InterfazTareas.TareaSimple t = tareaSeleccionada();
        if (t == null) return;
        int opcion = JOptionPane.showConfirmDialog(this,
                "Eliminar la tarea \"" + t.getTitulo() + "\"?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
            tareas.remove(t);
            refrescar();
        }
    }

    private void moverTarea(int direccion) {
        InterfazTareas.TareaSimple t = tareaSeleccionada();
        if (t == null) return;
        int destino = indiceEstado(t.getEstado()) + direccion;
        if (destino < 0 || destino >= InterfazTareas.ESTADOS.length) {
            JOptionPane.showMessageDialog(this, "La tarea no se puede mover mas en esa direccion",
                    "Aviso", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        t.setEstado(InterfazTareas.ESTADOS[destino]);
        refrescar();
        listas[destino].setSelectedValue(t, true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new InterfazTablero().setVisible(true);
        });
    }
}
