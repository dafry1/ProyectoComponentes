package daos;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import dominio.Empleado;
import excepciones.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.bson.Document;
import org.bson.conversions.Bson;
import adaptadoresDoc.EmpleadoDoc; // <-- Importamos tu adaptador

/**
 * DAO enfocado en la persistencia y consulta real de empleados en MongoDB.
 * 
 * @author Andre
 */
public class EmpleadoDAO implements IEmpleadoDAO {
    private static final Logger LOG = Logger.getLogger(EmpleadoDAO.class.getName());
    private static final String CAMPOS_VACIOS = "No se puede iniciar sesión con campos faltantes";
    private static final String EMPLEADO_INEXISTENTE = "El usuario no existe en el sistema";
    
    // Cambiamos a MongoCollection<Document> para interceptar el flujo con tus adaptadores
    private final MongoCollection<Document> coleccion;
    
    public EmpleadoDAO(MongoCollection<?> coleccion) {
        this.coleccion = (MongoCollection<Document>) coleccion;
    }
    
    /**
     * Extrae todos los empleados utilizando el mapeo manual de EmpleadoDoc.
     */
    @Override
    public List<Empleado> consultarEmpleados() {
        List<Empleado> listaEmpleados = new ArrayList<>();
        try {
            // Iteramos sobre los documentos y usamos tu adaptador manual
            for (Document doc : coleccion.find()) {
                listaEmpleados.add(EmpleadoDoc.toEntity(doc));
            }
        } catch (Exception ex) {
            LOG.severe(">> Error al consultar empleados de la BD: " + ex.getMessage());
            throw new PersistenciaException("Error al obtener el catálogo de empleados.");
        }
        return listaEmpleados;
    }
    
    /**
     * Valida credenciales consultando un Document plano y transformándolo de forma segura.
     */
    @Override
    public Empleado verificarEmpleado(String nombreUsuario, String password) {
        
        if (nombreUsuario == null || nombreUsuario.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            LOG.warning(">> " + CAMPOS_VACIOS);
            throw new PersistenciaException(CAMPOS_VACIOS);
        }

        try {
            Bson filtroCredenciales = Filters.and(
                Filters.eq("nombreUsuario", nombreUsuario.trim()),
                Filters.eq("contrasenia", password)
            );

            // Buscamos el Document plano
            Document docEncontrado = coleccion.find(filtroCredenciales).first();

            if (docEncontrado != null) {
                LOG.info(">> Empleado verificado con éxito: " + nombreUsuario);
                // Tu EmpleadoDoc se encarga de usar AdaptadorDoc para procesar el ID de forma correcta
                return EmpleadoDoc.toEntity(docEncontrado); 
            }
            
        } catch (Exception ex) {
            LOG.severe(">> Error crítico de infraestructura o conexión durante la verificación de credenciales.");
            throw new PersistenciaException("Error de conexión al verificar credenciales.");
        }

        LOG.warning("Intentó ingresar el usuario '" + nombreUsuario + "', pero no coincide el usuario o contraseña.");
        throw new PersistenciaException(EMPLEADO_INEXISTENTE);
    }

    /**
     * Inserta un empleado transformándolo primero a Document con tu adaptador.
     */
    @Override
    public void insertarEmpleado(Empleado empleado) {
        try {
            Document docEmpleado = EmpleadoDoc.toDocument(empleado);
            coleccion.insertOne(docEmpleado);
        } catch(Exception ex) {
            throw new PersistenciaException(ex.getMessage()); 
        }
    }
}