package Entidades;

/**
 * Por ahora no tiene persistencia alguna
 * 
 * @author Andre
 */
public class Pieza {
    
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column(name = "id_pieza")
    private Long id;

    // @Column(name = "costo", nullable = false)
    private double costo;
    
    // @Column(name = "marca", nullable = false, length = 100)
    private String marca;
    
    // @Column(name = "modelo", nullable = false, length = 100)
    private String modelo;
    
    // @Column(name = "stock", nullable = false)
    private int stock;

    //Constructores
    public Pieza() {}
    public Pieza(double costo, String marca, String modelo) {
        this.costo = costo;
        this.marca = marca;
        this.modelo = modelo;
    }
    public Pieza(Long id, double costo, String marca, String modelo) {
        this.id = id;
        this.costo = costo;
        this.marca = marca;
        this.modelo = modelo;
    }
    
    //Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }
}