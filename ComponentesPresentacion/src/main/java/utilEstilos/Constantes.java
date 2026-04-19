package utilEstilos;
import java.awt.Color;
import java.awt.Font;

/**
 * Clase con decisiones centralizadas (fuente, colores, textos)
 * 
 * @author Andre
 */
public class Constantes {
    
    /** Arreglo que ya define los campos de la barra superior de navegación */
    public static final String[] CAMPOS_PANEL_SUPERIOR = {
    "Inicio", "Iniciar venta", "Iniciar solicitud", "Historial de ventas", "Historial de solicitudes"};
    
    /** Constante que protege el texto que pide el nombre */
    public static final String PIEZA_NOMBRE = "Nombre de la pieza";
    
    /** Constante que protege el texto que pide la marca */
    public static final String PIEZA_MARCA = "Marca";
    
    /** Constante que protege el texto que pide la categoría */
    public static final String PIEZA_CATEGORIA = "Categoría";
    
    /** Constante que protege el texto que pide el precio máximo */
    public static final String PIEZA_PRECIOMAX = "Precio máximo";
    
    /** Azul (37, 99, 235) */
    public static final Color COLOR_BOTONES = new Color(37, 99, 235);
    
    /** Azul oscuro 29, 78, 216 */
    public static final Color COLOR_BOTON_HOVER = new Color(29, 78, 216);
    
    /** Blanco */
    public static final Color COLOR_TEXTO_BOTONES = Color.WHITE;
    
    /** Blanco */
    public static final Color COLOR_FONDO = Color.WHITE;
    
    /** Fuente que tendrán las letras del progrma */
    public static final Font FUENTE = new Font(Font.SANS_SERIF, Font.PLAIN, 14);
    
    /** Cantidad de caracteres que tendrán los campos de texto */ 
    public static final int NUM_CARACTERES = 20;
    
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    
    
    /** Color que se pone en un registro específico de una tabla: (210, 214, 218) || PUEDA QUE SE VAYA ESTA CONSTANTE */ 
    public static final Color COLOR_ELEGIR_FILA = new Color(210, 214, 218);
    
    /** Azul (37, 99, 235) | PUEDA QUE SE VAYA ESTA CONSTANTE */
    public static final Color COLOR_TABLA = COLOR_BOTONES;
}