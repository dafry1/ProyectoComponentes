package DTOS;

import java.util.List;

/**
 * Solicitud. Una venta con pasos extra, pues también
 * involucra piezas, pero de forma más libre (sin stock
 * interno), así como manejando fechas y la dirección
 * Aplica el patrón builder al ya tener manejar varios
 * atributos
 * 
 * @author Andre
 */
public class SolicitudDTO extends VentaDTO {
    
    //Atributos
    String fechaHoraPedido;
    String fechaLlegada;
    String estado;
    DireccionDTO direccion;

    public SolicitudDTO() {
    }

    public SolicitudDTO(String fechaHoraPedido, String fechaLlegada, String estado, DireccionDTO direccion, Long id, EmpleadoDTO empleado, ClienteDTO cliente, List<DetallesVentaDTO> detalles, double total, String fechaHora, String folio) {
        super(id, empleado, cliente, detalles, total, fechaHora, folio);
        this.fechaHoraPedido = fechaHoraPedido;
        this.fechaLlegada = fechaLlegada;
        this.estado = estado;
        this.direccion = direccion;
    }
    
    public SolicitudDTO build() {
        return new SolicitudDTO();
    }

    public String getFechaHoraPedido() {
        return fechaHoraPedido;
    }

    public void setFechaHoraPedido(String fechaHoraPedido) {
        this.fechaHoraPedido = fechaHoraPedido;
    }

    public String getFechaLlegada() {
        return fechaLlegada;
    }

    public void setFechaLlegada(String fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public DireccionDTO getDireccion() {
        return direccion;
    }

    public void setDireccion(DireccionDTO direccion) {
        this.direccion = direccion;
    }
}