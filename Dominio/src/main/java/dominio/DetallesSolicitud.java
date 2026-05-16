/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

/**
 *
 * @author Andre
 */
public class DetallesSolicitud {
    private double costo;
    private int cantidad;
    private double subtotal;
    private Pieza pieza;

    public DetallesSolicitud() {
    }

    public DetallesSolicitud(double costo, int cantidad, double subtotal, Pieza pieza) {
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
