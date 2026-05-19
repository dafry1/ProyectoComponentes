package pantallasFactura;

import DTOS.ContribuyenteDTO;
import coordinadores.ICoordinadorNegocio;
import coordinadores.ICoordinadorPresentacion;
import excepciones.PresentacionException;
import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import observadores.IObservador;
import utilEstilos.UtilSwing;
import utilEstilos.UtilBoton;
import utilEstilos.UtilGeneral;
import utilPresentacion.Util;

/**
 * Diálogo para buscar un contribuyente por RFC
 * 
 * @author Andre
 */
public class IngresarRFC extends JDialog {
    JPanel panelPrincipal;
    
    //Auxiliares
    private ICoordinadorNegocio coordinadorNegocio;
    private ICoordinadorPresentacion coordinadorPresentacion;
    IObservador observador;
    
    /**
     * Constructor que coordina diferentes métodos y establece atributos
     * 
     * @param coordinadorPresentacion
     * @param coordinadorNegocio 
     */
    public IngresarRFC(ICoordinadorPresentacion coordinadorPresentacion, ICoordinadorNegocio coordinadorNegocio, IObservador observador) {
        
        //Establece atributos
        this.coordinadorNegocio = coordinadorNegocio;
        this.coordinadorPresentacion = coordinadorPresentacion;
        this.observador = observador;
        
        //Configuración inicial  
        UtilSwing.configurarDialogoInicio(this, "Buscar contribuyente por RFC");
        this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
        
        //Ensambla el diálogo
        panelPrincipal();
  
        //Agregar todo al diálogo
        this.add(panelPrincipal);
        
        //Configuración final
        UtilSwing.configurarDialogoFinal(this);
    }
    
    /** Crea el panel principal */
    private void panelPrincipal() {
        
        //Configura el panel
        this.panelPrincipal = new JPanel();
        panelPrincipal.setLayout(new BoxLayout(panelPrincipal, BoxLayout.Y_AXIS));
        panelPrincipal.setBorder(javax.swing.BorderFactory.createEmptyBorder(30, 50, 30, 50));
        panelPrincipal.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        //Área de indicaciones
        JLabel lbl = Util.crearLabel("Buscar por RFC");
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelPrincipal.add(lbl);
        panelPrincipal.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 5)));
        
        //Crea un campo de texto para ingresar
        JTextField campoRFC = UtilGeneral.crearCampoTexto();
        panelPrincipal.add(campoRFC);

        //Pequeño espacio
        panelPrincipal.add(javax.swing.Box.createRigidArea(new java.awt.Dimension(0, 25)));

        //Boton que selecciona la pieza
        JButton boton = UtilBoton.crearBoton("Buscar");
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.addActionListener(e -> buscarContribuyente(campoRFC.getText()));
    }
    
    /**
     * Maneja la lógica para buscar un contribuyente
     * 
     * @param rfc del contribuyente
     */
    private void buscarContribuyente(String rfc) {
        
        
        
        //Valida que no esté vacío
        if (rfc == null || rfc.isBlank()) {
            Util.dialogoAlerta(IngresarRFC.this, "Ingrese un RFC");
            return;
        }

        //Intenta buscar con ese RFC
        ContribuyenteDTO contribuyente = null;
        try {
            contribuyente = coordinadorNegocio.obtenerContribuyente(rfc);
        } catch (PresentacionException e) {
            Util.dialogoError(IngresarRFC.this, e.getMessage());
            return;
        }

        //Avisa
        if (contribuyente != null) {
            Util.dialogoError(IngresarRFC.this, "Contribuyente corrupto");
            return;
        } else {
            Util.dialogoAviso(IngresarRFC.this, "Iniciando proceso de facturación..");
        }
        
        //Notifica al observador
        if (observador != null) observador.observar();
    }
}