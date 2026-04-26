package DTOS;

/**
 * DTO que representa una parte de un pedido de una venta.
 * Se ha automatizado el cálculo del subtotal para facilitar la edición en UI.
 * * @author Andre
 */
public class DetallesVentaDTO extends DTO {
    
    private double costo;
    private int cantidad;
    private double subtotal;
    private PiezaDTO pieza;

    public DetallesVentaDTO(){}
    
    public DetallesVentaDTO(Long id, double costo, int cantidad, double subtotal, PiezaDTO pieza) {
        super(id);
        this.costo = costo;
        this.cantidad = cantidad;
        this.pieza = pieza;
        recalcularSubtotal();
    }

    /**
     * Calcula el subtotal basado en costo y cantidad actual
     */
    private void recalcularSubtotal() {
        this.subtotal = this.costo * this.cantidad;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
        recalcularSubtotal(); 
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        recalcularSubtotal(); 
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
        if(pieza != null && this.costo == 0) {
            this.costo = pieza.getCostoPieza();
            recalcularSubtotal();
        }
    }
}