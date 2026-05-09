package DTO;

/**
 *
 * @author Andre
 */
public class PiezaInfraestructuraDTO {
    private String nombre;
    private String categoria;
    private String marcaPieza;
    private String modeloPieza;
    private double costoPieza;
    private int stockPieza;

    public PiezaInfraestructuraDTO(String nombre, String categoria, String marcaPieza, String modeloPieza, double costoPieza, int stockPieza) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.marcaPieza = marcaPieza;
        this.modeloPieza = modeloPieza;
        this.costoPieza = costoPieza;
        this.stockPieza = stockPieza;
    }

    public PiezaInfraestructuraDTO() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getMarcaPieza() {
        return marcaPieza;
    }

    public void setMarcaPieza(String marcaPieza) {
        this.marcaPieza = marcaPieza;
    }

    public String getModeloPieza() {
        return modeloPieza;
    }

    public void setModeloPieza(String modeloPieza) {
        this.modeloPieza = modeloPieza;
    }

    public double getCostoPieza() {
        return costoPieza;
    }

    public void setCostoPieza(double costoPieza) {
        this.costoPieza = costoPieza;
    }

    public int getStockPieza() {
        return stockPieza;
    }

    public void setStockPieza(int stockPieza) {
        this.stockPieza = stockPieza;
    }
    
    
}
