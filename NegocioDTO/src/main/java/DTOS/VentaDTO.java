package DTOS;

import java.util.List;

/**
 * DTO de una venta que guarda los detalles, fecha
 * y hora, el total, etc
 *
 * @author Andre
 */
public class VentaDTO extends DTO {
    
    //Atributos
    private ClienteDTO cliente;
    private EmpleadoDTO empleado;
    private List<DetallesVentaDTO> detalles;
    private double total;
    private String fechaHora;
    private String folio;

    /** Constructor vacío */
    public VentaDTO() {}  

    /**
     * Constructor básico para presentación
     * 
     * @param cliente
     * @param empleado
     * @param detalles 
     */
    public VentaDTO(ClienteDTO cliente, EmpleadoDTO empleado, List<DetallesVentaDTO> detalles) {
        this.cliente = cliente;
        this.empleado = empleado;
        this.detalles = detalles;
    }
    
    /**
     * Constructor completo
     * 
     * @param id
     * @param empleado
     * @param cliente
     * @param detalles
     * @param total
     * @param fechaHora
     * @param folio 
     */
    public VentaDTO(Long id, EmpleadoDTO empleado, ClienteDTO cliente, List<DetallesVentaDTO> detalles, double total, String fechaHora, String folio) {
        super(id);
        this.cliente = cliente;
        this.detalles = detalles;
        this.total = total;
        this.fechaHora = fechaHora;
        this.folio = folio;
        this.empleado = empleado;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    public List<DetallesVentaDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallesVentaDTO> detalles) {
        this.detalles = detalles;
    }

    public double getTotal() {
        double total = 0;
        for (DetallesVentaDTO detalle: detalles) {
            total += detalle.getSubtotal();
        }
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

    public EmpleadoDTO getEmpleado() {
        return empleado;
    }

    public void setEmpleado(EmpleadoDTO empleado) {
        this.empleado = empleado;
    }
}