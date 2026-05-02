package utilEstilos;

import coordinadores.ICoordinadorPresentacion;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Utilerías que implementa el patrón Builder: una clase con métodos que
 * construyen partes del programa más que simple moldes. Es como una
 * linea de ensamblaje con un plano que solo debe replicar. A diferencia
 * UtilPanel, que te dice "ten esta plantilla para tus necesidades", esta 
 * te dice "ten esta infraestructura específica del programa lista"
 * @author Andre
 */
public class UtilBuild {
    
    /**
     * Crea el panel superior de navegación con lógica incluida
     * 
     * @param frame donde es desplegado
     * @param coordinador que se encarga de navegar
     * 
     * @return el panel superior totalmente navegable
     */
    public static JPanel crearNavegacion(JFrame frame, ICoordinadorPresentacion coordinador) {
        
        //Creación y configuración
        JPanel nav = new JPanel(new GridLayout(1, 5));
        nav.setPreferredSize(new Dimension(0, 65));
        nav.setBackground(new Color(0, 95, 255));
        
        //Crea un botón por cada apartado anterior y lo configura
        for (String texto: Constantes.CAMPOS_PANEL_SUPERIOR ) {
            JButton btn = new JButton(texto);
            btn.setForeground(Constantes.COLOR_TEXTO_BOTONES);
            btn.setBackground(Constantes.COLOR_BOTONES);
            btn.setFont(Constantes.FUENTE);
            btn.setFocusPainted(false);
            btn.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.WHITE));
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

            //Dependiendo de qué campo sea presionado, creará una respectiva pantalla
            btn.addActionListener(e -> {
                switch (texto) {
                    case "Inicio" -> coordinador.mostrarVentanaInicio();
                    case "Iniciar venta" -> coordinador.mostrarVentanaVenta();
                    case "Iniciar solicitud" -> coordinador.mostrarVentanaSolicitud();
                    case "Historial de ventas" -> coordinador.mostrarHistorialVentas();
                    case "Historial de solicitudes" -> coordinador.mostrarHistorialSolicitudes();
                }
            });
            nav.add(btn);
        }
        return nav;
    }
}