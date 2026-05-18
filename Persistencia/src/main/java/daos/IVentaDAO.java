package daos;

import dominio.Venta;
import java.time.LocalDate;
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
     * Muestra la cantidad de ventas registradas el mismo dia de la consulta en la BD
     *
     * @return cantidad de ventas
     */
    int cantidadVentas();
    
    /**
     * Registra una venta en el sistema
     * 
     * @param venta
     * 
     * @return la venta creada
     */
    Venta registrarVenta(Venta venta);
    
    /**
     * Marca una venta como facturada
     * 
     * @param idVenta
     */
    void marcarFacturada(String idVenta);
}