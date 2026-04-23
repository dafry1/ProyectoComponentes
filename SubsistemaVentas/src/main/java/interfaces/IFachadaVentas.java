package interfaces;

import DTOS.ClienteDTO;
import DTOS.DetallesVentaDTO;
import DTOS.PiezaDTO;
import DTOS.VentaDTO;
import java.util.List;

/**
 * Contrato para la fachada que unifica los métodos
 * del subsistema
 * 
 * @author Andre
 */
public interface IFachadaVentas {
    
    /**
     * Consulta todas las piezas del sistema
     * 
     * @return lista de PiezaDTO
     */
    List<PiezaDTO> consultarPiezas();
    
    /**
     * Consulta las piezas más vendidas en el día del sistema
     *
     * @return lista de PiezaDTO
     */
    List<PiezaDTO> consultarTopDiaPiezas();

    /**
     * Consulta las piezas más vendidas en la semana del sistema
     *
     * @return lista de PiezaDTO
     */
    List<PiezaDTO> consultarTopSemanaPiezas();

    /**
     * Consulta las piezas más vendidas en el mes del sistema
     *
     * @return lista de PiezaDTO
     */
    List<PiezaDTO> consultarTopMesPiezas();

    /**
     * Consulta las piezas más vendidas en todo el tiempo del sistema
     *
     * @return lista de PiezaDTO
     */
    List<PiezaDTO> consultarTopTodoPiezas();
    
    /**
     * Consulta todas las ventas del sistema
     *
     * @return lista de VentaDTO
     */
    List<VentaDTO> consultarVentas();
    
    /**
     * Orquesta la lógica de procesar una venta (registro de
     * la venta, actualización de stock)
     * 
     * @param cliente que compró las piezas
     * @param detalles de la venta
     * 
     * @return la venta registrada
     */
    VentaDTO procesarVenta(ClienteDTO cliente, List<DetallesVentaDTO> detalles);
}