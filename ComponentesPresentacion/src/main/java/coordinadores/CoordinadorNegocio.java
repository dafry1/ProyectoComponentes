package coordinadores;

import DTOS.DetallesVentaDTO;
import DTOS.EmpleadoDTO;
import DTOS.PiezaDTO;
import DTOS.SolicitudDTO;
import DTOS.VentaDTO;
import excepciones.PresentacionException;
import excepciones.NegocioException;
import excepciones.PersistenciaException;
import fachada.FachadaInicioSesion;
import fachadas.FachadaSolicitudes;
import fachadas.FachadaVentas;
import fachada.IFachadaInicioSesion;
import java.util.List;
import observadores.IObservador;
import fachadas.IFachadaSolicitudes;
import fachadas.IFachadaVentas;

/**
 * Coordinador encargado de recopilar todas las fachadas con lógica de negocio
 * en un solo lugar
 *
 * @author Andre
 */
public class CoordinadorNegocio implements ICoordinadorNegocio {

    private static final System.Logger LOG = System.getLogger(CoordinadorNegocio.class.getName());

    //Instancia de la fachada del subsistema de las ventas
    private IFachadaVentas fachadaVentas = new FachadaVentas();
    private IFachadaInicioSesion fachadaInicioSesion = new FachadaInicioSesion();
    private IFachadaSolicitudes fachadaSolicitudes = new FachadaSolicitudes();

    //Coordinador auxiliar
    ICoordinadorEstados coordinadorEstados;

    //Constructor
    public CoordinadorNegocio(ICoordinadorEstados coordinadorEstados) {
        this.coordinadorEstados = coordinadorEstados;
    }

    ;
    
    /**
     * Llama a la fachada de ventas para que consulte
     * una pieza por Id
     * 
     * @param id de la pieza
     * 
     * @return la pieza
     */
    @Override
    public PiezaDTO consultarPieza(Long id) {
        try {
            return fachadaVentas.consultarPieza(id);
        } catch (NegocioException e) {
            LOG.log(System.Logger.Level.ERROR, "Error al consultar pieza: " + e.getMessage());
            throw new PresentacionException("No se pudo obtener la pieza solicitada.");
        }
    }

    /**
     * Regresa todas las piezas del sistema, dadas directamente por el
     * IFachadaVentas
     *
     * @return lista de PiezaDTO
     */
    @Override
    public List<PiezaDTO> consultarPiezas() {
        try {
            return fachadaVentas.consultarPiezas();
        } catch (NegocioException e) {
            LOG.log(System.Logger.Level.ERROR, "Error al consultar todas las piezas: " + e.getMessage());
            throw new PresentacionException("Error al cargar el catálogo de piezas.");
        }
    }

    /**
     * Regresa la cantidad de piezas individuales. No de productos en total,
     * sino de cada tipo de pieza en stock. Se ve como un wrapper simple, pero
     * es importante no meter mano en lógica de negocio como la lista
     * directamente
     *
     * @return la cantidad de tipos de piezas en específico
     */
    @Override
    public int totalProductos() {
        try {
            return fachadaVentas.consultarPiezas().size();
        } catch (NegocioException e) {
            LOG.log(System.Logger.Level.ERROR, "Error al calcular total de productos: " + e.getMessage());
            return 0;
            
        }
    }

    /**
     * Regresa las piezas más vendidas al día en el sistema, dadas directamente
     * por el IFachadaVentas
     *
     * @return lista de PiezaDTO
     */
    @Override
    public List<PiezaDTO> consultarTopDiaPiezas() {
        try {
            return fachadaVentas.consultarTopDiaPiezas();
        } catch (NegocioException e) {
            LOG.log(System.Logger.Level.ERROR, "Error en top piezas día: " + e.getMessage());
            throw new PresentacionException("No se pudieron cargar las piezas destacadas del día.");
        }
    }

    /**
     * Regresa las piezas más vendidas a la semana en el sistema, dadas
     * directamente por el IFachadaVentas
     *
     * @return lista de PiezaDTO
     */
    @Override
    public List<PiezaDTO> consultarTopSemanaPiezas() {
        try {
            return fachadaVentas.consultarTopSemanaPiezas();
        } catch (NegocioException e) {
            LOG.log(System.Logger.Level.ERROR, "Error en top piezas semana: " + e.getMessage());
            throw new PresentacionException("No se pudieron cargar las piezas destacadas de la semana.");
        }
    }

    /**
     * Regresa las piezas más vendidas al mes en el sistema, dadas directamente
     * por el IFachadaVentas
     *
     * @return lista de PiezaDTO
     */
    @Override
    public List<PiezaDTO> consultarTopMesPiezas() {
        try {
            return fachadaVentas.consultarTopMesPiezas();
        } catch (NegocioException e) {
            LOG.log(System.Logger.Level.ERROR, "Error en top piezas mes: " + e.getMessage());
            throw new PresentacionException("No se pudieron cargar las piezas destacadas del mes.");
        }
    }

    /**
     * Regresa las piezas más vendidas en todo el tiempo en el sistema, dadas
     * directamente por el IFachadaVentas
     *
     * @return lista de PiezaDTO
     */
    @Override
    public List<PiezaDTO> consultarTopTodoPiezas() {
        try {
            return fachadaVentas.consultarTopTodoPiezas();
        } catch (NegocioException e) {
            LOG.log(System.Logger.Level.ERROR, "Error en top piezas histórico: " + e.getMessage());
            throw new PresentacionException("No se pudieron cargar las piezas más vendidas.");
        }
    }

