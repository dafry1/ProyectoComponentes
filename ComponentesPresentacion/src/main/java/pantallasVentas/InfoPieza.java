package pantallasVentas;

import DTOS.PiezaDTO;
import coordinadores.CoordinadorEstados;
import interfaces.IDTO;
import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
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

    public infoPieza(IObservador observador, IDTO dto) {
        //Configuración inicial
        this.observador = observador;
        UtilSwing.configurarDialogoInicio(this, true);
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        PiezaDTO pieza = (PiezaDTO) dto;

        //Crea el label de paneles
        JPanel panelLabels = UtilPanel.crearPanel();
        panelLabels.setLayout(new BoxLayout(panelLabels, BoxLayout.Y_AXIS));
        panelLabels.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 10, 20));

        //Crea los labels
        panelLabels.add(UtilGeneral.crearLabel("ID de la pieza: " + pieza.getId()));
        panelLabels.add(UtilGeneral.crearLabel("Nombre: " + pieza.getNombre()));
        panelLabels.add(UtilGeneral.crearLabel("Categoría: " + pieza.getCategoria()));
        panelLabels.add(UtilGeneral.crearLabel("Marca: " + pieza.getMarcaPieza()));
        panelLabels.add(UtilGeneral.crearLabel("Modelo: " + pieza.getModeloPieza()));

        //Crea el panel de botones
        JPanel panelBoton = UtilPanel.crearPanel();
        panelBoton.setLayout(new BoxLayout(panelBoton, BoxLayout.Y_AXIS));
        panelBoton.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 20, 20, 20));

        //Label de stock al panel
        JLabel labelStock = UtilGeneral.crearLabel("Stock disponible: " + pieza.getStockPieza());
        labelStock.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelBoton.add(labelStock);
        
        //Espacio
        panelBoton.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 15)));

        //Crea e inyecta lógica al botón
        JButton boton = UtilBoton.crearBoton("Seleccionar");
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.addActionListener(e -> {
            
            //Crea un diálogo de confirmación
            boolean confirmarAgregar = UtilSwing.dialogoConfirmacion(boton, "¿Desea agregar esta pieza al carrito?");
            
            //Si confirma, se manda al coordinador y se llama al observador
            if (confirmarAgregar) {
                CoordinadorEstados.singleton().agregarPiezaCarrito(pieza);
                observador.observar();
                UtilSwing.dialogoAviso(this, "Pieza agregada al carrito con éxito.");
                this.dispose();
            } 
            
            //Si se cancela, cierra el diálogo
            else {
                this.dispose();
            }
        });
        panelBoton.add(boton);

        //Agrega al diálogo
        this.add(panelLabels);
        this.add(panelBoton);

        //Configuración final
        UtilSwing.configurarDialogoFinal(this);
    }
}