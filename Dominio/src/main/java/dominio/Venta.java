package dominio;

import java.util.List;

/**
 *
 * @author Andre
 */
public class Venta {
    private Cliente cliente;
    private Empleado empleado;
    private List<DetallesVenta> detalles;
    private double total;
    private String fechaHora;
    private String folio;
    private String id;
    
    private boolean facturada = false;
    private double antesImpuestos;

    public Venta() {
    }

    public Venta(Empleado empleado, Cliente cliente, List<DetallesVenta> detalles, double total, String fechaHora, String folio) {
        this.cliente = cliente;
        this.detalles = detalles;
        this.total = total;
        this.fechaHora = fechaHora;
        this.folio = folio;
        this.empleado = empleado;
    }

    public Venta(Cliente cliente, Empleado empleado, List<DetallesVenta> detalles, double total, String fechaHora, String folio, String id) {
        this.cliente = cliente;
        this.empleado = empleado;
        this.detalles = detalles;
        this.total = total;
        this.fechaHora = fechaHora;
        this.folio = folio;
        this.id = id;
    }
    
    

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
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

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    } 

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isFacturada() {
        return facturada;
    }

    public void setFacturada(boolean facturada) {
        this.facturada = facturada;
    }

    public double getAntesImpuestos() {
        return antesImpuestos;
    }

    public void setAntesImpuestos(double antesImpuestos) {
        this.antesImpuestos = antesImpuestos;
    }
}
