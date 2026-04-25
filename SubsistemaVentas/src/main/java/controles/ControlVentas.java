package controles;

import DTOS.ClienteDTO;
import DTOS.DetallesVentaDTO;
import DTOS.VentaDTO;
import excepciones.NegocioException;
import interfaces.IFabricaBO;
import interfaces.IPiezaBO;
import interfaces.IVentaBO;
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
     * Constructor con fábrica inyectada
     * 
     * @param fabricaBO que suministra los BO
     */
    public ControlVentas(IFabricaBO fabricaBO) {
        this.piezaBO = fabricaBO.fabricarPieza();
        this.ventaBO = fabricaBO.fabricarVenta();
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
     * @param cliente que compró las piezass
     * @param detalles de la venta
     * 
     * @return la venta registrada
     */
    public VentaDTO procesarVenta(ClienteDTO cliente, List<DetallesVentaDTO> detalles) {
        
        //Excepción si la lista está vacía o es null
        if (detalles == null || detalles.isEmpty()) {
            LOG.log(System.Logger.Level.ERROR, CARRITO_VACIO);
            throw new NegocioException(CARRITO_VACIO);
        }
        
        //Excepción si es null
        if (cliente == null) {
            cliente = new ClienteDTO(); //-> FIXME: LO CREA A LA AHÍ SE VA XD LUEGO DEBE VALIDAR BIEN
            //LOG.log(System.Logger.Level.ERROR, SIN_CLIENTE);
            //throw new NegocioException(SIN_CLIENTE);
        } 
        
        //Actualiza stock y registra la venta
        piezaBO.actualizarStockTrasVenta(detalles);
        VentaDTO venta = ventaBO.registrarVenta(cliente, detalles);
        LOG.log(System.Logger.Level.INFO, () -> ">> Venta exitosa con la cantidad de: " + venta.getDetalles().size());
        return venta;
    }
}
