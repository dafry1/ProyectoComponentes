package pantallasVentas;

//SUBETE AL GITHUB
import DTOS.DTO;
import DTOS.DetallesVentaDTO;
import DTOS.PiezaDTO;
import coordinadores.CoordinadorEstados;
import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import observadores.IObservador;
import utilEstilos.UtilSwing;
import utilPresentacion.UtilBoton;
import utilPresentacion.UtilGeneral;
import utilPresentacion.UtilPanel;

/**
 * Diálogo que muestra la información de una pieza y gestiona
 * su adición al carrito
 * @author Andre
 */
public class infoPieza extends JDialog {

    //Observa la pieza elegida
    private IObservador observador;

    public infoPieza(IObservador observador, DTO dto) {
        // Configuración inicial
        this.observador = observador;   
        this.setModal(false);
        this.setResizable(true);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        
        //Castea
        PiezaDTO pieza = (PiezaDTO) dto;
        
        //Extrae la información
        Long id = pieza.getId();
        String nombre = pieza.getNombre();
        String categoria = pieza.getCategoria();
        String marca = pieza.getMarcaPieza();
        String modelo = pieza.getModeloPieza();
        double costo = pieza.getCostoPieza();

        //Crea el panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(javax.swing.BorderFactory.createEmptyBorder(30, 50, 30, 50));
        panelPrincipal.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //Crea un arreglo de Strings con base en la información de la pieza
        String[] info = {
            "ID de la pieza: " + id,
            "Nombre: " + nombre,
            "Categoría: " + categoria,
            "Marca: " + marca,
            "Modelo: " + modelo
        };

        //Crea un label por cada elemento del arreglo
        for (String texto: info) {
            JLabel lbl = UtilGeneral.crearLabel(texto);
            lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelPrincipal.add(lbl);
            panelPrincipal.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 5)));
        }

        //Pequeño espacio
        panelPrincipal.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 20)));

        //Label del stock local restante de la pieza
        JLabel labelStock = UtilGeneral.crearLabel("Stock disponible: " + pieza.getStockPieza());
        labelStock.setFont(labelStock.getFont().deriveFont(java.awt.Font.BOLD, 14f));
        labelStock.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(labelStock);
        
        //Crea un campo de texto para ingresar
        JTextField campoCantidad = UtilGeneral.crearCampoTexto();
        panelPrincipal.add(campoCantidad);

        //Pequeño espacio
        panelPrincipal.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 25)));

        //Boton que selecciona la pieza
        JButton boton = UtilBoton.crearBoton("Seleccionar");
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.addActionListener(e -> {
            
            //Confirma con el regex que sean únicamente números del 1 al 9
            String cantidadString = campoCantidad.getText();
            if (!cantidadString.matches("[1-9]\\d*")) {
                UtilSwing.dialogoAlerta(infoPieza.this, "Ingrese un número entero positivo");
                return;
            }
            
            //Crea un diálogo de confirmación
            boolean confirmarAgregar = UtilSwing.dialogoConfirmacion(boton, "¿Desea agregar esta pieza al carrito?");
            if (confirmarAgregar) {
                
                //Convierte a entero solo si se confirma intención
                int cantidad = Integer.parseInt(cantidadString);
                
                //Crea el detalle
                DetallesVentaDTO detalle = new DetallesVentaDTO();
                detalle.setPieza(pieza);
                detalle.setCantidad(cantidad);
                detalle.setCosto(costo);
                detalle.setSubtotal(cantidad*costo);
                
                //Agrega el detalle al carrito y notifica al observador
                CoordinadorEstados.singleton().agregarCarritoVenta(detalle);
                observador.observar();
                UtilSwing.dialogoAviso(this, "Se agregaron " + cantidad + " de la pieza " + nombre);
                this.dispose();
            }
        });
        panelPrincipal.add(boton);

        //Agregar todo al diálogo
        this.add(panelPrincipal);
        
        //Configuración final
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}