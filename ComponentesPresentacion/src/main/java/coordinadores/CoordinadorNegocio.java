package coordinadores;

import DTOS.PiezaDTO;
import fachadaVentas.FachadaVentas;
import interfaces.IFachadaVentas;
import java.util.List;

/**
 * Coordinador encargado de recopilar todas las fachadas con lógica
 * de negocio en un solo lugar
 * 
 * @author Andre
 */
public class CoordinadorNegocio {
    //Instancia singleton
    private static CoordinadorNegocio instancia;
    
    //Instancia de la fachada del subsistema de las ventas
    private IFachadaVentas fachadaVentas = new FachadaVentas();
    
    /**
     * Crea la instancia única del coordiandor
     * 
     * @return la instancia única
     */
    public static CoordinadorNegocio singleton() {
        if (instancia == null) {
            instancia = new CoordinadorNegocio();
        }
        return instancia;
    }
    
    /**
     * Regresa todas las piezas del sistema, dadas directamente
     * por el IFachadaVentas
     * 
     * @return lista de PiezaDTO
     */
    public List<PiezaDTO> consultarPiezas() {
        return fachadaVentas.consultarPiezas();
    }
}
