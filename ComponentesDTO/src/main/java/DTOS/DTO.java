package DTOS;

/**
 * Superclase de la cual todos nuestros DTOs van a heredar para así
 * establecer un marco en común de comportamiento y atributos. En este
 * caso, todos los DTO poseen una ID y el método esIgual(), que permite
 * comparar IDs Long. También sirve para establecer contratos genéricos,
 * como con el objeto BotonAlmacenador, que sabe que solo debe guardar
 * un DTO, sin conocer de qué se trata específicamente
 * 
 * @author Andre
 */
public abstract class DTO {
    
    //Atributo, getter y setter que todos van a heredar
    protected Long id;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * Método propio que compara la id del objeto con la id del
     * parámetro. Existe para validar existencia de forma fácil
     * dentro de arreglos o comparaciones rápidas sin meterse en
     * soluciones locas
     * 
     * @param id a buscar
     * @return true si coinciden, false de lo contrario
     */
    public boolean esIgual(Long id) {
        if (this.id == null || id == null) {
            return false;
        }
        return this.id.equals(id);
    }
}