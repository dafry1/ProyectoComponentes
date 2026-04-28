package pantallasVentas;

import DTOS.ClienteDTO;
import coordinadores.ICoordinadorEstados;
import java.awt.Component;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import observadores.IObservador;
import utilEstilos.Constantes;
import utilPresentacion.UtilBoton;
import utilPresentacion.UtilGeneral;
import ensambladores.IEnsambladorDTO;
import java.awt.Dimension;
import utilEstilos.UtilFormato;
import utilEstilos.UtilSwing;

public class InfoCliente extends JDialog {

    private JPanel panelPrincipal;
    private ICoordinadorEstados coordinadorEstados;
    private IEnsambladorDTO ensambladorDTO;
    private IObservador observador;

    private Map<String, JTextField> camposCliente = new HashMap<>();

    public InfoCliente(ICoordinadorEstados coordinadorEstados, IObservador observador, IEnsambladorDTO ensambladorDTO) {
        this.coordinadorEstados = coordinadorEstados;
        this.observador = observador;
        this.ensambladorDTO = ensambladorDTO;

        UtilSwing.configurarDialogoInicio(this, "Capturar datos del cliente");

        crearPanelPrincipal();
        crearTitulo();
        crearFormulario();
        crearAcciones();

        this.add(panelPrincipal);

        UtilSwing.configurarDialogoFinal(this);
    }

    private void crearPanelPrincipal() {
        this.panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
    }

    private void crearTitulo() {
        JLabel titulo = new JLabel("Información del Cliente");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(titulo);
        espacio(20);
    }

    private void crearFormulario() {
       String[] etiquetas = {
            Constantes.CLIENTE_NOMBRE,
            Constantes.CLIENTE_APELLIDOP,
            Constantes.CLIENTE_APELLIDOM,
            Constantes.CLIENTE_CORREO,
            Constantes.CLIENTE_TELEFONO
        };

        for (String texto : etiquetas) {
            JLabel lbl = UtilGeneral.crearLabel(texto + ":");
            lbl.setAlignmentX(Component.CENTER_ALIGNMENT); 
            panelPrincipal.add(lbl);
            
            panelPrincipal.add(Box.createRigidArea(new Dimension(0, 5)));

            JTextField campo = UtilGeneral.crearCampoTexto();
            campo.setAlignmentX(Component.CENTER_ALIGNMENT);
            campo.setMaximumSize(new Dimension(450, 40)); 
            panelPrincipal.add(campo);
            
            camposCliente.put(texto, campo);
            
            espacio(15);
        }
    }

    private void crearAcciones() {
        JPanel panelBoton = new JPanel();
        panelBoton.setLayout(new BoxLayout(panelBoton, BoxLayout.X_AXIS));
        panelBoton.setOpaque(false);
        panelBoton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton botonRegistrar = UtilBoton.crearBoton("Asignar a la Venta");

        botonRegistrar.setMaximumSize(new Dimension(300, 50));
        botonRegistrar.setPreferredSize(new Dimension(300, 50));

        botonRegistrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonRegistrar.addActionListener(e -> {

            //Validaciones validador = new Validaciones();

            String nombre = camposCliente.get(Constantes.CLIENTE_NOMBRE).getText().trim();
            String paterno = camposCliente.get(Constantes.CLIENTE_APELLIDOP).getText().trim();
            String materno = camposCliente.get(Constantes.CLIENTE_APELLIDOM).getText().trim();
            String correo = camposCliente.get(Constantes.CLIENTE_CORREO).getText().trim();
            String telefono = camposCliente.get(Constantes.CLIENTE_TELEFONO).getText().trim();

            
            if (!UtilFormato.validarNombre(nombre)) {
                UtilSwing.dialogoAlerta(this, "El nombre no es válido (solo letras y espacios).");
                return;
            }

            if (!UtilFormato.validarNombre(paterno)) {
                UtilSwing.dialogoAlerta(this, "El apellido paterno no es válido (solo letras, sin espacios).");
                return;
            }

            if (!UtilFormato.validarNombre(materno)) {
                UtilSwing.dialogoAlerta(this, "El apellido materno no es válido (solo letras, sin espacios).");
                return;
            }

            if (!UtilFormato.validarCorreo(correo)) {
                UtilSwing.dialogoAlerta(this, "El formato de correo electrónico no es válido.");
                return;
            }

            if (!UtilFormato.validarTelefono(telefono)) {
                UtilSwing.dialogoAlerta(this, "El teléfono debe tener exactamente 10 dígitos numéricos.");
                return;
            }
   

            UtilSwing.dialogoConfirmacion(this, "¿Desea asignar a " + nombre + " a esta venta?", () -> {
                ClienteDTO cliente = ensambladorDTO.ensamblarClienteDTO(
                        nombre, paterno, materno, correo, telefono
                );

                coordinadorEstados.setCliente(cliente);

                if (observador != null) {
                    observador.observar();
                }

                UtilSwing.dialogoAviso(this, "Cliente validado y asignado.");
                this.dispose();
            });
        });

        panelPrincipal.add(Box.createVerticalGlue());
        panelPrincipal.add(botonRegistrar);

        botonRegistrar.setAlignmentX(Component.CENTER_ALIGNMENT);
    }

    private void espacio(int alto) {
        panelPrincipal.add(Box.createRigidArea(new java.awt.Dimension(0, alto)));
    }
}
