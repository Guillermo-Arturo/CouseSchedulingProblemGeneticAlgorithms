package entregafinal;

import java.util.HashMap;

/** 
 * Esta clase es la encarga de realizar las evaluaciones ya que es una abstracción de un
 * horario de clases tabular. Es la encargada de leer un cromosoma y crear un horario a 
 * partir de este. Además de evaluar cada horario de cada individuo generado, así como el calculo
 * de la función fitnes y de las condiciones que penalizan dicha función.
 * 
 * Los métodos más importantes de esta clase son createClasses() y calcClashes()
 *  
 * El método createClasses acepta a un individuo de la población y desempaqueta sus 
 * cromosomas. Después crea instancias de Class que almacenan la información genética 
 * de cada clase. 
 * 
 * El método calcClashes es usado para calcular la función fitness. Este evalua las instancias 
 * creadas por createClasses y cuenta cuantas restricciones fueron violadas. Agregando una
 * penalización a cada clase.
 * 
 */

public class Timetable {
	private final HashMap<Integer, Room> rooms;
	private final HashMap<Integer, Professor> professors;
	private final HashMap<Integer, Module> modules;
	private final HashMap<Integer, Group> groups;
	private final HashMap<Integer, Timeslot> timeslots;
	private Class classes[];

	private int numClasses = 0;

	/**
	 * Inicializa un nuevo horario.
	 */
	public Timetable() {
		this.rooms = new HashMap<Integer, Room>();
		this.professors = new HashMap<Integer, Professor>();
		this.modules = new HashMap<Integer, Module>();
		this.groups = new HashMap<Integer, Group>();
		this.timeslots = new HashMap<Integer, Timeslot>();
	}

	/**
	 * Crea copias del horario de clases.
	 */
	public Timetable(Timetable cloneable) {
		this.rooms = cloneable.getRooms();
		this.professors = cloneable.getProfessors();
		this.modules = cloneable.getModules();
		this.groups = cloneable.getGroups();
		this.timeslots = cloneable.getTimeslots();
	}

	/**
	 * Regresa los grupos del horario
	 * 
	 * @return groups
	 * */
	private HashMap<Integer, Group> getGroups() {
		return this.groups;
	}

	/**
	 * Regresa los horarios de cada clase.
	 * 
	 * @return timeslots
	 * */
	private HashMap<Integer, Timeslot> getTimeslots() {
		return this.timeslots;
	}
	
	/**
	 * Regresa las clases del horario de clases.
	 * 
	 * @return modules
	 * 
	 * */
	private HashMap<Integer, Module> getModules() {
		return this.modules;
	}

	/**
	 * Regresa los profesores asignado al horario de clases
	 * 
	 * @return profesores.
	 * */
	private HashMap<Integer, Professor> getProfessors() {
		return this.professors;
	}

	/**
	 * Agrega un nuevo salón al horario de clases.
	 * 
	 * @param roomId ID del salón
	 * @param roomName Número del salón
	 */
	public void addRoom(int roomId, String roomName) {
		this.rooms.put(roomId, new Room(roomId, roomName));
	}

	/**
	 * Agrega un nuevo profesor al horario de clases.
	 * 
	 * @param professorId ID del profesor.
	 */
	public void addProfessor(int professorId) {
		this.professors.put(professorId, new Professor(professorId));
	}

	/**
	 * Agrega una nueva clase al horario de clases.
	 * 
	 * @param moduleId ID de la clase
	 * @param professorIds Arreglo de profesores que imparten dicha clase
	 */
	public void addModule(int moduleId, int professorIds[]) {
		this.modules.put(moduleId, new Module(moduleId, professorIds));
	}

	/**
	 * Agrega un nuevo grupo al horario de clases.
	 * 
	 * @param groupId ID del grupo de estudiantes 
	 * @param moduleIds Array de material que toma el grupo.
	 */
	public void addGroup(int groupId, int moduleIds[]) {
		this.groups.put(groupId, new Group(groupId, moduleIds));
		this.numClasses = 0;
	}

	/**
	 * Agrega un nuevo horario de clase.
	 * 
	 * @param timeslotId ID del horario.
	 * @param timeslot Horario de la clase.
	 */
	public void addTimeslot(int timeslotId, String timeslot) {
		this.timeslots.put(timeslotId, new Timeslot(timeslotId, timeslot));
	}

	/**
	 * Crea clases usando la información de un cromosoma.
	 * 
	 * Itera a travpes del cromosoma y crea objetos Class en los cuales almacena la 
	 * información genética de cada clase para después ser evaluada y calcular en número
	 * de restricciones violadas.
	 * 
	 * @param individual Individuo de la población.
	 */
	public void createClasses(Individual individual) {
		// Inicializa el arreglo de clases del cromosoma.
		Class classes[] = new Class[this.getNumClasses()];

		// Obtiene el cromosoma del individuo.
		int chromosome[] = individual.getChromosome();
		int chromosomePos = 0;
		int classIndex = 0;
		
		// Iteramos a través de los grupos.
		for (Group group : this.getGroupsAsArray()) {
			int moduleIds[] = group.getModuleIds();
			//Iteramos a través de las clases.
			for (int moduleId : moduleIds) {
				classes[classIndex] = new Class(classIndex, group.getGroupId(), moduleId);

				// Agregamos un horario a la clase.
				classes[classIndex].addTimeslot(chromosome[chromosomePos]);
				chromosomePos++;

				// Agregamos un salón a la clase.
				classes[classIndex].setRoomId(chromosome[chromosomePos]);
				chromosomePos++;

				// Agregamos un profesor a la clase.
				classes[classIndex].addProfessor(chromosome[chromosomePos]);
				chromosomePos++;

				classIndex++;
			}
		}

		this.classes = classes;
	}