    /**
     * Regresa todas las ventas del sistema, dadas directamente por el
     * IFachadaVentas
     *
     * @return lista de VentaDTO
     */
    @Override
    public List<VentaDTO> consultarVentas() {
        try {
            return fachadaVentas.consultarVentas();
        } catch (NegocioException e) {
            LOG.log(System.Logger.Level.ERROR, "Error al consultar ventas: " + e.getMessage());
            throw new PresentacionException("Error al obtener el historial de ventas.");
        }
    }

    /**
     * Procesa la venta de la fachada y verifica lo que le importa de
     * presentación: el observador
     *
     * @param venta a procesar
     * @param observador si se necesita actualizar algo. Puede ser null
     */
//    @Override
    @Override
    public VentaDTO procesarVenta(VentaDTO venta, IObservador observador) {
        try {
            fachadaVentas.procesarVenta(venta);
            coordinadorEstados.limpiarCarritoVenta();
            if (observador != null) {
                observador.observar();
            }
            return venta;
        } catch (NegocioException e) {
            LOG.log(System.Logger.Level.ERROR, "Error al procesar venta: " + e.getMessage());
            throw new PresentacionException("Hubo un problema al registrar la venta: " + e.getMessage());
        }
    }

    @Override
    public List<PiezaDTO> filtrarPorNombre(String nombre) {
        try {
            return fachadaVentas.filtrarPorNombre(nombre);
        } catch (NegocioException e) {
            LOG.log(System.Logger.Level.ERROR, "Error filtrando por nombre: " + e.getMessage());
            throw new PresentacionException("Error en la búsqueda por nombre.");
        }
    }

    @Override
    public List<PiezaDTO> filtrarPorCategoria(String categoria) {
        try {
            return fachadaVentas.filtrarPorCategoria(categoria);
        } catch (NegocioException e) {
            LOG.log(System.Logger.Level.ERROR, "Error filtrando por categoría: " + e.getMessage());
            throw new PresentacionException("Error en la búsqueda por categoría.");
        }
    }

    @Override
    public List<PiezaDTO> filtrarPorMarca(String marca) {
        try {
            return fachadaVentas.filtrarPorMarca(marca);
        } catch (NegocioException e) {
            LOG.log(System.Logger.Level.ERROR, "Error filtrando por marca: " + e.getMessage());
            throw new PresentacionException("Error en la búsqueda por marca.");
        }
    }

    @Override
    public List<PiezaDTO> filtrarPorPrecioMax(double precioMaximo) {
        try {
            return fachadaVentas.filtrarPorPrecioMax(precioMaximo);
        } catch (NegocioException e) {
            LOG.log(System.Logger.Level.ERROR, "Error filtrando por precio: " + e.getMessage());
            throw new PresentacionException("Error en la búsqueda por precio.");
        }
    }

    @Override
    public List<PiezaDTO> filtrarPorNombreSoli(String nombre) {
        try {
            return fachadaSolicitudes.filtrarPorNombre(nombre);
        } catch (NegocioException e) {
            LOG.log(System.Logger.Level.ERROR, "Error filtrando solicitud por nombre: " + e.getMessage());
            throw new PresentacionException("Error en la búsqueda de bodega.");
        }
    }

    @Override
    public List<PiezaDTO> filtrarPorCategoriaSoli(String categoria) {
        try {
            return fachadaSolicitudes.filtrarPorCategoria(categoria);
        } catch (NegocioException e) {
            LOG.log(System.Logger.Level.ERROR, "Error filtrando solicitud por categoría: " + e.getMessage());
            throw new PresentacionException("Error en el filtro de bodega.");
        }
    }

    @Override
    public List<PiezaDTO> filtrarPorMarcaSoli(String marca) {
        try {
            return fachadaSolicitudes.filtrarPorMarca(marca);
        } catch (NegocioException e) {
            LOG.log(System.Logger.Level.ERROR, "Error filtrando solicitud por marca: " + e.getMessage());
            throw new PresentacionException("Error en el filtro de bodega.");
        }
    }

    @Override
    public List<PiezaDTO> filtrarPorPrecioMaxSoli(double precioMaximo) {
        try {
            return fachadaSolicitudes.filtrarPorPrecioMax(precioMaximo);
        } catch (NegocioException e) {
            LOG.log(System.Logger.Level.ERROR, "Error filtrando solicitud por precio: " + e.getMessage());
            throw new PresentacionException("Error en el filtro de bodega.");
        }
    }

    @Override
    public SolicitudDTO procesarSolicitud(SolicitudDTO solicitud, IObservador observador) {
        if (solicitud == null) {
            throw new PresentacionException("Solicitud vacía");
        }

        try {
            fachadaSolicitudes.procesarSolicitud(solicitud);
            coordinadorEstados.limpiarCarritoSolicitud();
            if (observador != null) {
                observador.observar();
            }
            return solicitud;
        } catch (NegocioException e) {
            LOG.log(System.Logger.Level.ERROR, "Error al procesar solicitud: " + e.getMessage());
            throw new PresentacionException("No se pudo enviar la solicitud a la bodega: " + e.getMessage());
        }
    }

    @Override
    public List<SolicitudDTO> consultarSolicitudes() {
        try {
            return fachadaSolicitudes.consultarSolicitudes();
        } catch (NegocioException e) {
            LOG.log(System.Logger.Level.ERROR, "Error al consultar solicitudes: " + e.getMessage());
            throw new PresentacionException("Error al cargar historial de pedidos.");
        }
    }

    @Override
    public List<PiezaDTO> consultarPiezasBodega() {
        try {
            return fachadaSolicitudes.consultarPiezas();
        } catch (NegocioException e) {
            LOG.log(System.Logger.Level.ERROR, "Error al conectar con bodega: " + e.getMessage());
            throw new PresentacionException("No se pudo conectar con la bodega remota.");
        }
    }
}
