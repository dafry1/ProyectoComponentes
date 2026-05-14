package fachadas;

import DTOS.DetallesSolicitudDTO;
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

    //PiezaDTO consultarPieza(Long id);

    List<PiezaDTO> filtrarPorNombre(String nombre);

    List<PiezaDTO> filtrarPorCategoria(String categoria);

    List<PiezaDTO> filtrarPorMarca(String marca);

    List<PiezaDTO> filtrarPorPrecioMax(double precioMaximo);


    List<DetallesSolicitudDTO> getCarritoSolicitud();

    void agregarCarritoSolicitud(DetallesSolicitudDTO detalle);

    void eliminarCarritoSolicitud(DetallesSolicitudDTO detalle);

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
    
    
    List<SolicitudDTO> consultarSolicitudes();
}