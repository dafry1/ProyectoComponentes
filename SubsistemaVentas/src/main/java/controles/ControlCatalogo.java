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
     * Busca una piezas
     * 
     * @param id
     * @return 
     */
    public PiezaDTO consultarPieza(Long id) {
        return piezaBO.consultarPieza(id);
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
    
    /**
     * Consulta las piezas más vendidas en el día del sistema
     *
     * @return lista de PiezaDTO
     */
    public List<PiezaDTO> consultarTopDiaPiezas() {
        List<PiezaDTO> piezasTopDia = piezaBO.consultarTopDiaPiezas();
        LOG.log(System.Logger.Level.INFO, () -> EXITO_CONSULTA + piezasTopDia.size());
        return piezasTopDia;
    }
    
    /**
     * Consulta las piezas más vendidas en la semana del sistema
     *
     * @return lista de PiezaDTO
     */
    public List<PiezaDTO> consultarTopSemanaPiezas() {
        List<PiezaDTO> piezasTopSemana = piezaBO.consultarTopSemanaPiezas();
        LOG.log(System.Logger.Level.INFO, () -> EXITO_CONSULTA + piezasTopSemana.size());
        return piezasTopSemana;
    }
    
    /**
     * Consulta las piezas más vendidas en el mes del sistema
     *
     * @return lista de PiezaDTO
     */
    public List<PiezaDTO> consultarTopMesPiezas() {
        List<PiezaDTO> piezasTopMes = piezaBO.consultarTopMesPiezas();
        LOG.log(System.Logger.Level.INFO, () -> EXITO_CONSULTA + piezasTopMes.size());
        return piezasTopMes;
    }
    
    /**
     * Consulta las piezas más vendidas en todo el tiempo del sistema
     *
     * @return lista de PiezaDTO
     */
    public List<PiezaDTO> consultarTopTodoPiezas() {
        List<PiezaDTO> piezasTopTodo = piezaBO.consultarTopTodoPiezas();
        LOG.log(System.Logger.Level.INFO, () -> EXITO_CONSULTA + piezasTopTodo.size());
        return piezasTopTodo;
    }
}