	/**
	 * Regresa el salón a partir de su ID.
	 * 
	 * @param roomId ID del salón.
	 * @return room	Salón.
	 */
	public Room getRoom(int roomId) {
		if (!this.rooms.containsKey(roomId)) {
			System.out.println("No se encontró un salón con el ID " + roomId);
		}
		return (Room) this.rooms.get(roomId);
	}

	/**
	 * Regresa los salón del horario de clases.
	 * */
	public HashMap<Integer, Room> getRooms() {
		return this.rooms;
	}

	/**
	 * Regresa un salón obtenido de manera aleatoria.
	 * 
	 * @return room Salón.
	 */
	public Room getRandomRoom() {
		Object[] roomsArray = this.rooms.values().toArray();
		Room room = (Room) roomsArray[(int) (roomsArray.length * Math.random())];
		return room;
	}

	/**
	 * Regresa un profesor a partir de su ID.
	 * 
	 * @param professorId ID del professor.
	 * @return professor Profesor.
	 */
	public Professor getProfessor(int professorId) {
		return (Professor) this.professors.get(professorId);
	}

	/**
	 * Regresa una materia a partir de su ID.
	 * 
	 * @param moduleId ID de la materia.
	 * @return module Materia.
	 */
	public Module getModule(int moduleId) {
		return (Module) this.modules.get(moduleId);
	}

	/**
	 * Regresa el arreglo de materias a partir del grupo.
	 * 
	 * @param groupId ID del grupo.
	 * @return moduleId Arreglo de materias.
	 */
	public int[] getGroupModules(int groupId) {
		Group group = (Group) this.groups.get(groupId);
		return group.getModuleIds();
	}

	/**
	 * Regresa el grupo a partir de su ID.
	 * 
	 * @param groupId ID del grupo.
	 * @return group Grupo.
	 */
	public Group getGroup(int groupId) {
		return (Group) this.groups.get(groupId);
	}

	/**
	 * Regresa todos los grupos de estudiantes del horario de clases.
	 * 
	 * @return Arreglo de grupos.
	 */
	public Group[] getGroupsAsArray() {
		return (Group[]) this.groups.values().toArray(new Group[this.groups.size()]);
	}

	/**
	 * Regresa un horario de clase con base a su ID.
	 * 
	 * @param timeslotId ID del horario de clases.
	 * @return timeslot Horario de clase.
	 */
	public Timeslot getTimeslot(int timeslotId) {
		return (Timeslot) this.timeslots.get(timeslotId);
	}

	/**
	 * Regresa un horario elegido de manera aleatoria.
	 * 
	 * @return timeslot Horario seleccionado.
	 */
	public Timeslot getRandomTimeslot() {
		Object[] timeslotArray = this.timeslots.values().toArray();
		Timeslot timeslot = (Timeslot) timeslotArray[(int) (timeslotArray.length * Math.random())];
		return timeslot;
	}

	/**
	 * Regresa las clases del horario.
	 * 
	 * @return classes Clases del horario.
	 */
	public Class[] getClasses() {
		return this.classes;
	}

	/**
	 * Regresa el número de clases que deben ser programadas dentro del horario.
	 * 
	 * @return numClasses numero de clases a programar.
	 */
	public int getNumClasses() {
		if (this.numClasses > 0) {
			return this.numClasses;
		}

		int numClasses = 0;
		Group groups[] = (Group[]) this.groups.values().toArray(new Group[this.groups.size()]);
		for (Group group : groups) {
			numClasses += group.getModuleIds().length;
		}
		this.numClasses = numClasses;

		return this.numClasses;
	}

	/**
	 * Calcula el número de restricciones que son violadas por cada cromosoma.
	 * 		--> Conflictos con profesores.
	 * 		--> Confictos con horarios. 
	 * 		--> Conflictos con salones.
	 *  
	 * @return numClashes Número de violaciones generadas por el individuo.
	 */
	public int calcClashes() {
		int clashes = 0;

		for (Class classA : this.classes) {

			// Verifica si un salón ya fue tomado.
			for (Class classB : this.classes) {
				if (classA.getRoomId() == classB.getRoomId() && classA.getTimeslotId() == classB.getTimeslotId()
						&& classA.getClassId() != classB.getClassId()) {
					clashes++;
					break;
				}
			}

			// Verifica si el profesor está disponible.
			for (Class classB : this.classes) {
				if (classA.getProfessorId() == classB.getProfessorId() && classA.getTimeslotId() == classB.getTimeslotId()
						&& classA.getClassId() != classB.getClassId()) {
					clashes++;
					break;
				}
			}
		}

		return clashes;
	}
}