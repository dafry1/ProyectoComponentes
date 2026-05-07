package conexiones;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistries;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

/**
 * Clase que suministra la conexión a una base de
 * datos Mongo. Utiliza codec
 * 
 * @author Andre
 */
public class ConexionMongo {
    
    //URL del servicio
    private static final String URL = "mongodb://localhost:27017";
    
    //Nombre de la base de datos
    private static final String BD = "componentes";
    
    //Instancia única
    private static MongoClient cliente;
    
    /**
     * Mediante un singleton regresa la instancia
     * única del cliente de mongo
     * 
     * @return cliente mongo
     */
    private static MongoClient obtenerCliente() {
        if (cliente == null) {
            cliente = crearCliente();
        }
        return cliente;
    }
    
    /**
     * Regresa la base de datos directamente
     * del cliente
     * 
     * @return la base de datos
     */
    public static MongoDatabase obtenerBD() {
        return obtenerCliente().getDatabase(BD);
    }
    
    /**
     * Mediante un traductor reflexivo (automatic = true) que
     * une varias formas para pasar de POJO a BSON, inyecta el
     * registro en la configuración de Mongo y finalmente
     * regresa el client
     * 
     * @return el cliente de Mongo ya configurado
     */
    private static MongoClient crearCliente() {
        CodecProvider proveedorPojo = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry registroCodecs = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(proveedorPojo)
        );
        MongoClientSettings configuracionMongo = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(URL))
                .codecRegistry(registroCodecs)
                .build();
        return MongoClients.create(configuracionMongo);
    }
}