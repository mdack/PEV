
public abstract class AGenetico {
	
	protected Cromosoma[] poblacion; // Poblacion
	protected int tamPob; // Tamaño de la poblacion
	protected int numGeneracion; // Numero de generacion actual
	protected int numMaxGen; // Numero maximo de generaciones
	protected Cromosoma elMejor; // Mejor cromosoma de la poblacion
	protected int posMejor; // Posicion en la poblacion
	protected double probCruce; // Probabilidad de cruce
	protected double probMut; // Probabilidad de mutacion
	protected double tolerancia; // Tolerancia
	protected double mejorAbs; // Fitness mejor absoluto.
	
	public AGenetico(int poblacion, int generaciones, double porcCruces, double porcMutacion, double precision){
		tamPob = poblacion;
		numMaxGen = generaciones;
		probCruce = porcCruces;
		probMut = porcMutacion;
		tolerancia = precision;
	}
	
	public abstract void inicializar();
	
	public abstract void evaluar();
	
	public abstract void seleccion(int tipo);
	
	public abstract void mutacion();
}
