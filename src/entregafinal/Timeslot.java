package entregafinal;
/**
 * Define la abstracción de un horario en el cual se imparte una clase.
 *
 */
public class Timeslot {
    private final int timeslotId;
    private final String timeslot;

    /**
     * Inicializa un nuevo horario de clase.
     * 
     * @param timeslotId ID de un horario.
     * @param timeslot Horario de clase
     */
    public Timeslot(int timeslotId, String timeslot){
        this.timeslotId = timeslotId;
        this.timeslot = timeslot;
    }
    
    /**
     * Regresa el ID de horario de clase.
     * 
     * @return timeslotId
     */
    public int getTimeslotId(){
        return this.timeslotId;
    }
    
    /**
     * Regresa el horario de clase.
     * 
     * @return timeslot
     */
    public String getTimeslot(){
        return this.timeslot;
    }
}
