package pantallasVentas;

import DTOS.ClienteDTO;
import DTOS.DetallesVentaDTO;
import DTOS.EmpleadoDTO;
import DTOS.VentaDTO;
import coordinadores.ICoordinadorEstados;
import coordinadores.ICoordinadorNegocio;
import coordinadores.ICoordinadorPresentacion;
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
import excepciones.PresentacionException;
import java.awt.Dimension;
import java.util.List;
import utilEstilos.UtilFormato;
import utilEstilos.UtilSwing;

public class InfoCliente extends JDialog {

    private JPanel panelPrincipal;
    private ICoordinadorEstados coordinadorEstados;
    private IEnsambladorDTO ensambladorDTO;
    private IObservador observador;
    private ICoordinadorPresentacion coordinadorPresentacion;
    private ICoordinadorNegocio coordinadorNegocio;

    private Map<String, JTextField> camposCliente = new HashMap<>();

    public InfoCliente(ICoordinadorPresentacion coordinadorPresentacion, ICoordinadorNegocio coordinadorNegocio, ICoordinadorEstados coordinadorEstados, IObservador observador, IEnsambladorDTO ensambladorDTO) {
        this.coordinadorEstados = coordinadorEstados;
        this.observador = observador;
        this.ensambladorDTO = ensambladorDTO;
        this.coordinadorNegocio = coordinadorNegocio;
        this.coordinadorPresentacion = coordinadorPresentacion;

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
            
            //Inicia el proceso dde hacer la venta
            UtilSwing.dialogoConfirmacion(this, "¿Desea registrar la venta?", () -> {
                
                //Crea al cliente con builder
                ClienteDTO cliente = new ClienteDTO.Builder()
                                            .nombres(nombre)
                                            .apellidoPaterno(paterno)
                                            .apellidoMaterno(materno)
                                            .correo(correo)
                                            .telefono(telefono)
                                            .build();

                //Activa el observador en caso de que exista
                if (observador != null) {
                    observador.observar();
                }
                
                //Procesa la venta
                procesarVentaConfirmada(cliente);
                
                //Notifica sobre la venta exitosa
                UtilSwing.dialogoAviso(this, "Venta procesada con éxito para el cliente: " + cliente.getNombres());

                //Regresa a la pantalla principal
                coordinadorPresentacion.mostrarVentanaInicio();
                this.dispose();
            });
        });

        panelPrincipal.add(Box.createVerticalGlue());
        panelPrincipal.add(botonRegistrar);

        botonRegistrar.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    
    /**
     * Arma la venta y la manda a procesar
     * con todo lo adyacente a eso
     * 
     * @param cliente 
     */
    private void procesarVentaConfirmada(ClienteDTO cliente) {
        //Obtiene los datos y crea la venta
        EmpleadoDTO empleado = coordinadorEstados.getUsuarioLogueado();
        List<DetallesVentaDTO> carrito = coordinadorEstados.getCarritoVenta();
        VentaDTO venta = new VentaDTO(cliente, empleado, carrito);

        //Procesa la venta
        try {
            coordinadorNegocio.procesarVenta(venta, observador);
        } catch (PresentacionException pe) {
            UtilSwing.dialogoAlerta(this, "Error al procesar: " + pe.getMessage());
        }
    }
    
    /** Crea espacio gráfico */
    private void espacio(int alto) {
        panelPrincipal.add(Box.createRigidArea(new java.awt.Dimension(0, alto)));
    }
}