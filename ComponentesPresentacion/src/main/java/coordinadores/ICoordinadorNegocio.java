package coordinadores;

import DTOS.DetallesVentaDTO;
import DTOS.EmpleadoDTO;
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
    
    PiezaDTO consultarPieza(Long id);
    
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
    
    /**
     * Valida que el usuario con los datos ingresados
     * exista dentro del sistema
     * 
     * @param nombreUsuario que quiere acceder al sistema
     * @param contra del usuario
     * 
     * @return el empleado DTO en caso de que exista 
     */
    EmpleadoDTO iniciarSesion(String nombreUsuario, String contra);
    
    public List<PiezaDTO> filtrarPorNombre(String nombre);

    public List<PiezaDTO> filtrarPorCategoria(String categoria);

    public List<PiezaDTO> filtrarPorMarca(String marca);

    public List<PiezaDTO> filtrarPorPrecioMax(double precioMaximo);
    
    SolicitudDTO procesarSolicitud(SolicitudDTO solicitud, IObservador observador);
    
    public List<SolicitudDTO> consultarSolicitudes();
}