package bo;

import DTOS.ClienteDTO;
import DTOS.EmpleadoDTO;
import DTOS.VentaDTO;
import adaptadores.AdaptadorVenta;
import dominio.Venta;
import excepciones.NegocioException;
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
    
    /**
     * Constructor que inyecta DAO y adaptador
     * 
     * @param ventaDAO
     */
    public VentaBO(IVentaDAO ventaDAO) {
        this.ventaDAO = ventaDAO;
    }
    
    /** Centraliza la forma en la que se adaptan las ventas de Entidad a DTO */
    private List<VentaDTO> adaptarVentasInternamente(List<Venta> ventas) {
        return AdaptadorVenta.listaDTO(ventas);
    }
    
    /**
     * Extrae todas las ventas de la BD
     *
     * @return lista de VentaDTO mapeadas
     */
    @Override
    public List<VentaDTO> consultarVentas() {
        return adaptarVentasInternamente(ventaDAO.consultarVentas());
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
        
        //Registra la venta
        try {
            Venta v = AdaptadorVenta.Entidad(venta);
            return AdaptadorVenta.DTO(ventaDAO.registrarVenta(v));  
        } catch (PersistenciaException e) {
            throw new NegocioException("");
        }
    }

    @Override
    public void marcarFacturada(String idVenta) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}