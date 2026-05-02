package daos;

import dominio.DetallesVenta;
import dominio.Pieza;
import excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;

/**
 * Objeto que habla con la BD para las piezas
 * 
 * @author Andre
 */
public class PiezaDAO implements IPiezaDAO {
    private static final System.Logger LOG = System.getLogger(PiezaDAO.class.getName());
    private static String CARRITO_VACIO = "No se puede procesar una venta con un carrito vacío";
    private static final String NO_FILTRADO = " inválido para filtrar piezas";
    
    //MEDIDAS TEMPORALES PARA MOCKEO
    private static List<Pieza> PIEZAS = new ArrayList<>();
    static {
        Pieza pieza1 = new Pieza();
        pieza1.setId(1L);
        pieza1.setNombre("Ryzen 5 9600X");
        pieza1.setCategoria("Procesador");
        pieza1.setMarcaPieza("AMD");
        pieza1.setModeloPieza("Zen 5");
        pieza1.setCostoPieza(2500.0);
        pieza1.setStockPieza(50);
        PIEZAS.add(pieza1);
        
        Pieza pieza2 = new Pieza();
        pieza2.setId(2L);
        pieza2.setNombre("Core i9-14900K");
        pieza2.setCategoria("Procesador");
        pieza2.setMarcaPieza("Intel");
        pieza2.setModeloPieza("Raptor Lake");
        pieza2.setCostoPieza(5500.0);
        pieza2.setStockPieza(40);
        PIEZAS.add(pieza2);
        
        Pieza pieza3 = new Pieza();
        pieza3.setId(3L);
        pieza3.setNombre("Trident Z5 RGB");
        pieza3.setCategoria("RAM");
        pieza3.setMarcaPieza("G.Skill");
        pieza3.setModeloPieza("DDR5-6400");
        pieza3.setCostoPieza(1800.0);
        pieza3.setStockPieza(30);
        PIEZAS.add(pieza3);
    }
    
    /**
     * Consulta una piez por su Id
     * 
     * @param id
     * @return 
     */
    @Override
    public Pieza consultarPieza(Long id) {
        for (Pieza p: PIEZAS) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }
    
    @Override
    public List<Pieza> consultarPiezas() {
        return PIEZAS;
    }

    @Override
    public List<Pieza> consultarTopDiaPiezas() {
        return PIEZAS;
    }

    @Override
    public List<Pieza> consultarTopSemanaPiezas() {
        return PIEZAS;
    }

    @Override
    public List<Pieza> consultarTopMesPiezas() {
        return PIEZAS;
    }

    @Override
    public List<Pieza> consultarTopTodoPiezas() {
        return PIEZAS;
    }

    @Override
    public void actualizarStock(DetallesVenta detalle) {
        for (Pieza pieza: PIEZAS) {
            
            if (pieza.equals(detalle.getPieza())) {
                int stockActual = pieza.getStockPieza();
                int cantidadVendida = detalle.getCantidad();
                
                if (stockActual >= cantidadVendida) {
                    pieza.setStockPieza(stockActual - cantidadVendida);
                    LOG.log(System.Logger.Level.DEBUG, "Stock actualizado: " + pieza.getNombre() + " ahora tiene " + pieza.getStockPieza());
                } else {
                    LOG.log(System.Logger.Level.ERROR, CARRITO_VACIO);
                }
                return;
            }
        }
;
    }

    @Override
    public void actualizarStockTrasVenta(List<DetallesVenta> detalles) {
        //Excepción si la lista está vacía o es null
        if (detalles == null || detalles.isEmpty()) {
            LOG.log(System.Logger.Level.ERROR, CARRITO_VACIO);
            throw new PersistenciaException(CARRITO_VACIO);
        }
        
        //Hace la iteración propiamente dicha
        for (DetallesVenta detalle: detalles) {
            actualizarStock(detalle);
        }
    }

    /**
     * Consulta todas las piezas cuyo nombre
     * coincida con el campo
     * 
     * @param nombre para filtrar
     * 
     * @return piezas filtradas 
     */
    @Override
    public List<Pieza> filtrarPorNombre(String nombre) {
        if (nombre.isBlank()) {
            String DEBUG = "Nombre " + NO_FILTRADO;
            LOG.log(System.Logger.Level.ERROR, DEBUG);
            throw new PersistenciaException(DEBUG);
        }
        return PIEZAS.stream().filter(p -> p.getNombre().toLowerCase()
                                .contains(nombre.toLowerCase()))
                                .toList();
    }

    /**
     * Consulta todas las piezas cuya categoria
     * coincida con el campo
     * 
     * @param categoria para filtrar
     * 
     * @return piezas filtradas 
     */
    @Override
    public List<Pieza> filtrarPorCategoria(String categoria) {
        if (categoria.isBlank()) {
            String DEBUG = "Categoría " + NO_FILTRADO;
            LOG.log(System.Logger.Level.ERROR, DEBUG);
            throw new PersistenciaException(DEBUG);
        }
        return PIEZAS.stream().filter(p -> p.getCategoria().toLowerCase()
                                .contains(categoria.toLowerCase()))
                                .toList();
    }

    /**
     * Consulta todas las piezas cuya marca
     * coincida con el campo
     * 
     * @param marca para filtrar
     * 
     * @return piezas filtradas 
     */
    @Override
    public List<Pieza> filtrarPorMarca(String marca) {
        if (marca.isBlank()) {
            String DEBUG = "Marca " + NO_FILTRADO;
            LOG.log(System.Logger.Level.ERROR, DEBUG);
            throw new PersistenciaException(DEBUG);
        }
        return PIEZAS.stream().filter(p -> p.getMarcaPieza().toLowerCase()
                                .contains(marca.toLowerCase()))
                                .toList();
    }

    /**
     * Consulta todas las piezas cuyo precio
     * coincida con el máximo
     * 
     * @param precioMaximo para filtrar
     * 
     * @return piezas filtradas 
     */
    @Override
    public List<Pieza> filtrarPorPrecioMax(double precioMaximo) {
        return PIEZAS.stream().filter(p -> p.getCostoPieza() <= precioMaximo).toList();
    }
}