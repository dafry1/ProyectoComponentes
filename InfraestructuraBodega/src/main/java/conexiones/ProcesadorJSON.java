package conexiones;

import DTO.PiezaInfraestructuraDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import excepciones.InfraestructuraException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class ProcesadorJSON {

    private static final System.Logger LOG = System.getLogger(ProcesadorJSON.class.getName());
    private static ProcesadorJSON instancia;

    private ProcesadorJSON() {}

    public static ProcesadorJSON singleton() {
        if (instancia == null) {
            instancia = new ProcesadorJSON();
        }
        return instancia;
    }

    // --- MÉTODOS DE CONEXIÓN ---

    public List<PiezaInfraestructuraDTO> obtenerBodega() throws InfraestructuraException {
        return realizarPeticion("http://localhost:8081/piezas");
    }

    public List<PiezaInfraestructuraDTO> obtenerBodegaFiltrada(String campo, String valor) throws InfraestructuraException {
        String valorCodificado = java.net.URLEncoder.encode(valor, java.nio.charset.StandardCharsets.UTF_8);
        String url = "http://localhost:8081/piezas?" + campo + "=" + valorCodificado;
        return realizarPeticion(url);
    }

    private List<PiezaInfraestructuraDTO> realizarPeticion(String url) throws InfraestructuraException {
        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest peticion = HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
        
        try {
            HttpResponse<String> respuesta = cliente.send(peticion, HttpResponse.BodyHandlers.ofString());
            // PASAMOS EL JSON COMPLETO (STRING), SIN HACER SPLIT
            return convertirPiezas(respuesta.body());
        } catch (Exception e) {
            LOG.log(System.Logger.Level.ERROR, "Error en la conexión con la API", e);
            throw new InfraestructuraException("Error de conexión: " + e.getMessage());
        }
    }

    // --- LÓGICA DE PROCESAMIENTO (JACKSON) ---

    /**
     * Recibe el JSON completo y lo mapea automáticamente a DTOs
     */
    private List<PiezaInfraestructuraDTO> convertirPiezas(String jsonCrudo) {
        List<PiezaInfraestructuraDTO> listaResultados = new ArrayList<>();
        
        if (jsonCrudo == null || jsonCrudo.isBlank() || jsonCrudo.equals("[]")) {
            return listaResultados;
        }

        ObjectMapper mapper = new ObjectMapper();

        try {
            // Jackson analiza el String completo y detecta el Array
            JsonNode raiz = mapper.readTree(jsonCrudo);
            
            if (raiz.isArray()) {
                for (JsonNode nodo : raiz) {
                    PiezaInfraestructuraDTO dto = new PiezaInfraestructuraDTO();

                    // Mapeo directo y seguro
                    dto.setNombre(nodo.path("nombre").asText("N/A"));
                    dto.setCategoria(nodo.path("categoria").asText("General"));
                    dto.setMarcaPieza(nodo.path("marca").asText("Genérica"));
                    dto.setModeloPieza(nodo.path("modelo").asText("Estándar"));
                    
                    // asDouble() resuelve el problema del "0"
                    dto.setCostoPieza(nodo.path("precio").asDouble(0.0));

                    if (!dto.getNombre().equals("N/A")) {
                        listaResultados.add(dto);
                    }
                }
            }
        } catch (Exception e) {
            LOG.log(System.Logger.Level.ERROR, "Error parseando JSON", e);
        }
        return listaResultados;
    }
}