package interfaces;

import dominio.Venta;
import java.util.List;

/**
 *
 * @author Andre
 */
public interface IVentaDAO {
    /**
     * Extrae todas las ventas de la BD
     *
     * @return lista de ventas
     */
    List<Venta> consultarVentas();
    
    /**
     * Registra una venta en el sistema
     * 
     * @param venta
     * 
     * @return la venta creada
     */
    Venta registrarVenta(Venta venta);
}