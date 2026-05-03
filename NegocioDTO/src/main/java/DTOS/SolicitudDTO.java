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
    String fechaEntrega;
    String fechaEntregaEstimada;
    String estado;
    String direccion;

    public SolicitudDTO() {
    }

    public SolicitudDTO(String fechaEntrega, String fechaEntregaEstimada, String estado, String direccion, Long id, EmpleadoDTO empleado, ClienteDTO cliente, List<DetallesVentaDTO> detalles, double total, String fechaHora, String folio) {
        super(id, empleado, cliente, detalles, total, fechaHora, folio);
        this.fechaEntrega = fechaEntrega;
        this.fechaEntregaEstimada = fechaEntregaEstimada;
        this.estado = estado;
        this.direccion = direccion;
    }

    public String getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getFechaEntregaEstimada() {
        return fechaEntregaEstimada;
    }

    public void setFechaEntregaEstimada(String fechaEntregaEstimada) {
        this.fechaEntregaEstimada = fechaEntregaEstimada;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}