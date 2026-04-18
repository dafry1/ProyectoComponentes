package coordinadores;

/**
 * Coordinador que sabe cosas que deben compartirse a lo largo del
 * sistema, como un objeto específico que debe ser trasladado o
 * recordar qué usuario y de qué tipo entró al programa
 * 
 * @author Andre
 */
public class CoordinadorEstados {
    
    //Instancia de sí mismo
    private CoordinadorEstados instancia = null;
    
    /**
     * Sinleton que asegura trabajar con una única instancia
     * 
     * @return la instancia singleton del coordinador
     */
    public CoordinadorEstados singleton() {
        if (instancia == null) {
            instancia = new CoordinadorEstados();
        }
        return instancia;
    }
    
    /** Guarda que la sesión actual le pertenece a un administrador */
    public void establecerAdministrador() {
        //TODO lógica
    }
    
    /** Guarda que la sesión actual le pertenece a un empleado */
    public void establecerEmpleado() {
        //TODO lógica
   }
}