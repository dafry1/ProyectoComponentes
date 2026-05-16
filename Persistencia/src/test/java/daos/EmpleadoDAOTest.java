package daos;

import adaptadoresDoc.EmpleadoDoc;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import dominio.Empleado;
import excepciones.PersistenciaException;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmpleadoDAOTest {

    private static MongoClient mongoClient;
    private static MongoDatabase database;
    private MongoCollection<Document> coleccionEmpleados;
    private EmpleadoDAO empleadoDAO;

    @BeforeAll
    public static void init() {
        mongoClient = MongoClients.create("mongodb://localhost:27017");
        database = mongoClient.getDatabase("componentes_prueba");
    }

    @BeforeEach
    public void setUp() {
        // Obtenemos la colección limpia para aislar las pruebas
        coleccionEmpleados = database.getCollection("empleados", Document.class);
        // Instanciamos el DAO pasándole la colección
        empleadoDAO = new EmpleadoDAO(coleccionEmpleados);
    }

    @AfterEach
    public void tearDown() {
        // Borramos los datos insertados al finalizar cada test
        coleccionEmpleados.drop();
    }

    @Test
    public void testInsertarEmpleado_Exitoso() {
        // Arrange
        Empleado empleado = new Empleado();
        empleado.setId(new ObjectId().toString());
        empleado.setNombreUsuario("andre99");
        empleado.setContrasenia("password123");

        // Act
        empleadoDAO.insertarEmpleado(empleado);

        // Assert: Comprobamos directo en MongoDB que exista el documento plano
        Document resultadoBD = coleccionEmpleados.find(new Document("nombreUsuario", "andre99")).first();
        assertNotNull(resultadoBD, "El empleado debió guardarse en MongoDB");
        assertEquals("password123", resultadoBD.getString("contrasenia"));
    }

    @Test
    public void testConsultarEmpleados_RetornaCatalogoCompleto() {
        // Arrange: Insertamos dos empleados usando el adaptador estático
        Empleado e1 = new Empleado();
        e1.setId(new ObjectId().toString());
        e1.setNombreUsuario("user1");
        
        Empleado e2 = new Empleado();
        e2.setId(new ObjectId().toString());
        e2.setNombreUsuario("user2");

        coleccionEmpleados.insertOne(EmpleadoDoc.toDocument(e1));
        coleccionEmpleados.insertOne(EmpleadoDoc.toDocument(e2));

        // Act
        List<Empleado> resultado = empleadoDAO.consultarEmpleados();

        // Assert
        assertNotNull(resultado);
        assertEquals(2, resultado.size(), "Debieron recuperarse exactamente los 2 empleados de la BD");
        assertEquals("user1", resultado.get(0).getNombreUsuario());
    }

    @Test
    public void testVerificarEmpleado_CredencialesCorrectas_RetornaEmpleado() {
        // Arrange: Registramos un empleado con credenciales específicas
        Empleado empleado = new Empleado();
        empleado.setId(new ObjectId().toString());
        empleado.setNombreUsuario("admin");
        empleado.setContrasenia("securePass");
        coleccionEmpleados.insertOne(EmpleadoDoc.toDocument(empleado));

        // Act
        Empleado resultado = empleadoDAO.verificarEmpleado("admin", "securePass");

        // Assert
        assertNotNull(resultado, "Debería retornar el objeto mapeado si las credenciales coinciden");
        assertEquals("admin", resultado.getNombreUsuario());
    }

    @Test
    public void testVerificarEmpleado_CamposVacios_LanzaPersistenciaException() {
        // Act & Assert: Comprobamos que lance excepción si el usuario es nulo o vacío
        PersistenciaException exNulo = assertThrows(PersistenciaException.class, () -> {
            empleadoDAO.verificarEmpleado(null, "123");
        });
        assertEquals("No se puede iniciar sesión con campos faltantes", exNulo.getMessage());

        PersistenciaException exVacio = assertThrows(PersistenciaException.class, () -> {
            empleadoDAO.verificarEmpleado("  ", "123");
        });
        assertEquals("No se puede iniciar sesión con campos faltantes", exVacio.getMessage());
    }

    @Test
    public void testVerificarEmpleado_ContraseniaIncorrecta_LanzaPersistenciaException() {
        // Arrange: Guardamos un usuario válido
        Empleado empleado = new Empleado();
        empleado.setId(new ObjectId().toString());
        empleado.setNombreUsuario("cajero");
        empleado.setContrasenia("claveCorrecta");
        coleccionEmpleados.insertOne(EmpleadoDoc.toDocument(empleado));

        // Act & Assert: Intentamos loguear con contraseña incorrecta
        PersistenciaException ex = assertThrows(PersistenciaException.class, () -> {
            empleadoDAO.verificarEmpleado("cajero", "claveInvalida");
        });
        
        assertEquals("El usuario no existe en el sistema", ex.getMessage());
    }

    @Test
    public void testVerificarEmpleado_UsuarioInexistente_LanzaPersistenciaException() {
        // Act & Assert: Intentar buscar un usuario que no está registrado en la BD
        PersistenciaException ex = assertThrows(PersistenciaException.class, () -> {
            empleadoDAO.verificarEmpleado("usuarioFantasma", "cualquiera");
        });

        assertEquals("El usuario no existe en el sistema", ex.getMessage());
    }
}