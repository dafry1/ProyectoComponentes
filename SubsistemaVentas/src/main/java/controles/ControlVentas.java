package controles;

import DTOS.ClienteDTO;
import DTOS.DetallesVentaDTO;
import DTOS.VentaDTO;
import excepciones.NegocioException;
import fabricas.IFabricaBO;
import bo.IPiezaBO;
import bo.IVentaBO;
import java.util.List;

/**
 * Control dedicado a todo lo relacionado a una transacción: registrar
 * ventas, consultar ventas, actualizar stock
 * 
 * @author Andre
 */
public class ControlVentas {
    private static final System.Logger LOG = System.getLogger(ControlVentas.class.getName());
    private static final String CARRITO_VACIO = "No se puede procesar una venta con un carrito vacío";
    private static final String SIN_CLIENTE = "No se asignó un cliente para la venta";
    
    //BO
    private IVentaBO ventaBO;
    private IPiezaBO piezaBO;
    
    /**
     * Constructor
     * 
     * @param piezaBO
     * @param ventaBO 
     */
    public ControlVentas(IPiezaBO piezaBO, IVentaBO ventaBO) {
        this.piezaBO = piezaBO;
        this.ventaBO = ventaBO;
    }
    
    /**
     * Consulta las ventas de su BO respectivo
     *
     * @return lista de tipo VentaDTO
     */
    public List<VentaDTO> consultarVentas() {
        List<VentaDTO> ventas = ventaBO.consultarVentas();
        LOG.log(System.Logger.Level.INFO, () -> ">> Ventas consultadas con éxito: " + ventas.size());
        return ventas;
    }
    
    /**
     * Actualiza el stock de PiezaBO y registra
     * la venta en la VentaBO
     * 
     * @param venta a procesar
     * 
     * @return la venta registrada
     */
    public VentaDTO procesarVenta(VentaDTO venta) {
        
        LOG.log(System.Logger.Level.INFO, () -> ">> INICIANDO EL PROCESO DE UNA VENTA");
        
        //Actualiza stock y registra la venta
        piezaBO.actualizarStockTrasVenta(venta.getDetalles());
        ventaBO.registrarVenta(venta);
        LOG.log(System.Logger.Level.INFO, () -> ">> Venta exitosa con la cantidad de: " + venta.getDetalles().size());
        return venta;
    }
}
