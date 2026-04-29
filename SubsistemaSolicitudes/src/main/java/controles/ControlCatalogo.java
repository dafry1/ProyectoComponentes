package controles;

import DTOS.PiezaDTO;
import interfaces.IFabricaBO;
import interfaces.IPiezaBO;
import java.util.List;

/**
 * Control dedicado a acceder a las piezas disponibles LOCALMENTE, sin
 * meterse a la hipotética bodega de la API
 * 
 * @author Andre
 */
public class ControlCatalogo{
    private static final System.Logger LOG = System.getLogger(ControlCatalogo.class.getName());
    private static final String EXITO_CONSULTA = ">> Piezas consultadas con éxito ";
    
    //Interfaces
    private IPiezaBO piezaBO;
    
    /**
     * Constructor con una fábrica inyectada
     * 
     * @param piezaBO 
     */
    public ControlCatalogo(IPiezaBO piezaBO) {
        this.piezaBO = piezaBO;
    }
    
    /**
     * Consulta las piezas de su BO respectivo
     * 
     * @return lista de tipo PiezaDTO
     */
    public List<PiezaDTO> consultarPiezas() {
        List<PiezaDTO> piezas = piezaBO.consultarPiezas();
        LOG.log(System.Logger.Level.INFO, () -> EXITO_CONSULTA + piezas.size());
        return piezas;
    }
}