
package controles;

import DTOS.ClienteDTO;
import DTOS.DetallesVentaDTO;
import DTOS.EmpleadoDTO;
import DTOS.VentaDTO;
import excepciones.NegocioException;
import fabricas.IFabricaBO;
import bo.IPiezaBO;
import bo.IVentaBO;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import utilerias.UtilNegocio;

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
        try {
            List<VentaDTO> ventas = ventaBO.consultarVentas();
            LOG.log(System.Logger.Level.INFO, () -> ">> Ventas consultadas con éxito: " + ventas.size());
            return ventas;
        } catch (NegocioException e) {
            String MSJ = e.getMessage();
            LOG.log(System.Logger.Level.ERROR, MSJ);
            throw new NegocioException(MSJ);
        }
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
        
        //Sin cliente
        ClienteDTO cliente = venta.getCliente();
        if (cliente == null) {
            LOG.log(System.Logger.Level.ERROR, SIN_CLIENTE);
            throw new NegocioException(SIN_CLIENTE); 
        }
        
        //Sin empleado
        EmpleadoDTO empleado = venta.getEmpleado();
        if (empleado == null) {
            String MSJ = "Venta sin empleado";
            LOG.log(System.Logger.Level.ERROR, ">>" + MSJ);
            throw new NegocioException(MSJ); 
        }
        
        //Datos del cliente inválidos
        if (!UtilNegocio.validarCliente(venta.getCliente())) {
            String DEBUG = "Cliente con datos inválidos";
            LOG.log(System.Logger.Level.ERROR, ">>" + DEBUG);
            throw new NegocioException(DEBUG); 
        }
        
        //Datos del empleado inválidos
        if (!UtilNegocio.validarEmpleado(venta.getEmpleado())) {
            String DEBUG = "Empleado con datos inválidos";
            LOG.log(System.Logger.Level.ERROR, ">>" + DEBUG);
            throw new NegocioException(DEBUG); 
        }
        
        //Asigna folio y fecha y hora
        venta.setFolio(generarFolio());
        venta.setFechaHora(UtilNegocio.hoyTexto());
        
        //Actualiza stock y registra la venta
        try {
            piezaBO.actualizarStockTrasVenta(venta.getDetalles());
            ventaBO.registrarVenta(venta);
            LOG.log(System.Logger.Level.INFO, () -> ">> Venta exitosa con la cantidad de: " + venta.getDetalles().size());
            return venta;
        } catch (NegocioException e) {
            String MSJ = e.getMessage();
            LOG.log(System.Logger.Level.ERROR, MSJ);
            throw new NegocioException(MSJ);
        }
    }
    
    /**
     * Genera un folio para la venta
     *
     * @param venta
     */
    private String generarFolio() {
        int numero = ventaBO.cantidadVentasDiarias() + 1;
        return "VEN - " + numero;
    }
}
