package Entidades;

import java.util.List;

/**
 * Por ahora no tiene persistencia alguna
 * 
 * @author Andre
 */
// @Entity
// @Table(name = "clientes")
public class Cliente {
    
    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Column(name = "id_cliente")
    private Long id;
    
    // @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;
    
    // @Column(name = "apellido_paterno", nullable = false, length = 100)
    private String apellidoPaterno;
    
    // @Column(name = "apellido_materno", nullable = false, length = 100)
    private String apellidoMaterno;
    
    // @OneToMany(mappedBy = "cliente")
    private List<Venta> ventas;
}