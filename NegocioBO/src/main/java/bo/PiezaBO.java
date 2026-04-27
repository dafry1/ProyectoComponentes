package bo;

import DTOS.DetallesVentaDTO;
import DTOS.PiezaDTO;
import dominio.Pieza;
import excepciones.NegocioException;
import interfaces.IAdaptadorDetallesVenta;
import interfaces.IAdaptadorPieza;
import interfaces.IPiezaBO;
import interfaces.IPiezaDAO;
import java.util.List;
import utilerias.UtilNegocio;

/**
 * BO que se conecta directamente con la persistencia para
 * extraer los datos de la BD
 * 
 * @author Andre
 */
public class PiezaBO implements IPiezaBO {
    private static final System.Logger LOG = System.getLogger(PiezaBO.class.getName());
    private static String CARRITO_VACIO = "No se puede procesar una venta con un carrito vacío";
    
    //Atributos
    private IPiezaDAO piezaDAO;
    private IAdaptadorPieza adaptadorPieza;
    private IAdaptadorDetallesVenta adaptadorDetalles;
    
    /**
     * Constructor que inyecta DAO y adaptador
     * 
     * @param piezaDAO
     * @param adaptadorPieza 
     */
    public PiezaBO(IPiezaDAO piezaDAO, IAdaptadorPieza adaptadorPieza, IAdaptadorDetallesVenta adaptadorDetalles) {
        this.piezaDAO = piezaDAO;
        this.adaptadorPieza = adaptadorPieza;
        this.adaptadorDetalles = adaptadorDetalles;
    }
    
    /** Centraliza la forma en la que se adaptan las piezas de Entidad a DTO */
    private List<PiezaDTO> adaptarPiezasInternamente(List<Pieza> piezas) {
        return adaptadorPieza.listaDTO(piezas);
    }
    
    /**
     * Extrae todas las piezas de la BD
     * 
     * @return lista de PiezaDTO mapeadas
     */
    @Override
    public List<PiezaDTO> consultarPiezas() {
        return adaptarPiezasInternamente(piezaDAO.consultarPiezas());
    }
    
    /**
     * Consulta las piezas más vendidas al día registradas en el sistema
     * 
     * @return la lista de piezas en DTO
     */
    @Override
    public List<PiezaDTO> consultarTopDiaPiezas(){
        return adaptarPiezasInternamente(piezaDAO.consultarTopDiaPiezas());
    }
    
    /**
     * Consulta las piezas más vendidas a la semana registradas en el sistema
     * 
     * @return la lista de piezas en DTO
     */
    @Override
    public List<PiezaDTO> consultarTopSemanaPiezas(){
        return adaptarPiezasInternamente(piezaDAO.consultarTopSemanaPiezas());
    }
    
    /**
     * Consulta las piezas más vendidas al mes registradas en el sistema
     * 
     * @return la lista de piezas en DTO
     */
    @Override
    public List<PiezaDTO> consultarTopMesPiezas(){
        return adaptarPiezasInternamente(piezaDAO.consultarTopMesPiezas());
    }
    
    /**
     * Consulta las piezas más vendidas en todo el tiempo registradas en
     * el sistema
     * 
     * @return la lista de piezas en DTO
     */
    @Override
    public List<PiezaDTO> consultarTopTodoPiezas(){
        return adaptarPiezasInternamente(piezaDAO.consultarTopTodoPiezas());
    }
    
    /**
     * Busca la pieza del detalle para actualizar el stock de esa pieza
     * 
     * @param detalle 
     */
    @Override
    public void actualizarStock(DetallesVentaDTO detalle) {
        if (detalle == null) {
            String DEBUG = "Detalle vacío";
            LOG.log(System.Logger.Level.ERROR, DEBUG);
            throw new NegocioException(">>" + DEBUG);
        }
        piezaDAO.actualizarStock(adaptadorDetalles.Entidad(detalle));
    }
    
    /**
     * Utiliza el método actualizarStock iterando sobre la lista
     * de detalles para actualizar todos las piezas
     * 
     * @param detalles de la venta y el stock se debe actualizar
     */
    @Override
    public void actualizarStockTrasVenta(List<DetallesVentaDTO> detalles) {
        if (detalles == null || detalles.isEmpty()) {
            String DEBUG = "Lista de detalles vacía";
            LOG.log(System.Logger.Level.ERROR, DEBUG);
            throw new NegocioException(">>" + DEBUG);
        }
        piezaDAO.actualizarStockTrasVenta(adaptadorDetalles.listaEntidad(detalles));
    }

    @Override 
    public List<PiezaDTO> filtrarPorNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) {
            String DEBUG = "Nombre vacío";
            LOG.log(System.Logger.Level.ERROR, DEBUG);
            throw new NegocioException(">>" + DEBUG);
        }
        return adaptarPiezasInternamente(piezaDAO.filtrarPorNombre(nombre));
    }

    @Override
    public List<PiezaDTO> filtrarPorCategoria(String categoria) {
        if (categoria == null || categoria.isBlank()) {
            String DEBUG = "Categoría vacía";
            LOG.log(System.Logger.Level.ERROR, ">>" + DEBUG);
            throw new NegocioException(DEBUG);
        }
        return adaptarPiezasInternamente(piezaDAO.filtrarPorCategoria(categoria));
    }

    @Override
    public List<PiezaDTO> filtrarPorMarca(String marca) {
        if (marca == null || marca.isBlank()) {
            String DEBUG = "Marca vacía";
            LOG.log(System.Logger.Level.ERROR, ">>" + DEBUG);
            throw new NegocioException(DEBUG);
        }
        return adaptarPiezasInternamente(piezaDAO.filtrarPorMarca(marca));
    }

    @Override
    public List<PiezaDTO> filtrarPorPrecioMax(double precioMaximo) {
        if (precioMaximo <= 0) {
            String DEBUG = "Precio igual o menor a 0";
            LOG.log(System.Logger.Level.ERROR, ">>" + DEBUG);
            throw new NegocioException(DEBUG);
        }
        return adaptarPiezasInternamente(piezaDAO.filtrarPorPrecioMax(precioMaximo));
    }
}