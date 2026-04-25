package interfaces;

import DTOS.VentaDTO;
import java.util.List;

/**
 * Interfaz para el BO de ventas
 * 
 * @author Andre
 */
public interface IVentaBO {
    
    /**
     * Extrae todas las ventas de la BD
     *
     * @return lista de VentaDTO mapeadas
     */
    List<VentaDTO> consultarVentas();
    
    /**
     * Registra una venta en el sistema
     * 
     * @param venta a registrar
     * 
     * @return la venta creada
     */
    VentaDTO registrarVenta(VentaDTO venta);
}