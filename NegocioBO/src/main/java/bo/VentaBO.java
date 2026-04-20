package bo;

import DTOS.ClienteDTO;
import DTOS.DetallesVentaDTO;
import DTOS.VentaDTO;
import interfaces.IVentaBO;
import java.util.List;

/**
 * BO par la entidad venta
 * 
 * @author Andre
 */
public class VentaBO implements IVentaBO {
    
    /**
     * Registra una venta en el sistema
     * 
     * @param cliente que compró las piezas
     * @param detalles que compró el client
     * 
     * @return la venta creada
     */
    @Override
    public VentaDTO registrarVenta(ClienteDTO cliente, List<DetallesVentaDTO> detalles) {
        VentaDTO venta = new VentaDTO();
        venta.setCliente(cliente);
        venta.setDetalles(detalles);
        venta.setFechaHora("Hoy xd");
        venta.setFolio("001"); 
        return venta;
    }
}