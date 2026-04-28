package coordinadores;

import DTOS.ClienteDTO;
import DTOS.DetallesVentaDTO;
import DTOS.EmpleadoDTO;
import java.util.List;

/**
 * Fachada de un coordinador que almacena estados globales
 *
 * @author Andre
 */
public interface ICoordinadorEstados {

    //----- MÉTODOS DE TRABAJADORES -----//
    
    /**
     * Indica si la sesión actual le pertenece a un administrador
     *
     * @return
     */
    boolean esAdministrador();

    /**
     * Cierra la sesión limpiando los datos
     */
    void cerrarSesion();

    //----- MÉTODOS DEL CARRITO DE VENTAS -----//
    /**
     * Regresa una lista inmutable del carrito. Solo el coordinador la puede
     * modificar
     */
    List<DetallesVentaDTO> getCarritoVenta();

    /**
     * Agrega una pieza al carrito sin tocar directamente la referencia a la
     * lista
     */
    void agregarCarritoVenta(DetallesVentaDTO detalle);

    /**
     * Elimina una pieza del carrito sin tocar directamente la referencia a la
     * lista
     */
    void eliminarCarritoVenta(DetallesVentaDTO detalle);

    /**
     * Suma el costo de todos los elementos del carrito
     *
     * @return total del carrito
     */
    double totalCarritoVenta();

    /**
     * Suma el costo de todos los elementos del carrito
     *
     * @return total del carrito
     */
    boolean carritoVentaVacio();

    /**
     * Encapsula la lógica de limpiar el carrito
     */
    void limpiarCarritoVenta();

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
    int calcularStockAntesVenta(Long id);
    

    
     /**
     * Verifica la existencia de un empleado
     * 
     * @param usuario
     * @param contra
     * @return 
     */
    public EmpleadoDTO verificarEmpleado(String usuario, String contra);
    
    /**
     * Guarda el empleado actual de manera global
     * 
     * @param empleado dueño de la sesión
     */
    public void establecerSesion(EmpleadoDTO empleado);
    
    /**
     * Regresa el empleado que está usando el sistema actualmente
     *
     * @return
     */
    public EmpleadoDTO getUsuarioLogueado();
    
    public void setCliente(ClienteDTO cliente);
    
    public ClienteDTO getCliente();
}