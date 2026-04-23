package pantallasVentas;

import DTOS.DTO;
import DTOS.DetallesVentaDTO;
import DTOS.PiezaDTO;
import coordinadores.CoordinadorEstados;
import coordinadores.ICoordinadorEstados;
import ensambladores.EnsambladorDTO;
import ensambladores.IEnsambladorDTO;
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
public class InfoPieza extends JDialog {
    
    //Atributos
    String nombre;
    String categoria;
    String marca;
    String modelo;
    double costo;
    
    public InfoPieza(ICoordinadorEstados coordinadorEstados, IObservador observador, DTO dto, IEnsambladorDTO ensambladorDTO) {
        // Configuración inicial  
        this.setModal(false);
        this.setResizable(true);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        
        //Castea
        PiezaDTO pieza = (PiezaDTO) dto;
        
        //Extrae la información
        nombre = pieza.getNombre();
        categoria = pieza.getCategoria();
        marca = pieza.getMarcaPieza();
        modelo = pieza.getModeloPieza();
        costo = pieza.getCostoPieza();
        
        //Stock real, de la BD
        int stock = pieza.getStockPieza();
        
        //Stock que se está manejando actualmente
        int stockCarito = coordinadorEstados.calcularStockAntesVenta(pieza.getId());
        
        //Recalcula y usa este valor, considerando las piezas ya ingresadas al carrito
        int stockDisponible = stock - stockCarito;

        //Crea el panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(javax.swing.BorderFactory.createEmptyBorder(30, 50, 30, 50));
        panelPrincipal.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //Crea un arreglo de Strings con base en la información de la pieza
        String[] info = {
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
        JLabel labelStock = UtilGeneral.crearLabel("Stock disponible: " + stockDisponible);
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
            
            //Lanza mensaje si el número no es válido
            String cantidadString = campoCantidad.getText();
            if (!UtilFormato.numeroEnteroPositivo(cantidadString)) {
                UtilSwing.dialogoAlerta(InfoPieza.this, "Ingrese un número entero positivo");
                return;
            }
            
            //Crea un diálogo de confirmación
            UtilSwing.dialogoConfirmacion(boton, "¿Desea agregar esta pieza al carrito?", () -> {
                
                //Parsea solo si se confirma el agregado
                int cantidad = Integer.parseInt(cantidadString);
                
                if (stockDisponible == 0) {
                    UtilSwing.dialogoAlerta(InfoPieza.this, "No hay stock disponible para esta pieza. Se recomienda hacer una solicitud");
                    return;
                }
                
                //Valida stock suficiente
                if (cantidad > stockDisponible) {
                    UtilSwing.dialogoAlerta(InfoPieza.this, cantidad + " excede el stock actual (" + stockDisponible + ")");
                    return;
                }

                //Crea el detalle
                DetallesVentaDTO detalle = ensambladorDTO.ensamblarDetalleVentaDTO(cantidad, pieza);
                
                //Agrega el detalle al carrito y notifica al observador
                coordinadorEstados.agregarCarritoVenta(detalle);
                observador.observar();
                UtilSwing.dialogoAviso(this, "Se agregaron " + cantidad + " de la pieza " + nombre);
                this.dispose();
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