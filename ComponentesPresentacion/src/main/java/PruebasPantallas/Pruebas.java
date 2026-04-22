package PruebasPantallas;
import coordinadores.CoordinadorEstados;
import coordinadores.CoordinadorNegocio;
import coordinadores.CoordinadorPresentacion;
import coordinadores.ICoordinadorEstados;
import coordinadores.ICoordinadorNegocio;
import coordinadores.ICoordinadorPresentacion;
/**
 *
 * @author Andre
 */
public class Pruebas {

    public static void main(String[] args) {
        
   
        ICoordinadorEstados coordiandorEstados = CoordinadorEstados.singleton();
        ICoordinadorNegocio coordinadorNegocio = new CoordinadorNegocio();
        ICoordinadorPresentacion coordinadorPresentacion = new CoordinadorPresentacion(coordinadorNegocio, coordiandorEstados);
        coordinadorPresentacion.mostrarVentanaInicioSesion();
    }
}