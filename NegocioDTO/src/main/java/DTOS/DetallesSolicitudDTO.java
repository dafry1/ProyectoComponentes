/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOS;

/**
 *
 * @author Andre
 */
public class DetallesSolicitudDTO extends DTO{
    private double costo;
    private int cantidad;
    private double subtotal;
    private PiezaDTO pieza;

    public DetallesSolicitudDTO() {
    }

    public DetallesSolicitudDTO(String id, double costo, int cantidad, double subtotal, PiezaDTO pieza) {
        super(id);
        this.costo = costo;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
        this.pieza = pieza;
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
