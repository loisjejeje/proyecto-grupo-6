import clases.GestorReportes;
import clases.Proyecto;
import clases.Tarea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class InterfazReportes extends JFrame {

    private final Proyecto proyecto;
    private final GestorReportes gestor = new GestorReportes();

    private final JLabel lblPendientes = new JLabel();
    private final JLabel lblEnProceso = new JLabel();
    private final JLabel lblTerminadas = new JLabel();
    private final JLabel lblTotal = new JLabel();
    private final JProgressBar barra = new JProgressBar(0, 100);
    private final JTextArea area = new JTextArea();

    public InterfazReportes(Proyecto proyecto) {
        this.proyecto = proyecto;

        setTitle("Reportes - " + proyecto.getNombre());
        setSize(560, 520);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel resumen = new JPanel(new GridLayout(4, 2, 10, 6));
        resumen.setBorder(BorderFactory.createTitledBorder("Tareas por estado"));
        resumen.add(new JLabel("Pendientes:"));
        resumen.add(lblPendientes);
        resumen.add(new JLabel("En proceso:"));
        resumen.add(lblEnProceso);
        resumen.add(new JLabel("Terminadas:"));
        resumen.add(lblTerminadas);
        resumen.add(new JLabel("Total:"));
        resumen.add(lblTotal);

        barra.setStringPainted(true);

        JPanel norte = new JPanel(new BorderLayout(5, 8));
        norte.add(resumen, BorderLayout.NORTH);
        norte.add(barra, BorderLayout.SOUTH);

        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(area);
        scroll.setBorder(BorderFactory.createTitledBorder("Reporte de avance y pendientes"));

        JButton btnActualizar = new JButton("Actualizar");
        JButton btnCerrar = new JButton("Cerrar");
        btnActualizar.addActionListener(e -> actualizar());
        btnCerrar.addActionListener(e -> dispose());

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botones.add(btnActualizar);
        botones.add(btnCerrar);

        panel.add(norte, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);
        add(panel);

        addWindowFocusListener(new WindowAdapter() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                actualizar();
            }
        });

        actualizar();
    }

    public void actualizar() {
        List<Tarea> tareas = proyecto.getTableroPrincipal().getTareas();
        int pendientes = 0, enProceso = 0, terminadas = 0;
        for (Tarea t : tareas) {
            switch (t.getEstado()) {
                case "Pendiente": pendientes++; break;
                case "En Proceso": enProceso++; break;
                case "Terminada": terminadas++; break;
                default: break;
            }
        }
        int total = tareas.size();

        lblPendientes.setText(String.valueOf(pendientes));
        lblEnProceso.setText(String.valueOf(enProceso));
        lblTerminadas.setText(String.valueOf(terminadas));
        lblTotal.setText(String.valueOf(total));

        int porcentaje = total > 0 ? (int) Math.round(terminadas * 100.0 / total) : 0;
        barra.setValue(porcentaje);
        barra.setString(porcentaje + " % terminado");

        StringBuilder texto = new StringBuilder(capturarReporte(total, terminadas));
        texto.append("\n=== TAREAS NO TERMINADAS ===\n");
        texto.append(listarPendientes(tareas));
        area.setText(texto.toString());
        area.setCaretPosition(0);
    }

    private String capturarReporte(int total, int terminadas) {
        PrintStream original = System.out;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(buffer, true, StandardCharsets.UTF_8));
            gestor.generarReporteAvance(proyecto.getNombre(), total, terminadas);
        } finally {
            System.setOut(original);
        }
        return buffer.toString(StandardCharsets.UTF_8);
    }

    private String listarPendientes(List<Tarea> tareas) {
        List<Tarea> noTerminadas = new ArrayList<>();
        for (Tarea t : tareas) {
            if (!"Terminada".equals(t.getEstado())) noTerminadas.add(t);
        }
        if (noTerminadas.isEmpty()) {
            return "No hay tareas pendientes.\n";
        }
        noTerminadas.sort(Comparator.comparing(
                (Tarea t) -> InterfazTareas.parsearFecha(t.getFechaVencimiento()),
                Comparator.nullsLast(Comparator.naturalOrder())));

        LocalDate hoy = LocalDate.now();
        StringBuilder sb = new StringBuilder();
        for (Tarea t : noTerminadas) {
            sb.append("- #").append(t.getIdTarea()).append(" ").append(t.getTitulo())
              .append(" | ").append(t.getEstado())
              .append(" | vence ").append(t.getFechaVencimiento());
            LocalDate vence =
