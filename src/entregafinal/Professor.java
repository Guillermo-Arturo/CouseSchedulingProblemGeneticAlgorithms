package entregafinal;
/**
 * Define la abstracción de un profesor.
 */
public class Professor {
    private final int professorId;

    /**
     * Inicializa a un nuevo profesor.
     * 
     * @param professorId ID del profesor.
     */
    public Professor(int professorId){
        this.professorId = professorId;
    }
    
    /**
     * Regresa el ID del profesor.
     * 
     * @return professorId
     */
    public int getProfessorId(){
        return this.professorId;
    }
}
