package daos;

import adaptadoresDoc.SolicitudDoc;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dominio.Solicitud;
import excepciones.PersistenciaException;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SolicitudDAOTest {

    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private MongoCollection<Document> coleccion;
    private SolicitudDAO solicitudDAO;

    @BeforeAll
    public static void init() {
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("componentes_prueba");
    }

    @BeforeEach
    public void setUp() {
        // Inicializamos la colección limpia para el test actual
        coleccion = database.getCollection("solicitudes");
        solicitudDAO = new SolicitudDAO(coleccion);
    }

    @AfterEach
    public void tearDown() {
        // Borramos los datos para que no afecten a las siguientes pruebas
        coleccion.drop();
    }

    @Test
    public void testRegistrarSolicitud_Exitoso() {
        // Arrange
        Solicitud solicitud = new Solicitud();
        // Ajusta estos setters según las propiedades reales de tu clase Solicitud
        solicitud.setFechaHora(LocalDateTime.now().toString()); 

        // Act
        Solicitud resultado = solicitudDAO.registrarSolicitud(solicitud);

        // Assert
        assertNotNull(resultado, "La solicitud registrada no debería ser nula");
        assertNotNull(resultado.getId(), "MongoDB debería haber asignado un ID único a la solicitud");
        assertEquals(1, coleccion.countDocuments(), "Debería existir exactamente 1 documento en la colección");
    }

    @Test
    public void testConsultarSolicitudes_RetornaListaCompleta() {
        // Arrange - Insertamos un par de solicitudes usando el adaptador estático
        Solicitud s1 = new Solicitud();
        s1.setFechaHora(LocalDateTime.now().toString());
        
        Solicitud s2 = new Solicitud();
        s2.setFechaHora(LocalDateTime.now().toString());

        coleccion.insertOne(SolicitudDoc.toDocument(s1));
        coleccion.insertOne(SolicitudDoc.toDocument(s2));

        // Act
        List<Solicitud> resultado = solicitudDAO.consultarSolicitudes();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size(), "Debería haber recuperado las 2 solicitudes guardadas");
    }

    @Test
    public void testContarSolicitudesHoy_FiltraCorrectamente() {
        // Arrange
        String hoy = LocalDateTime.now().toString(); // Empieza con YYYY-MM-DD
        String ayer = LocalDateTime.now().minusDays(1).toString();

        Solicitud sHoy1 = new Solicitud(); sHoy1.setFechaHora(hoy);
        Solicitud sHoy2 = new Solicitud(); sHoy2.setFechaHora(hoy);
        Solicitud sAyer = new Solicitud(); sAyer.setFechaHora(ayer);

        // Insertamos los registros a través del DAO
        solicitudDAO.registrarSolicitud(sHoy1);
        solicitudDAO.registrarSolicitud(sHoy2);
        solicitudDAO.registrarSolicitud(sAyer);

        // Act
        int totalHoy = solicitudDAO.contarSolicitudesHoy();

        // Assert
        assertEquals(2, totalHoy, "El contador por regex solo debería sumar las 2 solicitudes de hoy");
    }

    @Test
    public void testRegistrarSolicitud_ObjetoNulo_RetornaNull() {
        // Act
        Solicitud resultado = solicitudDAO.registrarSolicitud(null);

        // Assert
        assertNull(resultado, "Al enviar una solicitud nula debe retornar null");
        assertEquals(0, coleccion.countDocuments(), "La base de datos debería seguir vacía");
    }
}