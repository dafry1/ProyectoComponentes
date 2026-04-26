package pantallasVentas;

import DTOS.ClienteDTO;
import DTOS.DTO;
import DTOS.DetallesVentaDTO;
import DTOS.PiezaDTO;
import coordinadores.CoordinadorEstados;
import coordinadores.ICoordinadorEstados;
import ensambladores.IEnsambladorDTO;
import java.awt.Component;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import observadores.IObservador;
import utilEstilos.Constantes;
import utilEstilos.UtilFormato;
import utilEstilos.UtilSwing;
import utilPresentacion.UtilBoton;
import utilPresentacion.UtilGeneral;

/**
 * Diálogo que muestra un formulario para capturar los datos
 * del cliente para la venta
 * 
 * @author Andre
 */
public class InfoCliente extends JDialog {
    JPanel panelPrincipal;
    
    //Sobre la pieza
    PiezaDTO pieza;
    String nombre;
    String categoria;
    String marca;
    String modelo;
    double costo;
    
    //Stock según su naturaleza
    int stockDisponible;
    int stock;
    int stockCarrito;
    
    //Auxiliares
    ICoordinadorEstados coordinadorEstados;
    IEnsambladorDTO ensambladorDTO;
    IObservador observador;
    
    //Mapa donde se guardan los campos
    Map<String, JTextField> camposCliente = new HashMap<>();
    
    /**
     * Constructor que coordina diferentes métodos y establece atributos
     * 
     * @param coordinadorEstados
     * @param observador
     * @param pieza
     * @param ensambladorDTO 
     */
    public InfoCliente(ICoordinadorEstados coordinadorEstados, IObservador observador, PiezaDTO pieza, IEnsambladorDTO ensambladorDTO) {
        
        //Establece atributos
        this.pieza = pieza;
        this.coordinadorEstados = coordinadorEstados;
        this.observador = observador;
        this.ensambladorDTO = ensambladorDTO;
        
        //Configuración inicial  
        UtilSwing.configurarDialogoInicio(this, "Capturar datos del cliente");
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        
        //Ensambla el diálogo
        panelPrincipal();
        areaSuperior();
        espacio();
        areaStock();
        espacio();
        
        //Agregar todo al diálogo
        this.add(panelPrincipal);
        
        //Configuración final
        UtilSwing.configurarDialogoFinal(this);
    }
    
    
    
    /** Crea el panel principal */
    private void panelPrincipal() {
        this.panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(javax.swing.BorderFactory.createEmptyBorder(30, 50, 30, 50));
        panelPrincipal.setAlignmentX(Component.CENTER_ALIGNMENT);
    }
    
    
    
    /** Crea el área donde se despliega la información de la pieza */
    private void areaSuperior() {
        nombre = pieza.getNombre();
        categoria = pieza.getCategoria();
        marca = pieza.getMarcaPieza();
        modelo = pieza.getModeloPieza();
        costo = pieza.getCostoPieza();
        
        //Stock real, de la BD
        stock = pieza.getStockPieza();
        
        //Stock que se está manejando actualmente
        stockCarrito = coordinadorEstados.calcularStockAntesVenta(pieza.getId());
        
        //Recalcula y usa este valor, considerando las piezas ya ingresadas al carrito
        stockDisponible = stock - stockCarrito;
        
        //Crea un arreglo de Strings con base en la información de la pieza
        String[] info = {
            "Nombre: " + nombre,
            "Categoría: " + categoria,
            "Marca: " + marca,
            "Modelo: " + modelo,
            "Precio individual: " + costo 
        };

        //Crea un label por cada elemento del arreglo
        for (String texto: info) {
            JLabel lbl = UtilGeneral.crearLabel(texto);
            lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelPrincipal.add(lbl);
            panelPrincipal.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 5)));
        }
    }
    
    
    
    /** Crea el área del label de stock, elección de cantidad y botón de confirmar */
    private void areaStock() {
        //Label del stock local restante de la pieza
        JLabel labelStock = UtilGeneral.crearLabel("Stock disponible: " + stockDisponible);
        espacio();
        labelStock.setFont(labelStock.getFont().deriveFont(java.awt.Font.BOLD, 14f));
        labelStock.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(labelStock);
        
        //Arreglo con datos del cliente
        String[] labels = {
            Constantes.CLIENTE_NOMBRE,
            Constantes.CLIENTE_APELLIDOP,
            Constantes.CLIENTE_APELLIDOM,
            Constantes.CLIENTE_CORREO,
            Constantes.CLIENTE_TELEFONO
        };
        
        //Crea los campos de texto por cada campo para el cliente
        for (String texto: labels) {
            JTextField campo = UtilGeneral.crearCampoTexto();
            panelPrincipal.add(campo);
            camposCliente.put(texto, campo);
            
            //Pequeño espacio
            panelPrincipal.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 25)));
        }
        
        

        //Boton que selecciona la pieza
        JButton boton = UtilBoton.crearBoton("Registrar");
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.addActionListener(e -> {
            
            for (String texto: labels) {
                JTextField campo = camposCliente.get(texto);
                String infoCampo = campo.getText();
                if (infoCampo == null || infoCampo.isBlank()) {
                    UtilSwing.dialogoAlerta(InfoCliente.this, "Todos los campos son obligatorios");
                    return;
                }
            }
            
            //Implementación chafa temporal XDDDDD
            ClienteDTO cliente = 
                    ensambladorDTO
                            .ensamblarClienteDTO(
                                    camposCliente.get(Constantes.CLIENTE_NOMBRE).getText(),
                                    camposCliente.get(Constantes.CLIENTE_APELLIDOM).getText(),
                                    camposCliente.get(Constantes.CLIENTE_APELLIDOP).getText(),
                                    camposCliente.get(Constantes.CLIENTE_CORREO).getText(),
                                    camposCliente.get(Constantes.CLIENTE_TELEFONO).getText()
                            );
            

            
        });
        panelPrincipal.add(boton);
    }
    
    
    /**
     * Método que agrega una pieza al carrito. Es llamado por
     * el botón que confirma dicha decisión
     * 
     * @param cantidadString para trabajar
     */
    private void agregarPieza(String cantidadString) {
        
        //Parsea solo si se confirma el agregado
        int cantidad = Integer.parseInt(cantidadString);
        if (stockDisponible == 0) {
            UtilSwing.dialogoAlerta(this, "No hay stock disponible para esta pieza. Se recomienda hacer una solicitud");
            return;
        }

        //Valida stock suficiente
        if (cantidad > stockDisponible) {
            UtilSwing.dialogoAlerta(this, cantidad + " excede el stock actual (" + stockDisponible + ")");
            return;
        }

        //Crea el detalle
        DetallesVentaDTO detalle = ensambladorDTO.ensamblarDetalleVentaDTO(cantidad, pieza);

        //Agrega el detalle al carrito y notifica al observador
        coordinadorEstados.agregarCarritoVenta(detalle);
        observador.observar();
        UtilSwing.dialogoAviso(this, "Se agregaron " + cantidad + " de la pieza " + nombre);
        this.dispose();
    }
    
    
    
    /** Da un pequeño espacio al panel*/
    private void espacio() {
        panelPrincipal.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 20)));
    }
}