package PruebasPantallas;
import coordinadores.CoordinadorEstados;
import coordinadores.CoordinadorNegocio;
import coordinadores.CoordinadorPresentacion;
import coordinadores.ICoordinadorEstados;
import coordinadores.ICoordinadorNegocio;
import coordinadores.ICoordinadorPresentacion;
import ensambladores.EnsambladorDTO;
import ensambladores.IEnsambladorDTO;
/**
 *
 * @author Andre
 */
public class Pruebas {

    public static void main(String[] args) {
        
   
        ICoordinadorEstados coordiandorEstados = CoordinadorEstados.singleton();
        ICoordinadorNegocio coordinadorNegocio = new CoordinadorNegocio();
        IEnsambladorDTO ensamladorDTO = new EnsambladorDTO();
        
        ICoordinadorPresentacion coordinadorPresentacion = new CoordinadorPresentacion(coordinadorNegocio, coordiandorEstados, ensamladorDTO);
        coordinadorPresentacion.mostrarVentanaInicioSesion();
    }
}