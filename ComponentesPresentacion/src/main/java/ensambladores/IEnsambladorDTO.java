package ensambladores;

import DTOS.ClienteDTO;
import DTOS.DetallesVentaDTO;
import DTOS.EmpleadoDTO;
import DTOS.PiezaDTO;
import DTOS.VentaDTO;
import java.util.List;

/**
 * Establece el contrato para un ensamblador
 * de DTOs. Este ensamblador evitará ensuciar
 * a presentación eso de setters
 * 
 * @author Andre
 */
public interface IEnsambladorDTO {
    
    /**
     * Obtiene la información necesaria para crear el DTO
     * de un detalle de una venta de forma centralizada
     * 
     * @param cantidad de la piza en específico
     * @param pieza que está en el detalle
     * 
     * @return DTO listo
     */
    DetallesVentaDTO ensamblarDetalleVentaDTO(int cantidad, PiezaDTO pieza);
    
    /**
     * Asigna atributos básicos de una venta, para que después sea
     * mandada a Negocio para terminar su empaquetado (folio,
     * fecha y hora)
     * 
     * @param cliente
     * @param empleado que realizó la venta
     * @param carrito con los detalles de la venta
     * 
     * @return la venta lista para procesarse en negocio
     */
    VentaDTO ensamblarVentaDTO(ClienteDTO cliente ,EmpleadoDTO empleado, List<DetallesVentaDTO> carrito);
    
    /**
     * Obtiene información necesaria para crear el DTO de
     * un cliente para la venta. No es una entidad independiente
     * ni se guarda, solo es puntual, y realmente no se administran
     * clientes dentro del sistema
     * 
     * @param nombres
     * @param apellidoPaterno
     * @param apellidoMaterno
     * @param correo
     * @param telefono
     * 
     * @return el clienteDTO ensamblado
     */
    ClienteDTO ensamblarClienteDTO(String nombres, String apellidoPaterno, String apellidoMaterno, String correo, String telefono);
}