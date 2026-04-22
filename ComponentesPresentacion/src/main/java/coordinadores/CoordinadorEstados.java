package coordinadores;

import DTOS.DetallesVentaDTO;
import DTOS.EmpleadoDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Coordinador que sabe cosas que deben compartirse a lo largo del sistema, como
 * un objeto específico que debe ser trasladado o recordar qué usuario y de qué
 * tipo entró al programa
 *
 * @author Andre
 */
public class CoordinadorEstados implements ICoordinadorEstados {

    //Instancia de sí mismo
    private static CoordinadorEstados instancia = null;
    private EmpleadoDTO usuarioLogueado;
    private boolean administrador = false;

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
    public void establecerSesion(EmpleadoDTO empleado) {
        this.usuarioLogueado = empleado;
        if (empleado != null && empleado.getId() != null) {
            this.administrador = (empleado.getId() == 1L);
        }
    }

    /**
     * Regresa el empleado que está usando el sistema actualmente
     *
     * @return
     */
    public EmpleadoDTO getUsuarioLogueado() {
        return usuarioLogueado;
    }

    /**
     * Indica si la sesión actual le pertenece a un administrador
     *
     * @return
     */
    public boolean esAdministrador() {
        return administrador;
    }

    /**
     * Cierra la sesión limpiando los datos
     */
    public void cerrarSesion() {
        this.usuarioLogueado = null;
        this.administrador = false;
        this.limpiarCarritoVenta();
    }

    //----- MÉTODOS DEL CARRITO DE VENTAS -----//
    //Carrito actual
    private List<DetallesVentaDTO> carritoVenta = new ArrayList<>();

    /**
     * Regresa una lista inmutable del carrito. Solo el coordinador la puede
     * modificar
     */
    public List<DetallesVentaDTO> getCarritoVenta() {
        return Collections.unmodifiableList(carritoVenta);
    }

    /**
     * Agrega una pieza al carrito sin tocar directamente la referencia a la
     * lista
     */
    public void agregarCarritoVenta(DetallesVentaDTO detalle) {
        if (detalle != null) {
            carritoVenta.add(detalle);
        }
    }

    /**
     * Elimina una pieza del carrito sin tocar directamente la referencia a la
     * lista
     */
    public void eliminarCarritoVenta(DetallesVentaDTO detalle) {
        carritoVenta.remove(detalle);
    }

    /**
     * Suma el costo de todos los elementos del carrito
     *
     * @return total del carrito
     */
    public double totalCarritoVenta() {
        return carritoVenta.stream().mapToDouble(DetallesVentaDTO::getSubtotal).sum();
    }
    
    /**
     * Determina si el carrito de venta está vacío
     * 
     * @return true si está vacío, false de lo contrario
     */
    public boolean carritoVentaVacio() {
        return carritoVenta.isEmpty();
    }

    /**
     * Encapsula la lógica de limpiar el carrito
     */
    public void limpiarCarritoVenta() {
        carritoVenta.clear();
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
    public int calcularStockAntesVenta(Long id) {
        return carritoVenta.stream()
                .filter(d -> d.getPieza().getId().equals(id))
                .mapToInt(DetallesVentaDTO::getCantidad).sum();
    }

}
