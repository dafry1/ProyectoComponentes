package PruebasPantallas;

import javax.swing.SwingUtilities;
import pantallas.VadministrarClientes;
import pantallas.ViniciarSolicitud;
import pantallas.ViniciarVenta;
import pantallas.Vinicio;
import pantallas.VinicioSesion;

/**
 *
 * @author Andre
 */
public class Pruebas {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ViniciarSolicitud().setVisible(true));
    }
}