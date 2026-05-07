package conexiones;

import DTOS.PiezaDTO;
import excepciones.InfraestructuraException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que se conecta con la API y obtiene las piezas en un JSON, y las
 * procesa en DTOs de negocio para ser mandados al subsistema de solicitudes
 *
 * @author Andre
 */
public class ConexionBodegaAPI {

    private static final System.Logger LOG = System.getLogger(ConexionBodegaAPI.class.getName());
    private static ConexionBodegaAPI instancia;

    private ConexionBodegaAPI() {
    }

    public static ConexionBodegaAPI singleton() {
        if (instancia == null) {
            instancia = new ConexionBodegaAPI();
        }
        return instancia;
    }

    //Tiene la URL del GitHubGist donde si se actualiza se nota rápidamente en Java
    String URL_BASE = "https://gist.githubusercontent.com/AndreVegaR/f227a380a9f0d5e76935241086bdfe47/raw/bodega_technoware.json";

    /**
     * Coordina varios métodos internos y configura todo para conectarse por
     * Internet a la API, convertir el JSON a DTOs y regresarlos en una lista
     *
     * @return lista de piezas
     */
    public List<PiezaDTO> consultarBodega() {

        //Motor que envía la petición como abrir en el navegador
        HttpClient cliente = HttpClient.newHttpClient();

        //Crea la petición
        HttpRequest peticion = crearPeticion();

        //Intenta conectarse a Internet
        try {

            //Envía la petición al servidor
            HttpResponse<String> respuesta = cliente.send(peticion, HttpResponse.BodyHandlers.ofString());

            //Almacena todo lo extraído en un solo JSON en String
            String json = respuesta.body();

            /**
             * Divide el JSON en bloques. Cada vez que hay un nuevo campo
             * "nombre", significa que es otra pieza de la bodega. Guarda cada
             * "bloque" en un arreglo de strings
             */
            String[] piezasJSON = json.split("\\{");

            //Regresa la lista de piezas convertidas
            return convertirPiezas(piezasJSON);
        } //Excepción si hubo problemas al conectarse
        catch (Exception e) {
            String DEBUG = "Error al conectarse con la API: " + e.getMessage();
            LOG.log(System.Logger.Level.ERROR, DEBUG);
            throw new InfraestructuraException(DEBUG);
        }
    }

    /**
     * 1. Inicia el proceso para crear la petición de conexión 2. Define a qué
     * dirección URL se envía la petición 3. Configura para evitar caché 4.
     * Configura para evitar caché en sistemas más desactualizados 5. Establece
     * que siempre caduca el contenido, por lo que cada vez debe actualizar
     */
    private HttpRequest crearPeticion() {
        return HttpRequest.newBuilder()
                .uri(URI.create(URL_BASE + "?t=" + System.currentTimeMillis()))
                .header("Cache-Control", "no-cache, no-store, must-revalidate")
                .header("Pragma", "no-cache")
                .header("Expires", "0")
                .GET()
                .build();
    }

    /**
     * Obtiene un bloque de información de la pieza en String, salido
     * directamente del JSON, y así extrae los datos para regresar una PiezaDTO
     *
     * @param bloque JSON de la pieza
     *
     * @return PiezaDTO
     */
    private PiezaDTO convertirPieza(String bloque) {
        //Extraer con valores por defecto para evitar el error de .matches()
        String idStr = extraer(bloque, "id");
        String nombre = extraer(bloque, "nombre");
        String categoria = extraer(bloque, "categoria");
        String marca = extraer(bloque, "marca");
        String modelo = extraer(bloque, "modelo");

        // Validación de seguridad: Si no hay nombre, no procesamos la pieza
        if (nombre.equals("N/A")) {
            return null;
        }

        //  Parseo de ID (Asegurar que no sea nulo para el BO)
        Long id;
        try {
            id = Long.parseLong(idStr.replaceAll("[^0-9]", ""));
        } catch (Exception e) {
            id = (long) Math.abs(bloque.hashCode()); // ID temporal si falla
        }

        //Crear DTO usando SETTERS
        PiezaDTO pieza = new PiezaDTO();
        pieza.setId(id);
        pieza.setNombre(nombre);
        pieza.setCategoria(categoria.equals("N/A") ? "General" : categoria);
        pieza.setMarcaPieza(marca.equals("N/A") ? "Genérica" : marca);
        pieza.setModeloPieza(modelo.equals("N/A") ? "" : modelo);

        //Procesar Precio 
        double precio = 0;
        if (bloque.contains("\"precio\":")) {
            try {
                int inicioP = bloque.indexOf("\"precio\":") + 9;
                int finP = bloque.indexOf(",", inicioP);
                if (finP == -1) {
                    finP = bloque.indexOf("}", inicioP);
                }
                String pStr = bloque.substring(inicioP, finP).replaceAll("[^0-9.]", "").trim();
                precio = Double.parseDouble(pStr);
            } catch (Exception e) {
                precio = 0;
            }
            pieza.setCostoPieza(precio);
        }
        return pieza;
    }

    /**
     * Itera sobre un String JSON para convertir cada elemento a una PiezaDTO
     *
     * @param piezasJSON
     *
     * @return colección de PiezasDTO
     */
    private List<PiezaDTO> convertirPiezas(String[] piezasJSON) {
        List<PiezaDTO> bodega = new ArrayList<>();
        for (String bloque : piezasJSON) {
            // Ignorar bloques vacíos o que no contienen datos reales
            if (bloque == null || !bloque.contains("\"")) {
                continue;
            }

            PiezaDTO pieza = convertirPieza(bloque);
            if (pieza != null) {
                bodega.add(pieza);
            }
        }
        return bodega;
    }

    /**
     * Auxiliar que extrae el valor basándose en una etiqueta específica.
     * Configura un patrón con formato para buscar, localiza el valor y lo
     * regresa
     *
     * @param piezaJSON en el formato JSON
     * @param llave del juego para obtener el valor
     *
     * @return el texto del valor
     */
    private String extraer(String piezaJSON, String llave) {
        String patron = "\"" + llave + "\": \"";
        if (!piezaJSON.contains(patron)) {
            return "N/A";
        }
        int inicio = piezaJSON.indexOf(patron) + patron.length();
        int fin = piezaJSON.indexOf("\"", inicio);
        return piezaJSON.substring(inicio, fin);
    }
}
