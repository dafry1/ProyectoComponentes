package coordinadores;

import DTOS.ContribuyenteDTO;
import DTOS.DetallesVentaDTO;
import DTOS.EmpleadoDTO;
import DTOS.FacturaDTO;
import DTOS.ParticipanteDTO;
import DTOS.PiezaDTO;
import DTOS.SolicitudDTO;
import DTOS.VentaDTO;
import java.util.List;
import observadores.IObservador;

/**
 * Interfaz que establece el contrato para el
 * coordinador con lógica de negocio
 * 
 * @author Andre
 */
public interface ICoordinadorNegocio {
    
    PiezaDTO consultarPieza(String id);
    
    /**
     * Regresa todas las piezas del sistema, dadas directamente
     * por el IFachadaVentas
     * 
     * @return lista de PiezaDTO
     */
    List<PiezaDTO> consultarPiezas();
    
    /**
     * Regresa la cantidad de piezas individuales. No de productos
     * en total, sino de cada tipo de pieza en stock. Se ve como
     * un wrapper simple, pero es importante no meter mano en
     * lógica de negocio como la lista directamente
     * 
     * @return la cantidad de tipos de piezas en específico
     */
    public int totalProductos();
    
    /**
     * Regresa las piezas más vendidas al día en el sistema, dadas directamente
     * por el IFachadaVentas
     *
     * @return lista de PiezaDTO
     */
    public List<PiezaDTO> consultarTopDiaPiezas();

    /**
     * Regresa las piezas más vendidas a la semana en el sistema, dadas
     * directamente por el IFachadaVentas
     *
     * @return lista de PiezaDTO
     */
    public List<PiezaDTO> consultarTopSemanaPiezas();

    /**
     * Regresa las piezas más vendidas al mes en el sistema, dadas directamente
     * por el IFachadaVentas
     *
     * @return lista de PiezaDTO
     */
    public List<PiezaDTO> consultarTopMesPiezas();

    /**
     * Regresa las piezas más vendidas en todo el tiempo en el sistema, dadas
     * directamente por el IFachadaVentas
     *
     * @return lista de PiezaDTO
     */
    public List<PiezaDTO> consultarTopTodoPiezas();
    
    /**
     * Regresa todas las ventas del sistema, dadas directamente por el
     * IFachadaVentas
     * 
     * @return lista de VentaDTO
     */
    List<VentaDTO> consultarVentas();
    
    /**
     * Orquesta todos los métodos necesarios para llevar a
     * cabo una venta dentro del sistema. Actualiza stock,
     * limpia el carrito, etc.
     * 
     * @param venta a procesar
     * @param observador si se necesita actualizar algo. Puede ser null
     * 
     * @return la venta procesadas
     */
    VentaDTO procesarVenta(VentaDTO venta, IObservador observador);
    
    public List<PiezaDTO> filtrarPorNombre(String nombre);

    public List<PiezaDTO> filtrarPorCategoria(String categoria);

    public List<PiezaDTO> filtrarPorMarca(String marca);

    public List<PiezaDTO> filtrarPorPrecioMax(double precioMaximo);
    
    
    
    
    public List<PiezaDTO> filtrarPorNombreSoli(String nombre);

    public List<PiezaDTO> filtrarPorCategoriaSoli(String categoria);

    public List<PiezaDTO> filtrarPorMarcaSoli(String marca);
    
    List<PiezaDTO> filtrarPorModeloSoli(String modelo);

    public List<PiezaDTO> filtrarPorPrecioMaxSoli(double precioMaximo);
    
    
    SolicitudDTO procesarSolicitud(SolicitudDTO solicitud, IObservador observador);
    
    public List<SolicitudDTO> consultarSolicitudes();
    
    
    List<PiezaDTO> consultarPiezasBodega();
    
    
    
    
    
    //MÉTODOS DE FACTURA
    /**
     * Consulta un contribuyente por RFC
     * 
     * @param rfc
     * @return 
     */
    ContribuyenteDTO obtenerContribuyente(String rfc);
    
    /**
     * Valida que un juego de datos sea válido
     * 
     * @param celular
     * @param correo
     * 
     * @return true si son válidos
     */
    boolean validarDatos(String celular, String correo);
    
    /**
     * Inicia el proceso para mandar por correo una factura
     * 
     * @param factura a enviar
     */
    void enviarFactura(FacturaDTO factura);
    
    /**
     * Determina si el rango de fecha y hora es válido para
     * proceder con la factura
     * 
     * @param fechaHora
     * 
     * @return true si es válido
     */
    boolean validarRangoFecha(String fechaHora);
    
    /**
     * Muestra la factura
     * 
     * @param infoFormularioReceptor que se hace en presentación
     * @param formaPago
     * 
     * @return el DTO de la factura
     */
    FacturaDTO obtenerFacturaMostrar(ParticipanteDTO infoFormularioReceptor, String formaPago);
    
    /**
     * Obtiene las formas de pago
     * 
     * @return formas de pago
     */
    String[] obtenerFormasPago();
    
    /**
     * Obtiene los régimenes fiscales
     * 
     * @return los régimenes fiscales
     */
    String[] obtenerRegimenesFiscales();
    
    /**
     * Obtiene los CFDIs
     * 
     * @return los CFDIs
     */
    String[] obtenerCfdis();
}