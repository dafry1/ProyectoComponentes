/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package daos;

import adaptadoresDoc.PiezaDoc;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dominio.DetallesVenta;
import dominio.Pieza;
import dominio.Venta;
import java.util.Arrays;
import java.util.List;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas de integración reales para PiezaDAO usando la base de datos componentes.
 * 
 * @author Andre
 */
public class PiezaDAOTest {
    
    private static MongoClient mongoClient;
    private static MongoDatabase database;
    
    private MongoCollection<Pieza> coleccionPiezas;
    private MongoCollection<Venta> coleccionVentas;
    private MongoCollection<Document> piezasDocCollection;
    
    private PiezaDAO piezaDAO;
    
    public PiezaDAOTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        mongoClient = MongoClients.create("mongodb://localhost:27017");

        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
                mongoClient.getCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build())
        );

        database = mongoClient.getDatabase("componentes_prueba").withCodecRegistry(pojoCodecRegistry);
    }
    
    @AfterAll
    public static void tearDownClass() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
    
    @BeforeEach
    public void setUp() {
        // Inicializamos las colecciones necesarias para el DAO con el codec asignado
        coleccionPiezas = database.getCollection("piezas", Pieza.class);
        coleccionVentas = database.getCollection("ventas", Venta.class);
        
        // Colección auxiliar en formato Document plano para realizar asserts nativos
        piezasDocCollection = database.getCollection("piezas", Document.class);
        
        // Instanciamos el objeto bajo prueba
        piezaDAO = new PiezaDAO(coleccionPiezas, coleccionVentas);
    }
    
    @AfterEach
    public void tearDown() {
        // Limpiamos la colección de piezas después de cada test para aislar los escenarios
        piezasDocCollection.drop();
    }

    @Test
    public void testInsertar_GuardaPiezaCorrectamente() {
        // Arrange (Preparar)
        Pieza nuevaPieza = new Pieza("Core i5-14600K", "Procesador", "Intel", "Raptor Lake", 3200.0, 15);
        nuevaPieza.setId(new ObjectId().toString()); 

        // Act (Ejecutar)
        piezaDAO.insertar(nuevaPieza);

        // Assert (Verificar)
        Document filtro = new Document("nombre", "Core i5-14600K");
        Document resultadoBD = piezasDocCollection.find(filtro).first();

        assertNotNull(resultadoBD, "La pieza debería haberse insertado en la base de datos");
        assertEquals("Procesador", resultadoBD.getString("categoria"));
        assertEquals(15, resultadoBD.getInteger("stockPieza"), "El stock inicial debería ser 15");
    }

    @Test
    public void testConsultarPiezas_RetornaListaVaciaYConElementos() {
        // 1. Verificar comportamiento con la base de datos vacía
        List<Pieza> listaVacia = piezaDAO.consultarPiezas();
        assertNotNull(listaVacia);
        assertTrue(listaVacia.isEmpty(), "Debería retornar una lista vacía si no hay registros");

        // 2. Arrange: Insertar elementos de prueba usando el mapeo manual
        Pieza p1 = new Pieza("Trident Z5", "RAM", "G.Skill", "DDR5", 1800.0, 30);
        Pieza p2 = new Pieza("Vengeance", "RAM", "Corsair", "DDR5", 2100.0, 25);
        p1.setId(new ObjectId().toString());
        p2.setId(new ObjectId().toString());

        piezasDocCollection.insertOne(PiezaDoc.toDocument(p1));
        piezasDocCollection.insertOne(PiezaDoc.toDocument(p2));

        // Act: Volver a consultar
        List<Pieza> listaConElementos = piezaDAO.consultarPiezas();

        
        // Assert
        assertNotNull(listaConElementos);
        assertEquals(2, listaConElementos.size(), "La lista debería contener exactamente 2 piezas");
        assertEquals("Trident Z5", listaConElementos.get(0).getNombre());
    }

    @Test
    public void testActualizarStock_ReduceStockDeUnaPieza() {
        // Arrange: Guardar una pieza con 50 unidades de stock inicial
        String idPieza = new ObjectId().toString();
        Pieza pieza = new Pieza("Ryzen 5 9600X", "Procesador", "AMD", "Zen 5", 2500.0, 50);
        pieza.setId(idPieza);
        piezasDocCollection.insertOne(PiezaDoc.toDocument(pieza));

        // Crear el detalle de la venta (se van a comprar 8 unidades)
        DetallesVenta detalle = new DetallesVenta();
        detalle.setPieza(pieza);
        detalle.setCantidad(8);

        // Act
        piezaDAO.actualizarStock(detalle);

        // Assert: Validar que el stock restante en la BD sea 42 (50 - 8)
        Document docActualizado = piezasDocCollection.find(new Document("_id", new ObjectId(idPieza))).first();
        assertNotNull(docActualizado);
        
        int stockResultante = docActualizado.getInteger("stockPieza");
        assertEquals(42, stockResultante, "El stock en la BD debería haber disminuido a 42");
    }

    @Test
    public void testActualizarStockTrasVenta_ReduceStockDeMultiplesPiezas() {
        // Arrange: Crear e insertar dos piezas distintas con sus respectivos stocks iniciales
        String idPieza1 = new ObjectId().toString();
        Pieza pieza1 = new Pieza("Pieza Uno", "Test", "Marca", "Mod", 100.0, 20); // Stock: 20
        pieza1.setId(idPieza1);

        String idPieza2 = new ObjectId().toString();
        Pieza pieza2 = new Pieza("Pieza Dos", "Test", "Marca", "Mod", 200.0, 10); // Stock: 10
        pieza2.setId(idPieza2);

        piezasDocCollection.insertOne(PiezaDoc.toDocument(pieza1));
        piezasDocCollection.insertOne(PiezaDoc.toDocument(pieza2));

        // Crear la lista de detalles del "carrito" de compras
        DetallesVenta detalle1 = new DetallesVenta();
        detalle1.setPieza(pieza1);
        detalle1.setCantidad(5); // Restará 5 a la pieza 1

        DetallesVenta detalle2 = new DetallesVenta();
        detalle2.setPieza(pieza2);
        detalle2.setCantidad(2); // Restará 2 a la pieza 2

        List<DetallesVenta> carrito = Arrays.asList(detalle1, detalle2);

        // Act
        piezaDAO.actualizarStockTrasVenta(carrito);

        // Assert: Comprobar que ambas piezas actualizaron sus respectivos stocks de forma independiente
        Document doc1 = piezasDocCollection.find(new Document("_id", new ObjectId(idPieza1))).first();
        Document doc2 = piezasDocCollection.find(new Document("_id", new ObjectId(idPieza2))).first();

        assertNotNull(doc1);
        assertNotNull(doc2);
        
        assertEquals(15, doc1.getInteger("stockPieza"), "El stock de la pieza 1 debió bajar de 20 a 15");
        assertEquals(8, doc2.getInteger("stockPieza"), "El stock de la pieza 2 debió bajar de 10 a 8");
    }
}