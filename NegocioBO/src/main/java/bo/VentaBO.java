package bo;

import DTOS.ClienteDTO;
import DTOS.EmpleadoDTO;
import DTOS.VentaDTO;
import dominio.Venta;
import excepciones.NegocioException;
import adaptadores.IAdaptadorPieza;
import adaptadores.IAdaptadorVenta;
import daos.IPiezaDAO;
import daos.IVentaDAO;
import excepciones.PersistenciaException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import utilerias.UtilNegocio;

/**
 * BO para la entidad venta
 * 
 * @author Andre
 */
public class VentaBO implements IVentaBO {
    private static final System.Logger LOG = System.getLogger(VentaBO.class.getName());
    String DEBUG;
    
    //Atributos
    private IVentaDAO ventaDAO;
    private IAdaptadorVenta adaptadorVenta;
    
    
    /**
     * Constructor que inyecta DAO y adaptador
     * 
     * @param ventaDAO
     * @param adaptadorVenta 
     */
    public VentaBO(IVentaDAO ventaDAO, IAdaptadorVenta adaptadorVenta) {
        this.ventaDAO = ventaDAO;
        this.adaptadorVenta = adaptadorVenta;
    }
    
    /** Centraliza la forma en la que se adaptan las piezas de Entidad a DTO */
    private List<VentaDTO> adaptarPiezasInternamente(List<Venta> ventas) {
        return adaptadorVenta.listaDTO(ventas);
    }
    
    /**
     * Extrae todas las ventas de la BD
     *
     * @return lista de VentaDTO mapeadas
     */
    @Override
    public List<VentaDTO> consultarVentas() {
        return adaptarPiezasInternamente(ventaDAO.consultarVentas());
    }
    
    /**
     * Devuelve la cantidad de ventas del dia de la consulta, desde la BD
     *
     * @return cantidad de ventas registradas
     */
    @Override
    public int cantidadVentasDiarias(){
        return ventaDAO.cantidadVentas();
    }
    
    /**
     * Registra una venta en el sistema
     * 
     * @param venta
     * 
     * @return la venta creada
     */
    @Override
    public VentaDTO registrarVenta(VentaDTO venta) {
        //Venta inválida
        if (venta == null || venta.getDetalles().isEmpty()) {
            DEBUG = "Venta vacía o sin detalles";
            LOG.log(System.Logger.Level.ERROR, ">>" + DEBUG);
            throw new NegocioException(DEBUG);
        }
        
        //Sin cliente
        ClienteDTO cliente = venta.getCliente();
        if (cliente == null) {
            DEBUG = "Venta sin cliente";
            LOG.log(System.Logger.Level.ERROR, ">>" + DEBUG);
            throw new NegocioException(DEBUG); 
        }
        
        //Sin empleado
        EmpleadoDTO empleado = venta.getEmpleado();
        if (empleado == null) {
            DEBUG = "Venta sin empleado";
            LOG.log(System.Logger.Level.ERROR, ">>" + DEBUG);
            throw new NegocioException(DEBUG); 
        }
        
        //Registra la venta
        try {
            Venta v = adaptadorVenta.Entidad(venta);
            return adaptadorVenta.DTO(ventaDAO.registrarVenta(v));  
        } catch (PersistenciaException e) {
            throw new NegocioException("");
        }
    }

    @Override
    public List<VentaDTO> filtrarVentasFecha(LocalDate fecha) {
        if (fecha.isAfter(LocalDate.now())) {
            String DEBUG = "Fecha mayor a la actual";
            LOG.log(System.Logger.Level.ERROR, ">>" + DEBUG);
            throw new NegocioException(DEBUG);
        }
        return adaptarPiezasInternamente(ventaDAO.consultarVentas());
    }

    @Override
    public List<VentaDTO> filtrarVentasTotalMinimo(double minimo) {
        if (minimo <= 0) {
            String DEBUG = "Total mínimo menor a 0";
            LOG.log(System.Logger.Level.ERROR, ">>" + DEBUG);
            throw new NegocioException(DEBUG);
        }
        return adaptarPiezasInternamente(ventaDAO.consultarVentas());
    }

    @Override
    public List<VentaDTO> filtrarVentasTotalMaximo(double maximo) {
        if (maximo <= 0) {
            String DEBUG = "Total máximo menor a 0";
            LOG.log(System.Logger.Level.ERROR, ">>" + DEBUG);
            throw new NegocioException(DEBUG);
        }
        return adaptarPiezasInternamente(ventaDAO.consultarVentas());
    }
}