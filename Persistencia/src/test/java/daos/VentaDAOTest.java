package daos;

import adaptadoresDoc.VentaDoc;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dominio.Venta;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VentaDAOTest {

    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private MongoCollection<Document> coleccion;
    private VentaDAO ventaDAO;

    @BeforeAll
    public static void init() {
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("componentes_prueba");
    }

    @BeforeEach
    public void setUp() {
        coleccion = database.getCollection("ventas", Document.class);
        coleccion.drop();
        ventaDAO = new VentaDAO(coleccion);
    }

    @AfterEach
    public void tearDown() {
        if (coleccion != null) {
            coleccion.drop();
        }
    }

    @Test
    public void testRegistrarVenta_Exitoso() {
        Venta venta = new Venta();
        venta.setTotal(150.50);
        venta.setFechaHora(LocalDate.now().toString() + " 12:00:00"); 

        Venta resultado = ventaDAO.registrarVenta(venta);

        assertNotNull(resultado, "La venta registrada no debería ser nula");
        assertNotNull(resultado.getId(), "El ID de la venta debería haber sido asignado");
        assertEquals(1, coleccion.countDocuments(), "Debería haber exactamente 1 documento en la BD");
    }

    @Test
    public void testConsultarVentas_RetornaLista() {
        Venta v1 = new Venta();
        v1.setTotal(10.0);
        v1.setFechaHora(LocalDate.now().toString() + " 10:00:00");

        Venta v2 = new Venta();
        v2.setTotal(20.0);
        v2.setFechaHora(LocalDate.now().toString() + " 11:00:00");

        coleccion.insertOne(VentaDoc.toDocument(v1));
        coleccion.insertOne(VentaDoc.toDocument(v2));

        List<Venta> listaVentas = ventaDAO.consultarVentas();

        assertNotNull(listaVentas);
        assertEquals(2, listaVentas.size(), "La lista debería contener exactamente las 2 ventas insertadas");
    }

    @Test
    public void testCantidadVentas_FiltraPorHoy() {
        String hoy = LocalDate.now().toString() + " 08:00:00"; 
        String ayer = LocalDate.now().minusDays(1).toString() + " 08:00:00";

        Venta vHoy1 = new Venta(); vHoy1.setFechaHora(hoy); vHoy1.setTotal(100.0);
        Venta vHoy2 = new Venta(); vHoy2.setFechaHora(hoy); vHoy2.setTotal(200.0);
        Venta vAyer = new Venta(); vAyer.setFechaHora(ayer); vAyer.setTotal(150.0);

        coleccion.insertOne(VentaDoc.toDocument(vHoy1));
        coleccion.insertOne(VentaDoc.toDocument(vHoy2));
        coleccion.insertOne(VentaDoc.toDocument(vAyer));

        int cantidadHoy = ventaDAO.cantidadVentas();

        assertEquals(2, cantidadHoy, "El contador debería reflejar solo las ventas del día de hoy");
    }

    @Test
    public void testRegistrarVenta_VentaNula_RetornaNull() {
        Venta resultado = ventaDAO.registrarVenta(null);

        assertNull(resultado, "Pasar un objeto nulo debería retornar null");
        assertEquals(0, coleccion.countDocuments(), "No se debería haber insertado nada en la BD");
    }
}