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
        
        //Instancia los coordinadores y el ensamblador
        ICoordinadorEstados coordinadorEstados = CoordinadorEstados.singleton();
        ICoordinadorNegocio coordinadorNegocio = new CoordinadorNegocio(coordinadorEstados);
        IEnsambladorDTO ensambladorDTO = new EnsambladorDTO();
        
        //Los asigna al coordinador de presentación, que lo suministra al resto de ventanas
        ICoordinadorPresentacion coordinadorPresentacion = new CoordinadorPresentacion(coordinadorNegocio, coordinadorEstados, ensambladorDTO);
        coordinadorPresentacion.mostrarVentanaInicioSesion();
    }
}