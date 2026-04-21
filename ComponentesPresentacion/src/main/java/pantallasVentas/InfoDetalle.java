package pantallasVentas;

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
import utilEstilos.UtilFormato;
import utilEstilos.UtilSwing;
import utilPresentacion.UtilBoton;
import utilPresentacion.UtilGeneral;

/**
 * Diálogo que muestra la información de una pieza y gestiona
 * su adición al carrito
 * 
 * @author Andre
 */
public class InfoDetalle extends JDialog {

    //Observa la pieza elegida
    private IObservador observador;

    public InfoDetalle(IObservador observador, DTO dto) {
        // Configuración inicial
        this.observador = observador;   
        this.setModal(false);
        this.setResizable(true);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        
        //Castea
        DetallesVentaDTO detalle = (DetallesVentaDTO) dto;
        
        //Extrae la información
        Long id = detalle.getId();
        int cantidad = detalle.getCantidad();
        double subtotal = detalle.getSubtotal();
        PiezaDTO pieza = detalle.getPieza();
        String nombrePieza = pieza.getNombre();
        int stockPieza = pieza.getStockPieza();
        
        //Crea el panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(javax.swing.BorderFactory.createEmptyBorder(30, 50, 30, 50));
        panelPrincipal.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //Crea un arreglo de Strings con base en la información de la pieza
        String[] info = {
            "ID del detalle: " + id,
            "Nombre: " + nombrePieza,
            "Cantidad: " + cantidad,
            "Subtotal: " + subtotal,
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
        JLabel labelStock = UtilGeneral.crearLabel("Cantidad de piezas: " + cantidad);
        labelStock.setFont(labelStock.getFont().deriveFont(java.awt.Font.BOLD, 14f));
        labelStock.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(labelStock);
        
        //Crea un campo de texto para editar la cantidad
        JTextField campoCantidad = UtilGeneral.crearCampoTexto();
        panelPrincipal.add(campoCantidad);

        //Pequeño espacio
        panelPrincipal.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 25)));

        //Boton que selecciona la pieza
        JButton boton = UtilBoton.crearBoton("Seleccionar");
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.addActionListener(e -> {
            
            //Lanza mensaje si el número no es válido
            String cantidadString = campoCantidad.getText();
            if (!UtilFormato.numeroEnteroPositivo(cantidadString)) {
                UtilSwing.dialogoAlerta(InfoDetalle.this, "Ingrese un número entero positivo");
                return;
            }
            
            //Crea un diálogo de confirmación
            UtilSwing.dialogoConfirmacion(boton, "¿Desea agregar esta pieza al carrito?", () -> {
                
                //Parsea solo si se confirma el agregado
                int cantidadActualizar = Integer.parseInt(cantidadString);
                
                //FIXME: QUIZÁ ESTO PUEDE HACER QUE BORRE TODO EL DETALLE
                if (cantidadActualizar == 0) {
                    UtilSwing.dialogoAlerta(InfoDetalle.this, "No hay stock disponible para esta pieza. Se recomienda hacer una solicitud");
                    return;
                }
                
                //Valida stock suficiente
                if (cantidadActualizar > stockPieza) {
                    UtilSwing.dialogoAlerta(InfoDetalle.this, cantidad + " excede el stock actual (" + stockPieza + ")");
                    return;
                }
                
                //Actualiza el detalle y notifica al observador
                detalle.setCantidad(cantidadActualizar);
                observador.observar();
                UtilSwing.dialogoAviso(this, "Se actualizaron " + cantidadActualizar + " de la pieza " + nombrePieza);
                this.dispose();
                
                //TODO: AQUÍ TAMBIÉN DEBE ACTUALIZAR LA LISTA SUPONGO
            });
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