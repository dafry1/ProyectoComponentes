package interfaces;

/**
 * Interfaz la cual todos nuestros DTO van a implementar para poder
 * establecer contratos de comportamiento y funcionalidad, como afirmar
 * con seguridad que cierto componente trabajará con objeto DTO de manera
 * general. Se usa interfaz para evitar casteo
 * 
 * @author Andre
 */
public interface IDTO {

    /**
     * Nombre del tipo de DTO que representa. No de la instancia
     * en específico, sino de la idea que representa en el proyecto
     * 
     * @return un String con su nombre
     */
    String nombreDTO();
    
    /**
     * Reune en un String toda información de la clase para poder ser
     * pasada fácilmente entre pantallas como se necesite, así cada
     * DTO habla por sí mismo
     * 
     * @return un String con toda su información
     */
    String mostrarInfo();
}