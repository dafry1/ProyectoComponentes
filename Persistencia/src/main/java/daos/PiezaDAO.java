package daos;

import adaptadoresDoc.PiezaDoc;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dominio.DetallesVenta;
import dominio.Pieza;
import excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 * Objeto que habla con la BD para las piezas
 * 
 * @author Andre
 */
public class PiezaDAO implements IPiezaDAO {
    private static final System.Logger LOG = System.getLogger(PiezaDAO.class.getName());
    private static String CARRITO_VACIO = "No se puede procesar una venta con un carrito vacío";
    private static final String NO_FILTRADO = " inválido para filtrar piezas";
    
    //Colección mongo
    private MongoCollection<Pieza> coleccion;
    
    
    private static final PiezaDoc adaptadorPieza = PiezaDoc.singleton();
    
    
   
    
    /**
     * Constructor
     * 
     * @param coleccion 
     */
    public PiezaDAO(MongoCollection coleccion) {
        this.coleccion = coleccion;
    }
    
    //MEDIDAS TEMPORALES PARA MOCKEO
    private static List<Pieza> PIEZAS = new ArrayList<>();
    static {
        PIEZAS.add(new Pieza("Ryzen 5 9600X", "Procesador", "AMD", "Zen 5", 2500.0, 50));
        PIEZAS.add(new Pieza("Core i9-14900K", "Procesador", "Intel", "Raptor Lake", 5500.0, 40));
        PIEZAS.add(new Pieza("Trident Z5 RGB", "RAM", "G.Skill", "DDR5-6400", 1800.0, 30));
        PIEZAS.add(new Pieza("Vengeance RGB Frosted", "RAM", "Corsair", "DDR5-6000", 2100.0, 25));
        PIEZAS.add(new Pieza("GeForce RTX 4090", "Tarjeta Gráfica", "NVIDIA", "Founders Edition", 35000.0, 10));
        PIEZAS.add(new Pieza("Radeon RX 7800 XT", "Tarjeta Gráfica", "AMD", "Steel Legend", 9800.0, 15));
    }
    
    @Override
    public Pieza consultarPieza(String id) {
        if (id == null || id.trim().isEmpty()) {
            LOG.log(System.Logger.Level.WARNING, "Se intentó consultar una pieza con ID nulo o vacío");
            return null;
        }
        try {
            Pieza cascaron = new Pieza();
            cascaron.setId(id);
            Document docConId = adaptadorPieza.toDocument(cascaron);
            Document filtro = new Document("_id", docConId.get("_id"));
            Document docResultado = coleccion.find(filtro, Document.class).first();
            return (docResultado != null) ? adaptadorPieza.toEntity(docResultado) : null;

        } catch (Exception e) {
            LOG.log(System.Logger.Level.ERROR, "Error al consultar pieza: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Consulta todas las piezas de la BD
     * 
     * @return 
     */
    @Override
    public List<Pieza> consultarPiezas() {
        List<Pieza> lista = new ArrayList<>();
        for (Document doc : coleccion.find(new Document(), Document.class)) {
            lista.add(adaptadorPieza.toEntity(doc));
        }
        return lista;
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

    /**
     * Actualiza el stock, convierte la pieza en Doc y obtiene la
     * cantidad del detalle y la hace negativa para restar a la 
     * pieza
     * 
     * @param detalle 
     */
    @Override
    public void actualizarStock(DetallesVenta detalle) {
        Document docPieza = adaptadorPieza.toDocument(detalle.getPieza());
        if (docPieza == null || !docPieza.containsKey("_id")) {
            LOG.log(System.Logger.Level.ERROR, "La pieza no tiene un ID válido para actualizar stock");
            throw new PersistenciaException("ID de pieza faltante");
        }
        try {
            Document filtro = new Document("_id", docPieza.get("_id"));
            int cantidadARestar = -detalle.getCantidad();
            Document actualizacion = new Document("$inc", new Document("stockPieza", cantidadARestar));
            var resultado = coleccion.updateOne(filtro, actualizacion);
            if (resultado.getModifiedCount() == 0) {
                 LOG.log(System.Logger.Level.WARNING, "No se modificó el stock");
            }
        } catch (Exception e) {
            LOG.log(System.Logger.Level.ERROR, "Error en MongoDB: " + e.getMessage());
            throw new PersistenciaException("Error al actualizar stock");
        }
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
        return filtrar("nombre", nombre);
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
        return filtrar("categoria", categoria);
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
        return filtrar("marcaPieza", marca);
    }
    
    /**
     * Consulta todas las piezas cuyo modelo
     * coincida con el campo
     * 
     * @param marca para filtrar
     * 
     * @return piezas filtradas 
     */
    @Override
    public List<Pieza> filtrarPorModelo(String marca) {
        return filtrar("modeloPieza", marca);
    }
    
    /**
     * Auxiliar que centraliza la forma de filtrar
     * piezas por cierto campo
     * 
     * @param campo
     * @param valorFiltro
     * 
     * @return la lista filtrada
     */
    private List<Pieza> filtrar(String campo, String valorFiltro) {
        if (valorFiltro == null || valorFiltro.isBlank()) {
            String msj = "Valor del filtro vacío";
            LOG.log(System.Logger.Level.ERROR, msj);
            throw new PersistenciaException(msj);
        }
        List<Pieza> lista = new ArrayList<>();
        Document filtro = new Document(campo, new Document("$regex", valorFiltro).append("$options", "i"));
        for (Document doc : coleccion.find(filtro, Document.class)) {
            lista.add(adaptadorPieza.toEntity(doc));
        }
        return lista;
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
        List<Pieza> listaFiltrada = new ArrayList<>();
        Document filtro = new Document("costoPieza", new Document("$lte", precioMaximo));
        try { for (Document doc : coleccion.find(filtro, Document.class)) {
                listaFiltrada.add(adaptadorPieza.toEntity(doc));
            }
        } catch (Exception e) {
            LOG.log(System.Logger.Level.ERROR, "Error al filtrar por precio: " + e.getMessage());
            throw new PersistenciaException("No se pudieron filtrar las piezas por precio.");
        }
        return listaFiltrada;
    }
    
    @Override
    public void insertar(Pieza pieza) {
        try {
            coleccion.insertOne(pieza);
        } catch (Exception e) {
            throw new PersistenciaException(e.getMessage());
        }
    }
}