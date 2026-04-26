package ensambladores;

import DTOS.ClienteDTO;
import DTOS.DetallesVentaDTO;
import DTOS.EmpleadoDTO;
import DTOS.PiezaDTO;
import DTOS.VentaDTO;
import java.util.List;

/**
 * Aplica el patrón ensamblador. A diferencia de una fábrica
 * que te dice "ten esto", al ensamblador le dices "ordéname
 * en una clase los datos que aquí te traigo". No aplica para
 * todos los DTO, naturalmente, sino para los que son creados
 * en presentación
 * 
 * @author Andre
 */
public class EnsambladorDTO implements IEnsambladorDTO {

    /**
     * Obtiene la información necesaria para crear el DTO
     * de un detalle de una venta de forma centralizada
     * 
     * @param cantidad de la piza en específico
     * @param pieza que está en el detalle
     * 
     * @return detalle listo
     */
    @Override
    public DetallesVentaDTO ensamblarDetalleVentaDTO(int cantidad, PiezaDTO pieza) {
        double costo = pieza.getCostoPieza();
        DetallesVentaDTO detalle = new DetallesVentaDTO();
        detalle.setCantidad(cantidad);
        detalle.setCosto(costo);
        detalle.setPieza(pieza);
        detalle.setSubtotal(costo*cantidad);
        return detalle;
    }

    /**
     * Asigna atributos básicos de una venta, para que después sea
     * mandada a Negocio para terminar su empaquetado (folio,
     * fecha y hora)
     * 
     * @param empleado que realizó la venta
     * @param carrito con los detalles de la venta
     * @param cliente que compró los productos
     * 
     * @return la venta lista para procesarse en negocio
     */
    @Override
    public VentaDTO ensamblarVentaDTO(ClienteDTO cliente, EmpleadoDTO empleado, List<DetallesVentaDTO> carrito) {
        VentaDTO venta = new VentaDTO();
        venta.setDetalles(carrito);
        venta.setEmpleado(empleado);
        venta.setCliente(cliente);
        return venta;
    }
    
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
    @Override
    public ClienteDTO ensamblarClienteDTO(String nombres, String apellidoPaterno, String apellidoMaterno, String correo, String telefono) {
        ClienteDTO cliente = new ClienteDTO();
        cliente.setNombres(nombres);
        cliente.setApellidoPaterno(apellidoPaterno);
        cliente.setApellidoMaterno(apellidoMaterno);
        cliente.setCorreo(correo);
        cliente.setTelefono(telefono);
        return cliente;
    }
}