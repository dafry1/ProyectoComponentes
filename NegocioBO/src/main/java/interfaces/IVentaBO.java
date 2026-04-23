package interfaces;

import DTOS.ClienteDTO;
import DTOS.DetallesVentaDTO;
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
     * @param cliente que compró las piezas
     * @param detalles que compró el client
     * 
     * @return la venta creada
     */
    VentaDTO registrarVenta(ClienteDTO cliente, List<DetallesVentaDTO> detalles);
}