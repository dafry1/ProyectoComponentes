package DTOS;

import interfaces.IDTO;

/**
 * PiezaDTO.java
 * 
 * Clase que representa la información de las piezas con las que se
 * interactua en el programa.
 * 
 * @author Aaron
 */
public class PiezaDTO implements IDTO {
    private Long id;
    private String nombre;
    private String categoria;
    private String marcaPieza;
    private String modeloPieza;
    private double costoPieza;
    private int stockPieza;
    
    /**
     * Constructor vacio de la clase PiezaDTO.
     */
    public PiezaDTO(){}
    public PiezaDTO(Long id, String nombre, String categoria, String marcaPieza, String modeloPieza, double costoPieza, int stockPieza) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.marcaPieza = marcaPieza;
        this.modeloPieza = modeloPieza;
        this.costoPieza = costoPieza;
        this.stockPieza = stockPieza;
    }
    
    //CONSTRUCTOR BASICO TEMPORAL
    public PiezaDTO(String nombre, String categoria, String marcaPieza, double costoPieza) {
        this.nombre = nombre;
        this.categoria = categoria;
        this.marcaPieza = marcaPieza;
        this.costoPieza = costoPieza;
    }
    
    /** Implementación de nombreDTO() */
    @Override
    public String nombreDTO() {
        return "Pieza";
    }

    /** Implementación de mostrarInfo() */
    @Override
    public String mostrarInfo() {
        return "Información de la pieza: \n"
             + "Nombre: " + nombre + "\n"
             + "Categoría: " + categoria + "\n"
             + "Marca: " + marcaPieza + "\n"
             + "Modelo: " + modeloPieza + "\n"
             + "Precio: " + costoPieza + "\n"
             + "Stock restante: " + stockPieza + "\n";
    }
    
    /**
     * Método para obtener el valor del ID del pieza.
     * 
     * @return Valor del ID del pieza.
     */
    public Long getID(){
        return id;
    }
    
    /**
     * Método para asignar el valor del ID al pieza.
     * 
     * @param id Valor del ID a asignar al pieza.
     */
    public void setID(Long id){
        this.id = id;
    }
    
    /**
     * Método para obtener el valor del tipo de la pieza.
     * 
     * @return Valor del tipo de la pieza.
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Método para asignar el valor del tipo a la pieza.
     * 
     * @param categoria Valor del tipo a asignar a la pieza.
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    /**
     * Método para obtener el valor del costo de la pieza.
     * 
     * @return Valor del costo de la pieza.
     */
    public double getCostoPieza() {
        return costoPieza;
    }

    /**
     * Método para asignar el valor del costo a la pieza.
     * 
     * @param costoPieza Valor del costo a asignar a la pieza.
     */
    public void setCostoPieza(double costoPieza) {
        this.costoPieza = costoPieza;
    }

    /**
     * Método para obtener el valor de la marca de la pieza.
     * 
     * @return Valor de la marca de la pieza.
     */
    public String getMarcaPieza() {
        return marcaPieza;
    }

    /**
     * Método para asignar el valor de la marca a la pieza.
     * 
     * @param marcaPieza Valor de la marca a asignar a la pieza.
     */
    public void setMarcaPieza(String marcaPieza) {
        this.marcaPieza = marcaPieza;
    }

    /**
     * Método para obtener el valor del modelo de la pieza.
     * 
     * @return Valor del modelo de la pieza.
     */
    public String getModeloPieza() {
        return modeloPieza;
    }

    /**
     * Método para asignar el valor del modelo a la pieza.
     * 
     * @param modeloPieza Valor del modelo a asignar a la pieza.
     */
    public void setModeloPieza(String modeloPieza) {
        this.modeloPieza = modeloPieza;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getStockPieza() {
        return stockPieza;
    }

    public void setStockPieza(int stockPieza) {
        this.stockPieza = stockPieza;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}