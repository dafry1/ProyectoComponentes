package pantallasSolicitudes;

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
public class InfoDetallesVentaSoli extends JDialog {

    private IObservador observador;
    ICoordinadorEstados coordinadorEstados;

    public InfoDetallesVentaSoli(ICoordinadorNegocio coordinadorNegocio, ICoordinadorEstados coordinadorEstados, IObservador observador, DetallesVentaDTO detalle) {
        this.coordinadorEstados = coordinadorEstados;
        
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
        
        // Panel Principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));
        
        // Información de la pieza
        String[] info = {
            "Producto: " + nombrePieza,
            "Precio Unitario: $" + detalle.getCosto(),
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
        JLabel labelInstruccion = UtilGeneral.crearLabel("Nueva cantidad: ");
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
            String cantidad = campoCantidad.getText().trim();
            actualizarCantidad(cantidad, detalle);
        });
        panelPrincipal.add(botonActualizar);
        
        //Botón que elimina el detalle
        JButton botonEliminar = UtilBoton.crearBoton("Eliminar");
        botonEliminar.setAlignmentX(Component.CENTER_ALIGNMENT);
        botonEliminar.addActionListener(e -> {
            UtilSwing.dialogoConfirmacion(this, "¿Desea eliminar este detalle?", () -> {
                coordinadorEstados.eliminarCarritoSolicitud(detalle);
                observador.observar();
                UtilSwing.dialogoAviso(this, "Pieza eliminada correctamente");
                InfoDetallesVentaSoli.this.dispose();
            });
        });
        panelPrincipal.add(botonEliminar);
        
        //Finalización
        this.add(panelPrincipal);
        this.pack();
        this.setLocationRelativeTo(null);
    }
    
    /**
     * Verifica la información para actualizar la cantidad
     * del detalle si todo está bien activa el proceso
     * 
     * @param cantidad
     * @param stockDisponible
     * @param detalle 
     */
    public void actualizarCantidad(String cantidad, DetallesVentaDTO detalle) {
  
        //Si no es número válido
        if (!UtilFormato.numeroEnteroPositivo(cantidad)) {
            UtilSwing.dialogoAlerta(this, "Por favor, ingrese un número entero válido.");
            return;
        }

        //Parsea
        int nuevaCantidad = Integer.parseInt(cantidad);
        
        //Si todo está bien inicia el proceso de verdad
        UtilSwing.dialogoConfirmacion(this, "¿Actualizar cantidad?", () -> {
            if (nuevaCantidad == 0) {
                coordinadorEstados.eliminarCarritoSolicitud(detalle);
                UtilSwing.dialogoAviso(this, "Pieza eliminada del carrito.");
            } else {
                detalle.setCantidad(nuevaCantidad);
                UtilSwing.dialogoAviso(this, "Cantidad actualizada.");
            }
        });
        
         //Notifica al observador
        if (observador != null) {
            observador.observar();
        }
    }
}