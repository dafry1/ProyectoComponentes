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
public class SolicitudDTO extends DTO {
    
    //Atributos
    private String fechaEntrega;
    private String fechaEntregaEstimada;
    private String estado;
    private String direccion;
    private ClienteDTO cliente;
    private EmpleadoDTO empleado;
    private List<DetallesSolicitudDTO> detalles;
    private double total;
    private String fechaHora;
    private String folio;
    
    public SolicitudDTO() {
    }

    public SolicitudDTO(String fechaEntrega, String fechaEntregaEstimada, String estado, String direccion, ClienteDTO cliente, EmpleadoDTO empleado, List<DetallesSolicitudDTO> detalles, double total, String fechaHora, String folio) {
        this.fechaEntrega = fechaEntrega;
        this.fechaEntregaEstimada = fechaEntregaEstimada;
        this.estado = estado;
        this.direccion = direccion;
        this.cliente = cliente;
        this.empleado = empleado;
        this.detalles = detalles;
        this.total = total;
        this.fechaHora = fechaHora;
        this.folio = folio;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public EmpleadoDTO getEmpleado() {
        return empleado;
    }

    public void setEmpleado(EmpleadoDTO empleado) {
        this.empleado = empleado;
    }

    public List<DetallesSolicitudDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallesSolicitudDTO> detalles) {
        this.detalles = detalles;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
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