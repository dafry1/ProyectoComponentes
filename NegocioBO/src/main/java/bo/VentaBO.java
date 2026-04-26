package bo;

import DTOS.VentaDTO;
import dominio.Venta;
import interfaces.IAdaptadorPieza;
import interfaces.IAdaptadorVenta;
import interfaces.IPiezaDAO;
import interfaces.IVentaBO;
import interfaces.IVentaDAO;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * BO para la entidad venta
 * 
 * @author Andre
 */
public class VentaBO implements IVentaBO {
    private static List<VentaDTO> VENTAS = new ArrayList<>();
    
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
     * Registra una venta en el sistema
     * 
     * @param venta
     * 
     * @return la venta creada
     */
    @Override
    public VentaDTO registrarVenta(VentaDTO venta) {
        generarFolio(venta);
        generarFecha(venta);
        Venta v = adaptadorVenta.Entidad(venta);
        return adaptadorVenta.DTO(ventaDAO.registrarVenta(v));
    }
    
    /**
     * Genera un folio para la venta
     * 
     * @param venta FIXME: TEMPORAL, EN FUNCIÓN REAL DEBE ACUDIR AL DAO PARA CONTAR LAS QUE LLEVAN EN EL DÍA
     */
    private void generarFolio(VentaDTO venta) {
        String PREFIJO = "TW-";
        int contador = VENTAS.size() + 1;
        venta.setFolio(PREFIJO + contador);
    }
    
    
    /**
     * Auxiliar obtiene la fecha
     * 
     * @param venta 
     */
    private void generarFecha(VentaDTO venta) {
        LocalDateTime fechaHoraRegistro = LocalDateTime.now();
        
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        
        String stringFechaHora = fechaHoraRegistro.format(formato);
        
        venta.setFechaHora(stringFechaHora);
    }

    @Override
    public List<VentaDTO> filtrarVentasFecha(LocalDate fecha) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<VentaDTO> filtrarVentasTotalMinimo(double minimo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<VentaDTO> filtrarVentasTotalMaximo(double maximo) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}