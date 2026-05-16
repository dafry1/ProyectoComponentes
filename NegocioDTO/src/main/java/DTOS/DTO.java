package DTOS;

/**
 * Superclase de la cual todos nuestros DTOs van a heredar para así
 * establecer un marco en común de comportamiento y atributos. En este
 * caso, todos los DTO poseen una ID y el equals sobreescrito, que permite
 * comparar IDs Long. También sirve para establecer contratos genéricos,
 * como con el objeto BotonAlmacenador, que sabe que solo debe guardar
 * un DTO, sin conocer de qué se trata específicamente. Es abstracta porque
 * no puedes tener un DTO genérico instanciado; es como querer tener un
 * animal sin especie ni clasificación taxonómica. Sobreescribe los 
 * métodos equals y hashCode
 * 
 * @author Andre
 */
public abstract class DTO {
    
    //Atributo, getter y setter que todos van a heredar
    protected String id;
    public String getId() {return id; }
    public void setId(String id) { this.id = id; }
    
    /** Constructor vacío porque las otras entidades también lo necesitan */
    public DTO() {}
    
    /** Constructor para que las subclases lo usen sin problemas */
    public DTO(String id) { this.id = id; }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DTO other = (DTO) o;
        return this.id != null && other.getId() != null && this.id.trim().equals(other.getId().trim());
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}