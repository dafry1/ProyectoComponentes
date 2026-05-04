/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallasSolicitudes;

import DTOS.DetallesVentaDTO;
import DTOS.PiezaDTO;
import coordinadores.ICoordinadorEstados;
import ensambladores.IEnsambladorDTO;
import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import observadores.IObservador;
import pantallasVentas.InfoPieza;
import utilEstilos.UtilFormato;
import utilEstilos.UtilSwing;
import utilPresentacion.UtilBoton;
import utilPresentacion.UtilGeneral;

/**
 *
 * @author Andre
 */
public class InfoPiezaBodega extends JDialog {
    JPanel panelPrincipal;
    
    //Sobre la pieza
    PiezaDTO pieza;
    String nombre;
    String categoria;
    String marca;
    String modelo;
    double costo;
    
    //Auxiliares
    ICoordinadorEstados coordinadorEstados;
    IEnsambladorDTO ensambladorDTO;
    IObservador observador;
    
    /**
     * Constructor que coordina diferentes métodos y establece atributos
     * 
     * @param coordinadorEstados
     * @param observador
     * @param pieza
     * @param ensambladorDTO 
     */
    public InfoPiezaBodega(ICoordinadorEstados coordinadorEstados, IObservador observador, PiezaDTO pieza, IEnsambladorDTO ensambladorDTO) {
        
        //Establece atributos
        this.pieza = pieza;
        this.coordinadorEstados = coordinadorEstados;
        this.observador = observador;
        this.ensambladorDTO = ensambladorDTO;
        
        //Configuración inicial  
        UtilSwing.configurarDialogoInicio(this, "Agregar pieza de la bodega");
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
                UtilSwing.dialogoAlerta(InfoPiezaBodega.this, "Ingrese un número entero positivo");
                return;
            }
            
            //Crea un diálogo de confirmación
            UtilSwing.dialogoConfirmacion(InfoPiezaBodega.this, "¿Desea agregar esta pieza al carrito?", () -> {
                agregarPieza(cantidadString);
            });
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
        
        //Crea el detalle
        DetallesVentaDTO detalle = ensambladorDTO.ensamblarDetalleVentaDTO(cantidad, pieza);

        //Agrega el detalle al carrito y notifica al observador
        coordinadorEstados.agregarCarritoSolicitud(detalle);
        UtilSwing.dialogoAviso(this, "Se agregaron " + cantidad + " de la pieza " + nombre);
        
        //Notifica al observador
        if (observador != null) {
            observador.observar();
        }
        this.dispose();
    }
    
    /** Da un pequeño espacio al panel*/
    private void espacio() {
        panelPrincipal.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 20)));
    }
}
