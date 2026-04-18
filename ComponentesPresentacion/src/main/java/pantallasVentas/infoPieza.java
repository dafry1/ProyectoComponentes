package pantallasVentas;
import interfaces.IDTO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import utilEstilos.UtilSwing;
import utilPresentacion.UtilBoton;
import utilPresentacion.UtilGeneral;
import utilPresentacion.UtilPanel;

/**
 *
 * @author Andre
 */
public class infoPieza extends JDialog {
    
    public infoPieza(IDTO objeto) {
        
        UtilSwing.configurarDialogoInicio(this, true);
        
        //Crea el panel
        JPanel panel = UtilPanel.crearPanel();
    
        //Crea el label
        String info = objeto.mostrarInfo();
        JLabel label = UtilGeneral.crearLabel(info);
        panel.add(label);
        
        //Crea un botón
        JButton boton = UtilBoton.crearBoton("Prueba");
        boton.addActionListener(e -> {
            System.out.println("Me picaste xd");
        });
        panel.add(boton);
        
        //Agrega el diálogo
        add(panel);
        
        UtilSwing.configurarDialogoFinal(this);
    }
}