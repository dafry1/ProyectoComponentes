package daos;

import adaptadoresDoc.PiezaDoc;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import dominio.DetallesVenta;
import dominio.Pieza;
import dominio.Venta;
import excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;

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
    private MongoCollection<Document> ventas;
    
    /**
     * Constructor
     * 
     * @param coleccion 
     * @param ventas 
     */
    public PiezaDAO(MongoCollection<Pieza> coleccion, MongoCollection ventas) {
        this.coleccion = coleccion;
        this.ventas = ventas;
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
            Document docConId = PiezaDoc.toDocument(cascaron);
            Document filtro = new Document("_id", docConId.get("_id"));
            Document docResultado = coleccion.find(filtro, Document.class).first();
            return (docResultado != null) ? PiezaDoc.toEntity(docResultado) : null;

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
            lista.add(PiezaDoc.toEntity(doc));
        }
        return lista;
    }

    /**
     * Método genérico que centraliza la lógica de un aggregate para
     * obtener las piezas más vendidas en orden descendente
     * 
     * @param fechaBuscada
     * 
     * @return 
     */
    private List<Pieza> consultarTopPiezas(String fechaBuscada) {
        List<Pieza> topPiezas = new ArrayList<>();
        try {
            List<Bson> tuberia = Arrays.asList(
                // Filtro por fecha usando la expresión regular
                Aggregates.match(Filters.regex("fechaHora", fechaBuscada)),

                // Desenrolla el array de detalles
                Aggregates.unwind("$detalles"),

                // Agrupa reteniendo las propiedades mediante Accumulators.first()
                Aggregates.group("$detalles.pieza._id", 
                    Accumulators.sum("totalVendido", "$detalles.cantidad"),
                    Accumulators.first("nombre", "$detalles.pieza.nombre"),
                    Accumulators.first("categoria", "$detalles.pieza.categoria"),
                    Accumulators.first("marcaPieza", "$detalles.pieza.marcaPieza"),
                    Accumulators.first("modeloPieza", "$detalles.pieza.modeloPieza"),
                    Accumulators.first("costoPieza", "$detalles.pieza.costoPieza"),
                    Accumulators.first("stockPieza", "$detalles.pieza.stockPieza")
                ),

                // Ordena de mayor a menor éxito de ventas
                Aggregates.sort(Sorts.descending("totalVendido")),

                // Proyección limpia para tu adaptador
                Aggregates.project(Projections.fields(
                    Projections.computed("id", "$_id"), 
                    Projections.include("nombre", "categoria", "marcaPieza", "modeloPieza", "costoPieza", "stockPieza"),
                    Projections.excludeId()
                ))
            );

            AggregateIterable<Document> resultado = ventas.aggregate(tuberia, Document.class);
            
            //Mapea los resultados
            for (Document doc : resultado) {
                Pieza pieza = PiezaDoc.toEntity(doc); 
                if (pieza != null) {
                    topPiezas.add(pieza);
                }
            }
        } catch (Exception e) {
            LOG.log(System.Logger.Level.ERROR, "Error al consultar el top de piezas del día: " + e.getMessage());
            throw new PersistenciaException("No se pudo obtener el top de piezas diarias. Motivo: " + e.getMessage());
        }
        return topPiezas;
    }
    
    
    @Override
    public List<Pieza> consultarTopDiaPiezas() {
        java.time.format.DateTimeFormatter formateador = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaBuscada = java.time.LocalDate.now().format(formateador); 
        return consultarTopPiezas(fechaBuscada);
    }

    @Override
    public List<Pieza> consultarTopSemanaPiezas() {
        java.time.format.DateTimeFormatter formateador = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");
        java.time.LocalDate hoy = java.time.LocalDate.now();
        StringBuilder sb = new StringBuilder("^(");
        for (int i = 0; i < 7; i++) {
            sb.append(hoy.minusDays(i).format(formateador));
            if (i < 6) sb.append("|");
        }
        sb.append(")");
        return consultarTopPiezas(sb.toString());  
    }

    @Override
    public List<Pieza> consultarTopMesPiezas() {
        java.time.format.DateTimeFormatter formateador = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM");
        String mesBuscado = java.time.LocalDate.now().format(formateador); 
        return consultarTopPiezas("^" + mesBuscado);
    }

    @Override
    public List<Pieza> consultarTopTodoPiezas() {
        return consultarTopPiezas("");
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
        Document docPieza = PiezaDoc.toDocument(detalle.getPieza());
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
            lista.add(PiezaDoc.toEntity(doc));
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
                listaFiltrada.add(PiezaDoc.toEntity(doc));
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