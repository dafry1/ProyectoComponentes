package fachada;

import DTOS.ClienteDTO;
import DTOS.DetallesVentaDTO;
import DTOS.PiezaDTO;
import DTOS.VentaDTO;
import bo.PiezaBO;
import bo.VentaBO;
import excepciones.NegocioException;
import interfaces.IFachadaVentas;
import interfaces.IPiezaBO;
import interfaces.IVentaBO;
import java.util.List;

/**
 * Unifica los BOs con sus métodos en una sola clase para
 * centralizar toda la lógica necesaria para registrar y
 * manejar ventas
 * 
 * @author Andre
 */
public class FachadaVentas implements IFachadaVentas {
    private static final System.Logger LOG = System.getLogger(FachadaVentas.class.getName());
    private static String CARRITO_VACIO = "No se puede procesar una venta con un carrito vacío";
    private static String SIN_CLIENTE = "No se asignó un cliente para la venta";
    
    //Interfaces
    private IPiezaBO piezaBO = new PiezaBO();
    private IVentaBO ventaBO = new VentaBO();
    
    /**
     * Consulta las piezas de su BO respectivo
     * 
     * @return lista de tipo PiezaDTO
     */
    @Override
    public List<PiezaDTO> consultarPiezas() {
        return piezaBO.consultarPiezas();
    }
    
    
    
    /**
     * Orquesta la lógica de procesar una venta (registro de
     * la venta, actualización de stock)
     * 
     * @param cliente que compró las piezass
     * @param detalles de la venta
     * 
     * @return la venta registrada
     */
    @Override
    public VentaDTO procesarVenta(ClienteDTO cliente, List<DetallesVentaDTO> detalles) {
        
        //Excepción si la lista está vacía o es null
        if (detalles == null || detalles.isEmpty()) {
            LOG.log(System.Logger.Level.ERROR, CARRITO_VACIO);
            throw new NegocioException(CARRITO_VACIO);
        }
        
        //Excepción si es null
        if (cliente == null) {
            cliente = new ClienteDTO(); //-> FIXME: LO CREA AHÍ A LA AHÍ SE VA XD LUEGO DEBE VALIDAR BIEN
            //LOG.log(System.Logger.Level.ERROR, SIN_CLIENTE);
            //throw new NegocioException(SIN_CLIENTE);
        } 
        
        //Actualiza stock y registra la venta
        piezaBO.actualizarStockTrasVenta(detalles);
        return ventaBO.registrarVenta(cliente, detalles);
    }
}