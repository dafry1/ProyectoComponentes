package controles;

import DTOS.ClienteDTO;
import DTOS.DetallesVentaDTO;
import DTOS.PiezaDTO;
import DTOS.VentaDTO;
import excepciones.NegocioException;
import fabricas.FabricaBO;
import interfaces.IFabricaBO;
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
public class ControlVentas{
    private static final System.Logger LOG = System.getLogger(ControlVentas.class.getName());
    private static final String CARRITO_VACIO = "No se puede procesar una venta con un carrito vacío";
    private static final String SIN_CLIENTE = "No se asignó un cliente para la venta";
    
    //Interfaces
    private IPiezaBO piezaBO;
    private IVentaBO ventaBO;
    
    /** Constructor que se autoinyecta dependencias gracias a la fábrica */
    public ControlVentas() {
        IFabricaBO fabricaBO = FabricaBO.singleton();
        this.piezaBO = fabricaBO.fabricarPieza();
        this.ventaBO = fabricaBO.fabricarVenta();
    }
    
    /**
     * Consulta las piezas de su BO respectivo
     * 
     * @return lista de tipo PiezaDTO
     */
    public List<PiezaDTO> consultarPiezas() {
        List<PiezaDTO> piezas = piezaBO.consultarPiezas();
        LOG.log(System.Logger.Level.INFO, () -> ">> Piezas consultadas con éxito: " + piezas.size());
        return piezas;
    }
    
    /**
     * Consulta las piezas más vendidas en el día del sistema
     *
     * @return lista de PiezaDTO
     */
    public List<PiezaDTO> consultarTopDiaPiezas() {
        List<PiezaDTO> piezasTopDia = piezaBO.consultarTopDiaPiezas();
        LOG.log(System.Logger.Level.INFO, () -> ">> Piezas consultadas con éxito: " + piezasTopDia.size());
        return piezasTopDia;
    }
    
    /**
     * Consulta las piezas más vendidas en la semana del sistema
     *
     * @return lista de PiezaDTO
     */
    public List<PiezaDTO> consultarTopSemanaPiezas() {
        List<PiezaDTO> piezasTopSemana = piezaBO.consultarTopSemanaPiezas();
        LOG.log(System.Logger.Level.INFO, () -> ">> Piezas consultadas con éxito: " + piezasTopSemana.size());
        return piezasTopSemana;
    }
    
    /**
     * Consulta las piezas más vendidas en el mes del sistema
     *
     * @return lista de PiezaDTO
     */
    public List<PiezaDTO> consultarTopMesPiezas() {
        List<PiezaDTO> piezasTopMes = piezaBO.consultarTopMesPiezas();
        LOG.log(System.Logger.Level.INFO, () -> ">> Piezas consultadas con éxito: " + piezasTopMes.size());
        return piezasTopMes;
    }
    
    /**
     * Consulta las piezas más vendidas en todo el tiempo del sistema
     *
     * @return lista de PiezaDTO
     */
    public List<PiezaDTO> consultarTopTodoPiezas() {
        List<PiezaDTO> piezasTopTodo = piezaBO.consultarTopTodoPiezas();
        LOG.log(System.Logger.Level.INFO, () -> ">> Piezas consultadas con éxito: " + piezasTopTodo.size());
        return piezasTopTodo;
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