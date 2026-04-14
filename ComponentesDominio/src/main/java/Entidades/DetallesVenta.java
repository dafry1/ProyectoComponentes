package Entidades;

/**
 * Por ahora sin persistencia alguna
 * 
 * @author Andre
 */
public class DetallesVenta {
    
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column(name = "id_detalle")
    private Long id;
    
    // @Column(name = "cantidad")
    private int cantidad;
    
    // @ManyToOne
    // @JoinColumn(name = "venta_id")
    private Venta venta;
    
    //Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
