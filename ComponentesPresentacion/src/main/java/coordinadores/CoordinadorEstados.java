package coordinadores;

import DTOS.ClienteDTO;
import DTOS.DetallesVentaDTO;
import DTOS.EmpleadoDTO;
import DTOS.PiezaDTO;
import fachada.FachadaInicioSesion;
import fachadas.FachadaSolicitudes;
import fachadas.FachadaVentas;
import fachada.IFachadaInicioSesion;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import fachadas.IFachadaSolicitudes;
import fachadas.IFachadaVentas;

/**
 * Coordinador que sabe cosas que deben compartirse a lo largo del sistema, como
 * un objeto específico que debe ser trasladado o recordar qué usuario y de qué
 * tipo entró al programa
 *
 * @author Andre
 */
public class CoordinadorEstados implements ICoordinadorEstados {

    //Fachadas de las cuales necesita los métodos de estados
    private final IFachadaInicioSesion fachadaSesion = new FachadaInicioSesion();
    private final IFachadaVentas fachadaVentas = new FachadaVentas();
    private final IFachadaSolicitudes fachadaSolicitud = new FachadaSolicitudes();
    //Instancia de sí mismo
    private static CoordinadorEstados instancia = null;
    
    
    /**
     * IMPLEMENTACION SUPERULTRAMEGAHIPER TEMPORAL SOLO PARA QUE
     * FUNCIONE AHORITITITA EL RESTO DE ESTADOS VOLÁTILES COMO
     * EL CARRITO O EL EMPLEADO SE MANEJA EN NEGOCIO
     */
    private static ClienteDTO clienteActual = null;
    
    @Override
    public void setCliente(ClienteDTO cliente) {
        this.clienteActual = cliente;
    }
    
    @Override
    public ClienteDTO getCliente() {
        return clienteActual;
    }
    
    @Override
    public boolean existePiezaCarrito(Long id) {
        return fachadaVentas.existePiezaCarrito(id);
    }
    
    
    
    
    
    /**
     * Sinleton que asegura trabajar con una única instancia
     *
     * @return la instancia singleton del coordinador
     */
    public static CoordinadorEstados singleton() {
        if (instancia == null) {
            instancia = new CoordinadorEstados();
        }
        return instancia;
    }

    //----- MÉTODOS DE TRABAJADORES -----//
    /**
     * Guarda el empleado actual de manera global
     * 
     * @param empleado dueño de la sesión
     */
    @Override
    public void establecerSesion(EmpleadoDTO empleado) {
        fachadaSesion.establecerSesion(empleado);
    }

    /**
     * Regresa el empleado que está usando el sistema actualmente
     *
     * @return
     */
    @Override
    public EmpleadoDTO getUsuarioLogueado() {
        return fachadaSesion.getUsuarioLogueado();
    }

    /**
     * Indica si la sesión actual le pertenece a un administrador
     *
     * @return
     */
    @Override
    public boolean esAdministrador() {
        return fachadaSesion.esAdministrador();
    }

    /**
     * Cierra la sesión limpiando los datos
     */
    @Override
    public void cerrarSesion() {
        fachadaSesion.cerrarSesion();
        fachadaVentas.limpiarCarritoVenta();
    }

    //----- MÉTODOS DEL CARRITO DE VENTAS -----//
    /**
     * Regresa una lista inmutable del carrito. Solo el coordinador la puede
     * modificar
     * @return 
     */
    @Override
     public List<DetallesVentaDTO> getCarritoVenta() {
        return fachadaVentas.getCarritoVenta();
    }

    /**
     * Agrega una pieza al carrito sin tocar directamente la referencia a la
     * lista
     * @param detalle
     */
    @Override
    public void agregarCarritoVenta(DetallesVentaDTO detalle) {
        fachadaVentas.agregarCarritoVenta(detalle);
    }

    /**
     * Elimina una pieza del carrito sin tocar directamente la referencia a la
     * lista
     * @param detalle
     */
    @Override
    public void eliminarCarritoVenta(DetallesVentaDTO detalle) {
        fachadaVentas.eliminarCarritoVenta(detalle);
    }

    /**
     * Suma el costo de todos los elementos del carrito
     *
     * @return total del carrito
     */
    @Override
    public double totalCarritoVenta() {
        return fachadaVentas.totalCarritoVenta();
    }
    
    /**
     * Determina si el carrito de venta está vacío
     * 
     * @return true si está vacío, false de lo contrario
     */
    @Override
    public boolean carritoVentaVacio() {
        return fachadaVentas.carritoVentaVacio();
    }

    /**
     * Encapsula la lógica de limpiar el carrito
     */
    @Override
    public void limpiarCarritoVenta() {
        fachadaVentas.limpiarCarritoVenta();
    }

    /**
     * Calcula el stock disponible de cierta pieza aún en medio proceso de la
     * venta. Sirve para validaciones rápidas y lógica de experiencia de
     * usuario. Aunque existan 20 piezas en la BD, si ya elegiste 10, y quieres
     * otras 15, no podrás elegirlas
     *
     * @param id de la pieza a calcular stock antes de la venta
     *
     * @return cantidad de stock de dicha pieza
     */
    @Override
    public int calcularStockAntesVenta(Long id) {
        return fachadaVentas.calcularStockAntesVenta(id);
    }

    //----- MÉTODOS INICIO SESION -----//

    
    /**
     * Verifica la existencia de un empleado
     * 
     * @param usuario
     * @param contra
     * @return 
     */
    @Override
    public EmpleadoDTO verificarEmpleado(String usuario, String contra) {
        return fachadaSesion.verificarEmpleado(usuario, contra);
    }
   
    //----- MÉTODOS SOLICITUD-----//
     /**
     * Consulta las piezas del sistema
     * 
     * @return lista de tipo PiezaDTO
     */
    @Override
    public List<PiezaDTO> consultarPiezas() {
        return fachadaSolicitud.consultarPiezas();
    }
    
    
    /**
     * Regresa el carrito
     * 
     * @return lista de detalles
     */
    @Override
    public List<DetallesVentaDTO> getCarritoSolicitud() {
        return fachadaSolicitud.getCarritoSolicitud();
    }

    
    /**
     * Agrega un detalle
     * 
     * @param detalle 
     */
    @Override
    public void agregarCarritoSolicitud(DetallesVentaDTO detalle) {
        fachadaSolicitud.agregarCarritoSolicitud(detalle);
    }
    
    /**
     * Elimina un detalle
     * 
     * @param detalle 
     */
    @Override
    public void eliminarCarritoSolicitud(DetallesVentaDTO detalle) {
        fachadaSolicitud.eliminarCarritoSolicitud(detalle);
    }

    /** Regresa el total */
    @Override
    public double totalCarritoSolicitud() {
        return fachadaSolicitud.totalCarritoSolicitud();
    }

    /**
     * Determina si está vacío el carrito
     * 
     * @return true o false
     */
    @Override
    public boolean carritoSolicitudVacio() {
        return fachadaSolicitud.carritoSolicitudVacio();
    }

    //Vacía el carrito
    @Override
    public void limpiarCarritoSolicitud() {
        fachadaSolicitud.limpiarCarritoSolicitud();
    }
    
    
}
