package PruebasPantallas;

import coordinadores.CoordinadorPresentacion;
import javax.swing.SwingUtilities;
import pantallasVentas.ViniciarSolicitud;
import pantallasVentas.ViniciarVenta;
import pantallasPrincipales.Vinicio;
import pantallasPrincipales.VinicioSesion;

/**
 *
 * @author Andre
 */
public class Pruebas {

    public static void main(String[] args) {
        CoordinadorPresentacion coordinador = new CoordinadorPresentacion();
        coordinador.mostrarVentanaInicioSesion();
        
    }
}