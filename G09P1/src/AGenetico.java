import java.util.Random;

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
	protected boolean maximizar;
	protected boolean esElitista;//tipo de seleccion por torneo
	protected int tipoSel; //tipo de seleccion
	
	public AGenetico(int poblacion, int generaciones, double porcCruces, double porcMutacion, double precision, boolean b, boolean elitismo, int tipoSel2){
		tamPob = poblacion;
		numMaxGen = generaciones;
		probCruce = porcCruces;
		probMut = porcMutacion;
		tolerancia = precision;
		maximizar = b;
		esElitista = elitismo;
		tipoSel = tipoSel2;
	}
	
	public abstract void inicializar();
	
	public void evaluar() {
		double maxFitness = Double.MIN_VALUE;
		double fitness = 0;
		double sumaAptitud = 0;
		double puntAcumulada;
		
		for(int i = 0; i < tamPob; i++){
			fitness = poblacion[i].evalua(); 
			sumaAptitud += fitness;
			if(fitness > maxFitness){
				this.posMejor = i;
				maxFitness = fitness;
			}
		}
		
		puntAcumulada = setPuntuaciones(sumaAptitud);	
		
		if(!maximizar){//Si es una función de minimización
			sumaAptitud = 0;
			for(int i = 0; i < tamPob; i++){
				fitness = maxFitness - poblacion[i].evalua();
				poblacion[i].setFitness(fitness);
				sumaAptitud += fitness;
			}
			for(int i = 0; i < tamPob; i++){
				if(poblacion[i].getFitness() > maxFitness){
					maxFitness = poblacion[i].getFitness();
					posMejor = i;
				}
			}
			puntAcumulada = setPuntuaciones(sumaAptitud);
		}
		
		if(maxFitness > mejorAbs) this.mejorAbs = maxFitness;
		this.elMejor = this.poblacion[posMejor];
		
		VistaPrincipal.addData(mejorAbs, maxFitness, (sumaAptitud/tamPob));

	}

	private double setPuntuaciones(double sumaAptitud) {
		double puntAcumulada = 0;
		for(int i = 0; i < tamPob; i++){
			this.poblacion[i].setPunt((this.poblacion[i].getFitness() / sumaAptitud));
			this.poblacion[i].setPuntAcum(poblacion[i].getPunt() + puntAcumulada);
			puntAcumulada += poblacion[i].getPunt();
		}
		return puntAcumulada;		
	}
	
	public abstract void seleccion(int tipo);
	
	public abstract void reproduccion();
	
	public void mutacion() {
		boolean mutado;
		int i, j;
		double prob;
		Random rnd = new Random();
		
		for(i = 0; i < this.tamPob; i++){			
			Gen gen = poblacion[i].genes[0];
			mutado = false;
			
			for(j = 0; j < gen.getLongAlelo(); j++)
			{
				prob = rnd.nextDouble();
				// mutan los genes con prob<prob_mut
				if (prob < probMut){
					mutado = true;
					if(gen.getPosAlelo(j)){
						gen.setPosAlelo(j, false);
					}
					else{
						gen.setPosAlelo(j, true);
					}
					
				}
			}
			if (mutado){
				poblacion[i].fitness = poblacion[i].evalua();
			}
		}
	}
}
