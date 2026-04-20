package utilEstilos;

/**
 * Utilerías donde se verifica lógica de presentación de forma instantánea, como
 * validar un número o un String. Sirve para no despertar innecesariamente a la
 * capa de Negocio, encargado de lógica más pesada. Podríamos decir que aquí está
 * lo, relativamente, universal. No vas a escribir "abc" si te preguntan por cantidad
 * 
 * @author Andre
 */
public class UtilFormato {
    
    /**
     * Valida si un número (aún en String fresco de un formulario)
     * es mayor a 0, sin decimales. Confirma con el regex que sean
     * únicamente números del 1 al 9.
     * 
     * @param numero en String para validar
     * 
     * @return true si es entero positivo, false de lo contrario
     */
    public static boolean numeroEnteroPositivo(String numero) {
        return numero.matches("[1-9]\\d*");
    }
}