package conexiones;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Se encarga de la conexión a una BD SQL
 * 
 * @author Andre
 */
public class ConexionSQL {
    
    //Declara los datos para conectar a la base de datos
    private final String CADENA_CONEXION = "jdbc:mysql://localhost:3306/componentes";
    private final String USUARIO = "root";
    private final String CONTRASENIA = "admin";
    
    /**
     * Establece la conexión para una BD SQL
     * 
     * @return una conexión
     * 
     * @throws SQLException 
     */
    public Connection crearConexion() throws SQLException {
        return DriverManager.getConnection(CADENA_CONEXION, USUARIO, CONTRASENIA);
    }
}