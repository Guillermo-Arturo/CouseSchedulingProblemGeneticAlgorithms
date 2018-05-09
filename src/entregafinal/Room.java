package entregafinal;

/**
 * Define la abstracción de un salón.
 */
public class Room {
	private final int roomId;
	private final String roomNumber;

	/**
	 * Inicializa un nuevo salón.
	 * 
	 * @param roomId ID del salón.
	 *            
	 * @param roomNumber Número del salón.
	 */
	public Room(int roomId, String roomNumber) {
		this.roomId = roomId;
		this.roomNumber = roomNumber;
	}

	/**
	 * Regresa el ID del salón.
	 * 
	 * @return roomId
	 */
	public int getRoomId() {
		return this.roomId;
	}

	/**
	 * Regresa el número del salón.
	 * 
	 * @return roomNumber
	 */
	public String getRoomNumber() {
		return this.roomNumber;
	}
}