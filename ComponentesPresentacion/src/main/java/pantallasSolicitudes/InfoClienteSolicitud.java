package pantallasSolicitudes;

import DTOS.ClienteDTO;
import DTOS.DetallesVentaDTO;
import DTOS.DireccionDTO;
import DTOS.EmpleadoDTO;
import DTOS.SolicitudDTO;
import coordinadores.ICoordinadorEstados;
import coordinadores.ICoordinadorNegocio;
import coordinadores.ICoordinadorPresentacion;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import observadores.IObservador;
import utilEstilos.UtilSwing;
import utilPresentacion.UtilBoton;
import utilPresentacion.UtilGeneral;

/**
 * Pantalla para capturar datos del cliente y su dirección para una Solicitud.
 */
public class InfoClienteSolicitud extends JDialog {

    private JPanel panelPrincipal;
    private final ICoordinadorEstados coordinadorEstados;
    private final ICoordinadorPresentacion coordinadorPresentacion;
    private final ICoordinadorNegocio coordinadorNegocio;
    private final IObservador observador;
    
    private final Map<String, JTextField> campos = new HashMap<>();

    public InfoClienteSolicitud(ICoordinadorPresentacion cp, ICoordinadorNegocio cn, ICoordinadorEstados ce, IObservador obs) {
        this.coordinadorEstados = ce;
        this.coordinadorNegocio = cn;
        this.coordinadorPresentacion = cp;
        this.observador = obs;

        UtilSwing.configurarDialogoInicio(this, "Datos de Solicitud y Entrega");

        crearPanelPrincipal();
        crearInterfaz();

        // Scroll por si la resolución es baja, para no perder campos
        JScrollPane scroll = new JScrollPane(panelPrincipal);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        this.add(scroll);

        UtilSwing.configurarDialogoFinal(this);
    }

    private void crearPanelPrincipal() {
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBackground(Color.WHITE);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));
    }

    private void crearInterfaz() {
        // --- SECCIÓN CLIENTE ---
        agregarTitulo("Información del Cliente");
        agregarCampo("Nombre(s)", "Ej. Juan");
        agregarCampo("Apellido Paterno", "Ej. Pérez");
        agregarCampo("Teléfono", "10 dígitos");

        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));
        panelPrincipal.add(new JSeparator());
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 15)));

        // --- SECCIÓN DIRECCIÓN ---
        agregarTitulo("Dirección de Entrega");
        agregarCampo("Calle", "Nombre de la vialidad");
        agregarCampo("Número", "Ext/Int");
        agregarCampo("Colonia", "Nombre del asentamiento");
        agregarCampo("Código Postal", "5 dígitos");

        espacio(25);

        // --- BOTÓN DE ACCIÓN ---
        JButton btnConfirmar = UtilBoton.crearBoton("Finalizar Solicitud");
        btnConfirmar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnConfirmar.setMaximumSize(new Dimension(300, 45));
        
        btnConfirmar.addActionListener(e -> validarYProcesar());
        panelPrincipal.add(btnConfirmar);
    }

    private void agregarTitulo(String texto) {
        JLabel t = new JLabel(texto);
        t.setFont(new Font("Segoe UI", Font.BOLD, 16));
        t.setForeground(new Color(44, 62, 80));
        t.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(t);
        espacio(15);
    }

    private void agregarCampo(String nombre, String hint) {
        JLabel lbl = new JLabel(nombre + ":");
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        
        JTextField txt = UtilGeneral.crearCampoTexto();
        txt.setToolTipText(hint);
        txt.setMaximumSize(new Dimension(350, 35));
        txt.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        campos.put(nombre, txt);
        
        panelPrincipal.add(lbl);
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, 5)));
        panelPrincipal.add(txt);
        espacio(10);
    }

    private void validarYProcesar() {
        // Validación rápida de campos vacíos
        for (String key : campos.keySet()) {
            if (campos.get(key).getText().trim().isEmpty()) {
                UtilSwing.dialogoAlerta(this, "Por favor completa el campo: " + key);
                return;
            }
        }

        UtilSwing.dialogoConfirmacion(this, "¿Deseas generar esta solicitud de pedido?", () -> {
            ejecutarLogicaNegocio();
        });
    }

    private void ejecutarLogicaNegocio() {
        try {
            // Instanciar DTOs con la data capturada
            ClienteDTO cliente = new ClienteDTO.Builder()
                    .nombres(campos.get("Nombre(s)").getText().trim())
                    .apellidoPaterno(campos.get("Apellido Paterno").getText().trim())
                    .telefono(campos.get("Teléfono").getText().trim())
                    .build();

            DireccionDTO direccion = new DireccionDTO(
                    campos.get("Calle").getText().trim(),
                    campos.get("Colonia").getText().trim(),
                    campos.get("Número").getText().trim(),
                    campos.get("Código Postal").getText().trim()
            );

            List<DetallesVentaDTO> carrito = coordinadorEstados.getCarritoVenta();
            EmpleadoDTO empleado = coordinadorEstados.getUsuarioLogueado();

            // Construir Solicitud
            SolicitudDTO solicitud = new SolicitudDTO();
            solicitud.setCliente(cliente);
            solicitud.setDireccion(direccion);
            solicitud.setEmpleado(empleado);
            solicitud.setDetalles(carrito);
            solicitud.setEstado("PENDIENTE");

            // Llamada al coordinador
            coordinadorNegocio.procesarSolicitud(solicitud, observador);

            UtilSwing.dialogoAviso(this, "Solicitud registrada correctamente.");
            coordinadorPresentacion.mostrarVentanaInicio();
            this.dispose();

        } catch (Exception ex) {
            UtilSwing.dialogoAlerta(this, "Error al procesar: " + ex.getMessage());
        }
    }

    private void espacio(int alto) {
        panelPrincipal.add(Box.createRigidArea(new Dimension(0, alto)));
    }
}