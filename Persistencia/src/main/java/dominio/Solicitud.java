package dominio;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Entidad que representa una solicitud de pedido en el sistema de retail.
 * Incluye información logística sobre la entrega y el personal involucrado.
 * 
 * @author Andre
 */
public class Solicitud {
    private Cliente cliente;
    private Empleado empleado;
    private List<DetallesVenta> detalles; 
    private double total;
    private String fechaHora;
    private String folio;
    
    // Atributos logísticos solicitados
    private String fechaEntrega;
    private String fechaEntregaEstimada;
    private String direccion;

    public Solicitud() {
    }

    public Solicitud(Empleado empleado, Cliente cliente, List<DetallesVenta> detalles, 
                     double total, String fechaHora, String folio, 
                     String fechaEntrega, String fechaEntregaEstimada, String direccion) {
        this.cliente = cliente;
        this.empleado = empleado;
        this.detalles = detalles;
        this.total = total;
        this.fechaHora = fechaHora;
        this.folio = folio;
        this.fechaEntrega = fechaEntrega;
        this.fechaEntregaEstimada = fechaEntregaEstimada;
        this.direccion = direccion;
    }

    // Getters y Setters
    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public List<DetallesVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallesVenta> detalles) {
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}