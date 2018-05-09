package entregafinal;

/**
 * Define la abstracción de un grupo de estudiantes. 
 *
 */
public class Group {
    private final int groupId;
    private final int moduleIds[];

    /**
     * Inicializa el grupo de estudiantes.
     * 
     * @param groupId Identificador que distingue a cada grupo.
     * @param groupSize Tamaño del grupo.
     * @param moduleIds Material que tomará dicho grupo.
     */
    public Group(int groupId, int moduleIds[]){
        this.groupId = groupId;
        this.moduleIds = moduleIds;
    }
    
    /**
     * Regresa el ID del grupo.
     * 
     * @return groupId
     */
    public int getGroupId(){
        return this.groupId;
    }
        
    /**
     * Regresa el arreglo de clases que toma el grupo.
     * 
     * @return moduleIds
     */
    public int[] getModuleIds(){
        return this.moduleIds;
    }
}
