package coordinadores;

import DTOS.DetallesVentaDTO;
import DTOS.EmpleadoDTO;
import DTOS.PiezaDTO;
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
     * Orquesta todos los métodos necesarios para llevar a
     * cabo una venta dentro del sistema. Actualiza stock,
     * limpia el carrito, etc.
     * 
     * @param coordinadorEstados con las piezas a vender
     * @param observador si se necesita actualizar algo. Puede ser null
     * 
     * @return la venta procesadas
     */
    VentaDTO procesarVenta(ICoordinadorEstados coordinadorEstados, IObservador observador);
    
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
}