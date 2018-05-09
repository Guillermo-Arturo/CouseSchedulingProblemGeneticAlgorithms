package entregafinal;

public class Individual {
	
	/**
	 * Representa a cada cromosoma con un arreglo de enteros. 
	 */
	private int[] chromosome;
	private double fitness = -1;

	/**
	 * Inicializa los individuos de la población, tomando como base el horario de clases
	 * definido.
	 * 
	 * Hace uso de la clase Timetable para conocer la información fija definida en una instancia
	 * de la clase (clases que serán agendadas, profesores que imparten las clases, los salones
	 * disponibles). La instancia de Timetable sólo nos permite conocer la información de las
	 * clases ya definida y con base a esta información podemos generar a los individuos de
	 * manera aleatoria y crear individuos válidos para el problema.
	 *  
	 * @param timetable Información del horario de clases.
	 *            
	 */
	public Individual(Timetable timetable) {
		int numClasses = timetable.getNumClasses();

		// 1 gen para el salon, 1 gen para el horario, 1 gen para el profesor.
		int chromosomeLength = numClasses * 3;
		// Crea el individuo de manera aleatoria.
		int newChromosome[] = new int[chromosomeLength];
		int chromosomeIndex = 0;
		// Iteramos sobre el arreglo de grupos.
		for (Group group : timetable.getGroupsAsArray()) {
			// Iteramos sobre el arreglo de clases.
			for (int moduleId : group.getModuleIds()) {
				// Agregamos un horario aleatorio.
				int timeslotId = timetable.getRandomTimeslot().getTimeslotId();
				newChromosome[chromosomeIndex] = timeslotId;
				chromosomeIndex++;

				// Agregamos un salón aleatorio.
				int roomId = timetable.getRandomRoom().getRoomId();
				newChromosome[chromosomeIndex] = roomId;
				chromosomeIndex++;

				// Agregamos un profesor aleatorio.
				Module module = timetable.getModule(moduleId);
				newChromosome[chromosomeIndex] = module.getRandomProfessorId();
				chromosomeIndex++;
			}
		}

		this.chromosome = newChromosome;
	}

	/**
	 * Inicializamos un individuo de manera aleatoria.
	 *
	 * Crea un individuo con un cromosoma de tamaño definido. Se utiliza dentro de la 
	 * operación de cruce para inicializar al nuevo hijo generado.
	 * 
	 * 
	 * @param chromosomeLength Longitud de los individuos del cromosoma.
	 */
	public Individual(int chromosomeLength) {
		// Crea un individuo de manera aleatoria.
		int[] individual;
		individual = new int[chromosomeLength];
		
		for (int gene = 0; gene < chromosomeLength; gene++) {
			individual[gene] = gene;
		}
		
		this.chromosome = individual;
	}
    
	/**
	 * Inicializa un individuo con un cromosoma especificad
	 * 
	 * @param chromosome El cromosoma de un individuo ya definido.
	 */
	public Individual(int[] chromosome) {
		// Crea el cromosoma de un individuo.
		this.chromosome = chromosome;
	}

	/**
	 * Regresa el cromosoma de un individuo.
	 * 
	 * @return El cromosoma de un individuo.
	 */
	public int[] getChromosome() {
		return this.chromosome;
	}

	/**
	 * Regresa la longitud de un cromosoma.
	 * 
	 * @return Longitud del cromosoma.
	 */
	public int getChromosomeLength() {
		return this.chromosome.length;
	}

	/**
	 * Fija el gen de un hijo durante la operación de cruce.
	 * 
	 * @param offset Posición dentro del cromosoma donde se agregará el gen.
	 * @param gene Gen del individuo
	 */
	public void setGene(int offset, int gene) {
		this.chromosome[offset] = gene;
	}

	/**
	 * Regresa la sección del cromosoma especificada.
	 * 
	 * @param offset Posición dentro del cromosoma.
	 * @return gene Sección del cromosoma.
	 */
	public int getGene(int offset) {
		return this.chromosome[offset];
	}

	/**
	 * Fija el resultado de evaluación (fitness) para el individuo.
	 * 
	 * @param fitness Valor obtenido de la función fitness.
	 */
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	/**
	 * Regresa el valor obtenido de la evaluación (fitness).
	 *
	 * @return Valor obtenido de la función fitness.
	 */
	public double getFitness() {
		return this.fitness;
	}
	
	/**
	 * Convierte la representación del cromosoma en un string 
	 * 
	 * @return String con la representación del cromosoma de un individuo.
	 * */
	public String toString() {
		String output = "";
		for (int gene = 0; gene < this.chromosome.length; gene++) {
			output += this.chromosome[gene] + ",";
		}
		return output;
	}

	/**
	 * Realiza la busqueda de un gen especifico dentro del cromosoma del individuo.
	 * 
	 * @param gene Gen a buscar dentro del cromosoma.
	 * @return True si el gen existe dentro del cromosoma, False si este no fue encontrado.
	 */
	public boolean containsGene(int gene) {
		for (int i = 0; i < this.chromosome.length; i++) {
			if (this.chromosome[i] == gene) {
				return true;
			}
		}
		return false;
	}
}
