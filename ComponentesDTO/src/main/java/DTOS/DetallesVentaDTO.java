package DTOS;

//SUBETE

/**
 * DTO que representa una parte de un pedido de una venta
 * 
 * @author Andre
 */
public class DetallesVentaDTO extends DTO {
    
    //Atributos
    private double costo;
    private int cantidad;
    private double subtotal;
    private PiezaDTO pieza;

    //Constructores
    public DetallesVentaDTO(){}
    public DetallesVentaDTO(Long id, double costo, int cantidad, double subtotal, PiezaDTO pieza) {
        super(id);
        this.costo = costo;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.pieza = pieza;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public PiezaDTO getPieza() {
        return pieza;
    }

    public void setPieza(PiezaDTO pieza) {
        this.pieza = pieza;
    }
}