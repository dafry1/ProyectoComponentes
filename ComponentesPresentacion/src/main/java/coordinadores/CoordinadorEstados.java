package coordinadores;

import DTOS.PiezaDTO;
import java.util.ArrayList;
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
    
    
    //----- MÉTODOS DEL CARRITO -----//
    //Carrito actual
    private List<PiezaDTO> carrito = new ArrayList<>();
    
    /** Regresa las piezas del carrito de ventas */
    public List<PiezaDTO> getCarrito() {
        return carrito;
    }
    
    /** Agrega una pieza al carrito sin tocar directamente la referencia a la lista*/
    public void agregarPiezaCarrito(PiezaDTO pieza) {
        carrito.add(pieza);
    }
    
    /**
     * Suma el costo de todos los elementos del carrito
     * @return total del carrito
     */
    public double totalCarrito() {
        double total = 0.0;
        for (PiezaDTO p: carrito) {
            total += p.getCostoPieza();
        }
        return total; 
    }
    
    /** Limpia el carrito con una referencia nueva vacía*/
    public void limpiarCarrito() {
        carrito = new ArrayList<>();
    }
}