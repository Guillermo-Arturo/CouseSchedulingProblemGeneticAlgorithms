package entregafinal;

public class SchedulingProblemGA {

    public static void main(String[] args) {

    	//Inicializamos el objeto Timetable para el manejo de los horarios.
        Timetable timetable = initializeTimetable();
        
        // Inicializamos el algoritmo genético (los detalles de implementación se definen en su respectiva clase)
        GeneticAlgorithm ga = new GeneticAlgorithm(100, 0.01, 0.9, 2, 5);
        
        //Inicializamos la población
        Population population = ga.initPopulation(timetable);
        
        // Evaluamos la población generada.
        ga.evalPopulation(population, timetable);
        
        // Permite llevar un conteo del número de generaciones evaluadas.
        int generation = 1;
        
        // Start evolution loop
        while (ga.isTerminationConditionMet(generation, 1000) == false
            && ga.isTerminationConditionMet(population) == false) {
         
            System.out.println("Generación " + generation + " Mejor fitness: " + population.getFittest(0).getFitness());

            // Aplicamos el operador de cruce a cada población.
            population = ga.crossoverPopulation(population);

            // Aplicamos el operador de mutación a cada población.
            population = ga.mutatePopulation(population, timetable);

            // Evaluamos la población generada.
            ga.evalPopulation(population, timetable);

            generation++;
        }

        timetable.createClasses(population.getFittest(0));
        System.out.println();
        System.out.println("La solución se encontró en la generación # " + generation);
        System.out.println("Fitness de la solución: " + population.getFittest(0).getFitness());

        // Se imprimen las clases de la solución.
        System.out.println();
        Class classes[] = timetable.getClasses();
        int classIndex = 1;
        for (Class bestClass : classes) {
            System.out.println("# " + classIndex + ":");
            System.out.println("ID Clase: " + 
                    timetable.getModule(bestClass.getModuleId()).getModuleId());
            System.out.println("Grupo: " + 
                    timetable.getGroup(bestClass.getGroupId()).getGroupId());
            System.out.println("Salón: " + 
                    timetable.getRoom(bestClass.getRoomId()).getRoomNumber());
            System.out.println("Profesor: " + 
                    timetable.getProfessor(bestClass.getProfessorId()).getProfessorId());
            System.out.println("Horario: " + 
                    timetable.getTimeslot(bestClass.getTimeslotId()).getTimeslot());
            System.out.println("-----");
            classIndex++;
        }
    }

    /**
     * Creamos una Timetable con toda la información necesaria de los cursos.
     */
	private static Timetable initializeTimetable() {

		Timetable timetable = new Timetable();

		// Definimos los salones.
		timetable.addRoom(1, "A1");
		timetable.addRoom(2, "B1");
		timetable.addRoom(4, "D1");
		timetable.addRoom(5, "F1");

		// Definimos los horarios de clase.
		timetable.addTimeslot(1, "9:00 - 11:00");
		timetable.addTimeslot(2, "11:00 - 13:00");
		timetable.addTimeslot(3, "13:00 - 15:00");
		timetable.addTimeslot(4, "9:00 - 11:00");
		timetable.addTimeslot(5, "11:00 - 13:00");
		timetable.addTimeslot(6, "13:00 - 15:00");
		timetable.addTimeslot(7, "9:00 - 11:00");
		timetable.addTimeslot(8, "11:00 - 13:00");
		timetable.addTimeslot(9, "13:00 - 15:00");
		timetable.addTimeslot(10, "9:00 - 11:00");
		timetable.addTimeslot(11, "11:00 - 13:00");
		timetable.addTimeslot(12, "13:00 - 15:00");
		timetable.addTimeslot(13, "9:00 - 11:00");
		timetable.addTimeslot(14, "11:00 - 13:00");
		timetable.addTimeslot(15, "13:00 - 15:00");

		// Definimos los profesores.
		timetable.addProfessor(1);
		timetable.addProfessor(2);
		timetable.addProfessor(3);
		timetable.addProfessor(4);

		// Definimos las materias que puede impartir cada profesor.
		timetable.addModule(1, new int[] { 1, 2 });
		timetable.addModule(2, new int[] { 1, 3 });
		timetable.addModule(3, new int[] { 1, 2 });
		timetable.addModule(4, new int[] { 3, 4 });
		timetable.addModule(5, new int[] { 4 });
		timetable.addModule(6, new int[] { 1, 4 });

		// Definimos los grupos de estudiantes y las materias que pueden tomar.
		timetable.addGroup(1, new int[] { 1, 3, 4 });
		timetable.addGroup(2, new int[] { 2, 3, 5, 6 });
		timetable.addGroup(3, new int[] { 3, 4, 5 });
		timetable.addGroup(4, new int[] { 1, 4 });
		timetable.addGroup(5, new int[] { 2, 3, 5 });
		timetable.addGroup(6, new int[] { 1, 4, 5 });
		timetable.addGroup(7, new int[] { 1, 3 });
		timetable.addGroup(8, new int[] { 2, 6 });
		timetable.addGroup(9, new int[] { 1, 6 });
		timetable.addGroup(10, new int[] { 3, 4 });
		
		return timetable;
	}
}
