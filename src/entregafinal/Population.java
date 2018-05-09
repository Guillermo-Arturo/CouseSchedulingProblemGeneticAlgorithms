package entregafinal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Population {
	private Individual population[];
	private double populationFitness = -1;

	/**
	 * Inicializa una población vacia de individuos.
	 * 
	 * @param populationSize Tamaño de la población.
	 */
	public Population(int populationSize) {
		// Define la población inicial.
		this.population = new Individual[populationSize];
	}
	
	/**
     * Inicializa la población de individuos.
     * 
     * @param populationSize Tamaño de la población.
     * @param timetable Información del horario de clases.
     */
	public Population(int populationSize, Timetable timetable) {
		// Inicializa la población.
		this.population = new Individual[populationSize];

		// Iteramos a través de la población.
		for (int individualCount = 0; individualCount < populationSize; individualCount++) {
			// Creamos un individuo.
			Individual individual = new Individual(timetable);
			// Agregamos al nuevo individuo a la población.
			this.population[individualCount] = individual;
		}
	}


	/**
	 * Inicializamos la población de individuos.
	 * 
	 * @param populationSize Tamaño de la población.
	 * 
	 * @param chromosomeLength Tamaño del cromosoma de un individuo.
	 */
	public Population(int populationSize, int chromosomeLength) {
		// Inicializa la población.
		this.population = new Individual[populationSize];

		// Iteramos a través de la población.
		for (int individualCount = 0; individualCount < populationSize; individualCount++) {
			// Creamos un nuevo individuo.
			Individual individual = new Individual(chromosomeLength);
			// Agregamos al nuevo individuo a la población.
			this.population[individualCount] = individual;
		}
	}

	/**
	 * Regresa los individuos que pertenecen a la población.
	 * 
	 * @return individuals Individuos dentro de la población.
	 */
	public Individual[] getIndividuals() {
		return this.population;
	}

	/**
	 * Regresa el resultado de la evaluación (fitness) del individuo.
	 * 
	 * @param offset Sección dentro del cromosoma
	 * @return individual Resultado de la función fitness del individuo.
	 */
	public Individual getFittest(int offset) {
		// Ordena a los individuos de la población con base al resultado fitness.
		Arrays.sort(this.population, new Comparator<Individual>() {
			@Override
			public int compare(Individual o1, Individual o2) {
				if (o1.getFitness() > o2.getFitness()) {
					return -1;
				} else if (o1.getFitness() < o2.getFitness()) {
					return 1;
				}
				return 0;
			}
		});

		// Regresa el resultado fitness del individuo.
		return this.population[offset];
	}

	/**
	 * Fija el resultado de fitness a la población (Suma de la fitness de cada individuo).
	 * 
	 * @param fitness Valor fitness total de la población.
	 */
	public void setPopulationFitness(double fitness) {
		this.populationFitness = fitness;
	}

	/**
	 * Regresa el valor fitness de la población.
	 * 
	 * @return populationFitness Valor fitness total de la población.
	 */
	public double getPopulationFitness() {
		return this.populationFitness;
	}

	/**
	 * Regresa el tamaño de la población.
	 * 
	 * @return size Tamaño de la población.
	 */
	public int size() {
		return this.population.length;
	}

	/**
	 * Fija la posición del individuo dentro del cromosoma.
	 * 
	 * @param individual Individuo de la población.
	 * @param offset Posición dentro del cromosoma.
	 * @return individual Individuo de la población.
	 */
	public Individual setIndividual(int offset, Individual individual) {
		return population[offset] = individual;
	}

	/**
	 * Regresa un individuo dentro dentro de la población.
	 * 
	 * @param offset Posición dentro del comosoma.
	 * @return individual Individuo de la población.
	 */
	public Individual getIndividual(int offset) {
		return population[offset];
	}

	/**
	 * Mezcla los individuos dentro de la población.
	 */
	public void shuffle() {
		Random rnd = new Random();
		for (int i = population.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			Individual a = population[index];
			population[index] = population[i];
			population[i] = a;
		}
	}

}