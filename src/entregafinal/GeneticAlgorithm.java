package entregafinal;


public class GeneticAlgorithm {

	private int populationSize;
	private double mutationRate;
	private double crossoverRate;
	private int elitismCount;
	protected int tournamentSize;

	public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount,
			int tournamentSize) {

		this.populationSize = populationSize;
		this.mutationRate = mutationRate;
		this.crossoverRate = crossoverRate;
		this.elitismCount = elitismCount;
		this.tournamentSize = tournamentSize;
	}

	/**
	 * Inicializa la población inicial
	 * 
	 * @param chromosomeLength Longitud de cada individuo dentro de un cromosoma.
	 *            
	 * @return population Población inicial
	 */
	public Population initPopulation(Timetable timetable) {
		// Initialize population
		Population population = new Population(this.populationSize, timetable);
		return population;
	}

	/**
	 * Checa si la población contiene una condición de paro. En este caso, el algoritmo se 
	 * detiene si el número de generaciones creadas rebasa el número máximo de generaciones
	 * a evaluar.
	 * 
	 * @param generationsCount Número de generaciones pasadas.
	 *            
	 * @param maxGenerations Número de generaciones a evaluar.
	 *            
	 * @return boolean True si la condición de paro se cumple, de lo contrario, False.
	 */
	public boolean isTerminationConditionMet(int generationsCount, int maxGenerations) {
		return (generationsCount > maxGenerations);
	}

	/**
	 * Checa si la población contiene una condición de paro. En este caso, el algoritmo
	 * se detiene si el resultado de la funcción fitness es igual a 1.0. Es decir, se encontró
	 * la mejor solución.
	 *
	 * @param population Población a evaluar.
	 * @return boolean True si la condición de paro se cumple, de lo contrario, False.
	 */
	public boolean isTerminationConditionMet(Population population) {
		return population.getFittest(0).getFitness() == 1.0;
	}

	/**
	 * Calcula el valor fitness de cada individuo dentro de la población
	 * 
	 * @param individual Individuo a evaluar.
	 * @param timetable Definición del horario de clases.
	 * @return fitness Resultado de la evaluación del individuo.
	 */
	public double calcFitness(Individual individual, Timetable timetable) {

		// Create new timetable object to use -- cloned from an existing timetable
		// Creamos un Timetable temporal para no manipular el original.
		Timetable temp = new Timetable(timetable);
		temp.createClasses(individual);

		// Calculamos las función fitness.
		int clashes = temp.calcClashes();
		double fitness = 1 / (double) (clashes + 1);

		// Asignamos el valor de su evaluación a cada individuo.
		individual.setFitness(fitness);

		return fitness;
	}

	/**
	 * Evalua la población
	 * 
	 * @param population Población a ser evaluada.
	 * @param timetable Horario de clases.
	 */
	public void evalPopulation(Population population, Timetable timetable) {
		double populationFitness = 0;

		// Iteramos sobre la población para evaluar a los individuos y sumar su fitness
		// total por población.
		for (Individual individual : population.getIndividuals()) {
			populationFitness += this.calcFitness(individual, timetable);
		}

		population.setPopulationFitness(populationFitness);
	}

	/**
	 * Selecciona los padres para realizar la operación de cruce. Implementa la selección
	 * por torneo, es decir, se selecciona a N individuos y los ponemos a competir entre
	 * ellos. Al final elegimos a los mejores evaluados.
	 * 
	 * @param population Población a evaluar.
	 * @return El mejor individuo seleccionado como padre.
	 */
	public Individual selectParent(Population population) {
		// Generamos el torneo de individuos.
		Population tournament = new Population(this.tournamentSize);

		// Selecionamos a los individuos de manera aleatoria para el torneo.
		population.shuffle();
		for (int i = 0; i < this.tournamentSize; i++) {
			Individual tournamentIndividual = population.getIndividual(i);
			tournament.setIndividual(i, tournamentIndividual);
		}

		return tournament.getFittest(0);
	}


	/**
     * Aplica el operador de mutación a la población.
     * 
     * @param population Población a ser modificada.
     * @param timetable Horario de clases.
     * @return Población modificada.
     */
	public Population mutatePopulation(Population population, Timetable timetable) {
		// Inicializamos la población.
		Population newPopulation = new Population(this.populationSize);

		// Iteramos sobre la población actual tomando en cuenta su resultado de evaluación.
		for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
			Individual individual = population.getFittest(populationIndex);

			// Creamos un individuo de manera aleatoria para intercambiar genes con él.
			Individual randomIndividual = new Individual(timetable);

			// Iteramos sobre los genes del individuo.
			for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
				// Se realiza una selección por elitismo, es decir, si el individuo es considerado
				// dentro de la élite de la población, este no se modifica.
				if (populationIndex > this.elitismCount) {
					// En caso de no ser un individuo élite, se determina de manera aleatoria
					// si este será modificado aplicando el operador de mutación.
					if (this.mutationRate > Math.random()) {
						// Se realiza el reemplazo de los genes del individuo.
						individual.setGene(geneIndex, randomIndividual.getGene(geneIndex));
					}
				}
			}

			// Agregamos el nuevo individuo generado a la población.
			newPopulation.setIndividual(populationIndex, individual);
		}

		return newPopulation;
	}

    /**
     * Aplica la operación de cruce a la población.
     * 
     * @param population Población a la que se aplicará el operador de cruce.
     * @return Población que fue cruzada.
     */
	public Population crossoverPopulation(Population population) {
		// Inicializamos la población.
		Population newPopulation = new Population(population.size());

		// Iteramos sobre la población actual tomando en cuenta su resultado de evaluación.
		for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
			Individual parent1 = population.getFittest(populationIndex);

			// Se determina de manera aleatoria si al individuo, dentro de la población, se le
			// aplicará la operación de cruce. 
			if (this.crossoverRate > Math.random() && populationIndex >= this.elitismCount) {
				// Initialize offspring
				// Crea un nuevo individio (hijo) para almacenar el resultado del cruce.
				Individual offspring = new Individual(parent1.getChromosomeLength());
				
				// Se selecciona al segundo padre.
				Individual parent2 = selectParent(population);

				// Iteramos sobre el genoma del primer padre.
				for (int geneIndex = 0; geneIndex < parent1.getChromosomeLength(); geneIndex++) {
					// Usamos la mitad de los genes del primer padre y la mitad de los genes del 
					// segundo padre para hacer el intercambio de genes.
					if (0.5 > Math.random()) {
						offspring.setGene(geneIndex, parent1.getGene(geneIndex));
					} else {
						offspring.setGene(geneIndex, parent2.getGene(geneIndex));
					}
				}

				// Agregamos al nuevo individuo generado (hijo) dentro de la población.
				newPopulation.setIndividual(populationIndex, offspring);
			} else {
				// Si el individuo no fue seleccionado para cruzarse, se agrega a la población
				// sin aplicar la operación de cruce.
				newPopulation.setIndividual(populationIndex, parent1);
			}
		}

		return newPopulation;
	}
}
