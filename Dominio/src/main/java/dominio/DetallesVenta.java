package dominio;

/**
 * Representación del detalle de una venta en
 * la BD. Está embebida a una Venta
 * 
 * @author Andre
 */
public class DetallesVenta {
    private double costo;
    private int cantidad;
    private double subtotal;
    private Pieza pieza;

    public DetallesVenta() {}

    public DetallesVenta(double costo, int cantidad, double subtotal, Pieza pieza) {
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

    public Pieza getPieza() {
        return pieza;
    }

    public void setPieza(Pieza pieza) {
        this.pieza = pieza;
    }
    
    
}
