package entregafinal;

/**
 * Define la abstracción de una clase.
 */
public class Module {
    private final int moduleId;
    private final int professorIds[];
    
    /**
     * Inicializa una clase.
     * 
     * @param moduleId
     * @param professorIds
     */
    public Module(int moduleId, int professorIds[]){
        this.moduleId = moduleId;
        this.professorIds = professorIds;
    }
    
    /**
     * Regresa el ID de la clase.
     * 
     * @return moduleId
     */
    public int getModuleId(){
        return this.moduleId;
    }
    
    /**
     * Regresa un el ID de un profesor elegido de manera aleatoria.
     * 
     * @return professorId
     */
    public int getRandomProfessorId(){
        int professorId = professorIds[(int) (professorIds.length * Math.random())];
        return professorId;
    }
}
