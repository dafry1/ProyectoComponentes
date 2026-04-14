package Entidades;

import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Andre
 */
public class Venta {
    
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column(name = "id_venta")
    private Long id;
    
    // @ManyToOne
    // @JoinColumn(name = "venta_id")
    private List<DetallesVenta> detalles;
    
    // @ManyToOne
    // @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    // @Column(name = "id_venta")
    private int totalArticulos;
    
    // @Column(name = "folio", nullable = false, length = 100)
    private String folio;
    
    // @Column(name = "fecha_hora_registro", nullable = false)
    private LocalDateTime fechaHoraRegistro;

    public Long getId() {
        return id;
    }
    
    //Getters y setters
    public void setId(Long id) {
        this.id = id;
    }

    public List<DetallesVenta> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallesVenta> detalles) {
        this.detalles = detalles;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getTotalArticulos() {
        return totalArticulos;
    }

    public void setTotalArticulos(int totalArticulos) {
        this.totalArticulos = totalArticulos;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public LocalDateTime getFechaHoraRegistro() {
        return fechaHoraRegistro;
    }

    public void setFechaHoraRegistro(LocalDateTime fechaHoraRegistro) {
        this.fechaHoraRegistro = fechaHoraRegistro;
    }
}