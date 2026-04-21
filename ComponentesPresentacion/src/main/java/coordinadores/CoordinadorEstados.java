package coordinadores;

import DTOS.DetallesVentaDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Coordinador que sabe cosas que deben compartirse a lo largo del
 * sistema, como un objeto específico que debe ser trasladado o
 * recordar qué usuario y de qué tipo entró al programa
 * 
 * @author Andre
 */
public class CoordinadorEstados {
    
    //Instancia de sí mismo
    private static CoordinadorEstados instancia = null;
    
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
    /** Guarda que la sesión actual le pertenece a un administrador */
    public void establecerAdministrador() {
        //TODO lógica
    }
    
    /** Guarda que la sesión actual le pertenece a un empleado */
    public void establecerEmpleado() {
        //TODO lógica
    }
    
    /** Indica si la sesión actual le pertenece a un administrador */
    public boolean esAdministrador() {
        //TODO lógica
        return true; //FIXME: ES TEMPORAL
    }
    
    
    
    
    
    //----- MÉTODOS DEL CARRITO DE VENTAS -----//
    //Carrito actual
    private List<DetallesVentaDTO> carritoVenta = new ArrayList<>();
    
    /** Regresa una lista inmutable del carrito. Solo el coordinador la puede modificar */
    public List<DetallesVentaDTO> getCarritoVenta() {
        return Collections.unmodifiableList(carritoVenta);
    }
    
    /** Agrega una pieza al carrito sin tocar directamente la referencia a la lista */
    public void agregarCarritoVenta(DetallesVentaDTO detalle) {
        if (detalle != null) { 
            carritoVenta.add(detalle); 
        }
    }
    
    /** Elimina una pieza del carrito sin tocar directamente la referencia a la lista */
    public void eliminarCarritoVenta(DetallesVentaDTO detalle) {
        carritoVenta.remove(detalle);
    }
    
    /**
     * Suma el costo de todos los elementos del carrito
     * @return total del carrito
     */
    public double totalCarritoVenta() {
        return carritoVenta.stream().mapToDouble(DetallesVentaDTO::getSubtotal).sum();
    }
    
    /** Encapsula la lógica de limpiar el carrito */
    public void limpiarCarritoVenta() {
        carritoVenta.clear();
    }
    
    /**
     * Calcula el stock disponible de cierta pieza aún en medio proceso
     * de la venta. Sirve para validaciones rápidas y lógica de experiencia
     * de usuario. Aunque existan 20 piezas en la BD, si ya elegiste 10, y 
     * quieres otras 15, no podrás elegirlas
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