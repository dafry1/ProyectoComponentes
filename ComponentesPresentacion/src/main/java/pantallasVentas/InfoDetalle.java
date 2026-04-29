package pantallasVentas;

import DTOS.DTO;
import DTOS.DetallesVentaDTO;
import DTOS.PiezaDTO;
import coordinadores.CoordinadorEstados;
import coordinadores.ICoordinadorEstados;
import coordinadores.ICoordinadorNegocio;
import java.awt.Component;
import java.awt.Font;
import javax.swing.*;
import observadores.IObservador;
import utilEstilos.UtilFormato;
import utilEstilos.UtilSwing;
import utilPresentacion.UtilBoton;
import utilPresentacion.UtilGeneral;

/**
 * Diálogo para editar la cantidad de una pieza ya presente en el carrito
 * @author Andre
 */
public class InfoDetalle extends JDialog {

    private IObservador observador;

    public InfoDetalle(ICoordinadorNegocio coordinadorNegocio, ICoordinadorEstados coordinadorEstados, IObservador observador, DetallesVentaDTO detalle) {
        // Configuración inicial
        this.observador = observador;   
        this.setModal(true); 
        this.setResizable(false);
        this.setTitle("Editar Detalle");
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        
        //Extracción de datos
        PiezaDTO pieza = detalle.getPieza();
        String nombrePieza = pieza.getNombre();
        int cantidadActual = detalle.getCantidad();
        
        //Consulta la cantidad total que hay. Como es solo para actualizar la cantidad, se considera el stock real de la BD
        int stockDisponible = coordinadorNegocio.consultarPieza(pieza.getId()).getStockPieza();
        
        // Panel Principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));
        
        // Información de la pieza
        String[] info = {
            "Producto: " + nombrePieza,
            "Precio Unitario: $" + detalle.getCosto(),
            "Stock en almacén: " + stockDisponible ,
            "Cantidad actual: " + cantidadActual
        };

        for (String texto : info) {
            JLabel lbl = UtilGeneral.crearLabel(texto);
            lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelPrincipal.add(lbl);
            // Uso de ruta completa para evitar error de import
            panelPrincipal.add(Box.createRigidArea(new java.awt.Dimension(0, 8)));
        }

        panelPrincipal.add(Box.createRigidArea(new java.awt.Dimension(0, 15)));

        // Sección de edición
        JLabel labelInstruccion = UtilGeneral.crearLabel("Ingrese la nueva cantidad:");
        labelInstruccion.setFont(labelInstruccion.getFont().deriveFont(Font.BOLD, 13f));
        labelInstruccion.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(labelInstruccion);
        
        JTextField campoCantidad = UtilGeneral.crearCampoTexto();
        campoCantidad.setText(String.valueOf(cantidadActual));
        // Ajuste de tamaño con ruta completa
        campoCantidad.setMaximumSize(new java.awt.Dimension(120, 35));
        panelPrincipal.add(campoCantidad);

        panelPrincipal.add(Box.createRigidArea(new java.awt.Dimension(0, 20)));

        // Botón de actualización
        JButton botonActualizar = UtilBoton.crearBoton("Actualizar");
        botonActualizar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonActualizar.addActionListener(e -> {
            
            String input = campoCantidad.getText().trim();
            
            if (!UtilFormato.numeroEnteroPositivo(input)) {
                UtilSwing.dialogoAlerta(this, "Por favor, ingrese un número entero válido.");
                return;
            }
            
            int nuevaCantidad = Integer.parseInt(input);
            
            if (nuevaCantidad > stockDisponible) {
                UtilSwing.dialogoAlerta(this, "Error: Solo hay " + stockDisponible + " unidades disponibles.");
                return;
            }

            UtilSwing.dialogoConfirmacion(this, "¿Actualizar cantidad?", () -> {
                if (nuevaCantidad == 0) {
                    CoordinadorEstados.singleton().getCarritoVenta().remove(detalle);
                    UtilSwing.dialogoAviso(this, "Pieza eliminada del carrito.");
                } else {
                    detalle.setCantidad(nuevaCantidad);
                    UtilSwing.dialogoAviso(this, "Cantidad actualizada.");
                }
                
                if (observador != null) {
                    observador.observar();
                }
                this.dispose();
            });
        });
        
        panelPrincipal.add(botonActualizar);

        // Finalización
        this.add(panelPrincipal);
        this.pack();
        this.setLocationRelativeTo(null);
    }
}