package dominio;

import java.util.Objects;

/**
 * Representación de una pieza en la BD
 * 
 * @author Andre
 */
public class Pieza {
    private String nombre;
    private String categoria;
    private String marcaPieza;
    private String modeloPieza;
    private double costoPieza;
    private int stockPieza;
    private String id;
    
    private double antesImpuestos;
    
    private static final double IVA = 1.16;
 
    public Pieza(){}
    
    public Pieza(String id, String nombre, String categoria, String marcaPieza, String modeloPieza, double costoPieza, int stockPieza) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.marcaPieza = marcaPieza;
        this.modeloPieza = modeloPieza;
        this.costoPieza = costoPieza;
        this.stockPieza = stockPieza;
        this.antesImpuestos = costoPieza/IVA;
    }

    public Pieza(String nombre, String categoria, String marcaPieza, String modeloPieza, double costoPieza, int stockPieza) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.marcaPieza = marcaPieza;
        this.modeloPieza = modeloPieza;
        this.costoPieza = costoPieza;
        this.stockPieza = stockPieza;
    }
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public double getAntesImpuestos() {
        return antesImpuestos;
    }

    public void setAntesImpuestos(double antesImpuestos) {
        this.antesImpuestos = antesImpuestos;
    }
    
    
    
    @Override
    public boolean equals(Object o) {
        // 1. Verificación básica de referencia
        if (this == o) return true;
        
        // 2. Verificación de nulidad y clase
        if (o == null || getClass() != o.getClass()) return false;
        
        // 3. Comparación por ID
        Pieza pieza = (Pieza) o;
        return Objects.equals(id, pieza.id);
    }

    @Override
    public int hashCode() {
        // Genera el hash basado en el ID
        return Objects.hash(id);
    }
}
