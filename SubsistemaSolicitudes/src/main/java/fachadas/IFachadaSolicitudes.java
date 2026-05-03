package fachadas;

import DTOS.DetallesVentaDTO;
import DTOS.PiezaDTO;
import DTOS.SolicitudDTO;
import java.time.LocalDate;
import java.util.List;

/**
 * Contrato para la fachada que unifica los métodos del subsistema de solicitudes.
 * Incluye gestión de catálogo, carrito y persistencia de solicitudes.
 * 
 * @author Andre
 */
public interface IFachadaSolicitudes {


    List<PiezaDTO> consultarPiezas();

    PiezaDTO consultarPieza(Long id);

    List<PiezaDTO> filtrarPorNombre(String nombre);

    List<PiezaDTO> filtrarPorCategoria(String categoria);

    List<PiezaDTO> filtrarPorMarca(String marca);

    List<PiezaDTO> filtrarPorPrecioMax(double precioMaximo);


    List<DetallesVentaDTO> getCarritoSolicitud();

    void agregarCarritoSolicitud(DetallesVentaDTO detalle);

    void eliminarCarritoSolicitud(DetallesVentaDTO detalle);

    double totalCarritoSolicitud();

    boolean carritoSolicitudVacio();

    void limpiarCarritoSolicitud();


    /**
     * Procesa y registra la solicitud final en el sistema.
     * Coordina la actualización de inventario y la persistencia.
     * 
     * @param solicitud El objeto DTO con cliente, empleado y detalles.
     * @return La solicitud registrada con su folio y fecha asignada.
     */
    SolicitudDTO procesarSolicitud(SolicitudDTO solicitud);

    /**
     * Consulta el historial completo de solicitudes realizadas.
     * 
     * @return Lista de solicitudes.
     */
    List<SolicitudDTO> consultarHistorialSolicitudes();

    /**
     * Filtra las solicitudes realizadas en una fecha específica.
     * 
     * @param fecha Fecha a consultar.
     * @return Lista de solicitudes que coinciden con la fecha.
     */
    List<SolicitudDTO> filtrarSolicitudesPorFecha(LocalDate fecha);

    /**
     * Busca solicitudes dentro de un rango de costo total.
     * 
     * @param minimo Total mínimo.
     * @return Lista de solicitudes filtradas.
     */
    List<SolicitudDTO> filtrarSolicitudesPorMontoMinimo(double minimo);
    
    List<SolicitudDTO> consultarSolicitudes();
}