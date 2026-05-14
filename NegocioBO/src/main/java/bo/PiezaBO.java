package bo;

import DTOS.DetallesVentaDTO;
import DTOS.PiezaDTO;
import dominio.Pieza;
import excepciones.NegocioException;
import adaptadores.IAdaptadorDetallesVenta;
import adaptadores.IAdaptadorPieza;
import daos.IPiezaDAO;
import excepciones.PersistenciaException;
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
     * @param adaptadorDetalles 
     */
    public PiezaBO(IPiezaDAO piezaDAO, IAdaptadorPieza adaptadorPieza, IAdaptadorDetallesVenta adaptadorDetalles) {
        this.piezaDAO = piezaDAO;
        this.adaptadorPieza = adaptadorPieza;
        this.adaptadorDetalles = adaptadorDetalles;
    }
    
    /**
     * Consulta una pieza por id
     * 
     * @param id
     * @return 
     */
    @Override
    public PiezaDTO consultarPieza(String id) {
        try {
            //Id inválida
            if (id == null) {
                String DEBUG = "Id inválida";
                LOG.log(System.Logger.Level.ERROR, DEBUG);
                throw new NegocioException(">>" + DEBUG);
            }

            //Intenta hallar la pieza
            Pieza piezaEncontrada = piezaDAO.consultarPieza(id);
            if (piezaEncontrada == null) {
                String DEBUG = "Pieza no encontrada";
                LOG.log(System.Logger.Level.ERROR, DEBUG);
                throw new NegocioException(">>" + DEBUG);
            }        
            return adaptadorPieza.DTO(piezaEncontrada);        
        } catch (PersistenciaException e) {
            String MSJ = "Error al consultar la pieza específica: " + e.getMessage();
            LOG.log(System.Logger.Level.ERROR, MSJ);
            throw new NegocioException(MSJ);
        }
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
        try {
            return adaptarPiezasInternamente(piezaDAO.consultarPiezas());
        } catch (PersistenciaException e) {
            String MSJ = "Error al consultar las piezas: " + e.getMessage();
            LOG.log(System.Logger.Level.ERROR, MSJ);
            throw new NegocioException(MSJ);
        }
    }
    
    /**
     * Consulta las piezas más vendidas al día registradas en el sistema
     * 
     * @return la lista de piezas en DTO
     */
    @Override
    public List<PiezaDTO> consultarTopDiaPiezas() {
        try {
            return adaptarPiezasInternamente(piezaDAO.consultarTopDiaPiezas());
        } catch (PersistenciaException e) {
            String MSJ = "Error al consultar las piezas más vendidas al día: " + e.getMessage();
            LOG.log(System.Logger.Level.ERROR, MSJ);
            throw new NegocioException(MSJ);
        }
    }
    
    /**
     * Consulta las piezas más vendidas a la semana registradas en el sistema
     * 
     * @return la lista de piezas en DTO
     */
    @Override
    public List<PiezaDTO> consultarTopSemanaPiezas() {
        try {
            return adaptarPiezasInternamente(piezaDAO.consultarTopSemanaPiezas());
        } catch (PersistenciaException e) {
            String MSJ = "Error al consultar las piezas más vendidas a la semana: " + e.getMessage();
            LOG.log(System.Logger.Level.ERROR, MSJ);
            throw new NegocioException(MSJ);
        }
    }
    
    /**
     * Consulta las piezas más vendidas al mes registradas en el sistema
     * 
     * @return la lista de piezas en DTO
     */
    @Override
    public List<PiezaDTO> consultarTopMesPiezas() {
        try {
            return adaptarPiezasInternamente(piezaDAO.consultarTopMesPiezas());
        } catch (PersistenciaException e) {
            String MSJ = "Error al consultar las piezas más vendidas al mes: " + e.getMessage();
            LOG.log(System.Logger.Level.ERROR, MSJ);
            throw new NegocioException(MSJ);
        }
    }
    
    /**
     * Consulta las piezas más vendidas en todo el tiempo registradas en
     * el sistema
     * 
     * @return la lista de piezas en DTO
     */
    @Override
    public List<PiezaDTO> consultarTopTodoPiezas() {
        try {
            return adaptarPiezasInternamente(piezaDAO.consultarTopTodoPiezas());
        } catch (PersistenciaException e) {
            String MSJ = "Error al consultar las piezas más vendidas en todo el tiempo: " + e.getMessage();
            LOG.log(System.Logger.Level.ERROR, MSJ);
            throw new NegocioException(MSJ);
        }
    }
    
    /**
     * Busca la pieza del detalle para actualizar el stock de esa pieza
     * 
     * @param detalle 
     */
    @Override
    public void actualizarStock(DetallesVentaDTO detalle) {
        try {
            if (detalle == null) {
                String DEBUG = "Detalle vacío";
                LOG.log(System.Logger.Level.ERROR, DEBUG);
                throw new NegocioException(">>" + DEBUG);
            }
            piezaDAO.actualizarStock(adaptadorDetalles.Entidad(detalle));
        } catch (PersistenciaException e) {
            String MSJ = "Error al actualizar el stock de esta pieza: " + e.getMessage();
            LOG.log(System.Logger.Level.ERROR, MSJ);
            throw new NegocioException(MSJ);
        }
    }
    
    /**
     * Utiliza el método actualizarStock iterando sobre la lista
     * de detalles para actualizar todos las piezas
     * 
     * @param detalles de la venta y el stock se debe actualizar
     */
    @Override
    public void actualizarStockTrasVenta(List<DetallesVentaDTO> detalles) {
        try {
            if (detalles == null) {
                String DEBUG = "Lista de detalles vacía";
                LOG.log(System.Logger.Level.ERROR, DEBUG);
                throw new NegocioException(">>" + DEBUG);
            }
            piezaDAO.actualizarStockTrasVenta(adaptadorDetalles.listaEntidad(detalles));
        } catch (PersistenciaException e) {
            String MSJ = "Error al actualizar el stock de las piezas la venta: " + e.getMessage();
            LOG.log(System.Logger.Level.ERROR, MSJ);
            throw new NegocioException(MSJ);
        }
    }

    @Override
    public List<PiezaDTO> filtrarPorNombre(String nombre) {
        try {
            if (nombre == null) {
                String DEBUG = "Nombre vacío";
                LOG.log(System.Logger.Level.ERROR, DEBUG);
                throw new NegocioException(">>" + DEBUG);
            }
            return adaptarPiezasInternamente(piezaDAO.filtrarPorNombre(nombre));
        } catch (PersistenciaException e) {
            String MSJ = "Error al filtrar las piezas por nombre: " + e.getMessage();
            LOG.log(System.Logger.Level.ERROR, MSJ);
            throw new NegocioException(MSJ);
        }
    }

    @Override
    public List<PiezaDTO> filtrarPorCategoria(String categoria) {
        try {
            if (categoria == null) {
                String DEBUG = "Categoría vacía";
                LOG.log(System.Logger.Level.ERROR, ">>" + DEBUG);
                throw new NegocioException(DEBUG);
            }
            return adaptarPiezasInternamente(piezaDAO.filtrarPorCategoria(categoria));
        } catch (PersistenciaException e) {
            String MSJ = "Error al filtrar las piezas por categoria: " + e.getMessage();
            LOG.log(System.Logger.Level.ERROR, MSJ);
            throw new NegocioException(MSJ);
        }
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