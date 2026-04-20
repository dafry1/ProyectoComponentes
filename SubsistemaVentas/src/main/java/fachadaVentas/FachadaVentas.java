package fachadaVentas;

import DTOS.PiezaDTO;
import bo.PiezaBO;
import interfaces.IFachadaVentas;
import interfaces.IPiezaBO;
import java.util.List;

/**
 * Unifica los BOs con sus métodos en una sola clase para
 * centralizar toda la lógica necesaria para registrar y
 * manejar ventas
 * 
 * @author Andre
 */
public class FachadaVentas implements IFachadaVentas {
    
    //Interfaces
    private IPiezaBO piezaBO = new PiezaBO();
    //private IVenta ventaBO = new VentaBO();
    
    /**
     * Consulta las piezas de su BO respectivo
     * 
     * @return lista de tipo PiezaDTO
     */
    @Override
    public List<PiezaDTO> consultarPiezas() {
        return piezaBO.consultarPiezas();
    }
}