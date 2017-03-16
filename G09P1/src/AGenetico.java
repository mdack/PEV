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
	protected static final double P = 0.75;
	private Cromosoma[] elite;
	protected int tamElite;
	
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
		double optFitness;
		double sumaAptitud = 0;
		double fitness = 0;
		
		if(!maximizar){//Si es una función de minimización
			optFitness = Double.MAX_VALUE;
			
			for(int i = 0; i < tamPob; i++){
				fitness = poblacion[i].evalua(); 
				sumaAptitud += fitness;
				if(fitness < optFitness){
					this.posMejor = i;
					optFitness = fitness;
				}
			}
			if(optFitness < mejorAbs) this.mejorAbs = optFitness;
			this.elMejor = this.poblacion[posMejor];
			setPuntuaciones(sumaAptitud);
		}
		else
		{
			optFitness = Double.MIN_VALUE;
			
			for(int i = 0; i < tamPob; i++){
				fitness = poblacion[i].evalua();
				poblacion[i].setFitness(fitness);
				sumaAptitud += fitness;
				if(fitness > optFitness){
					optFitness = fitness;
					this.posMejor = i;
				}
			}
			if(optFitness > mejorAbs) this.mejorAbs = optFitness;
			this.elMejor = this.poblacion[posMejor];
			setPuntuaciones(sumaAptitud);
		}
		
		VistaPrincipal.addData(mejorAbs, optFitness, (sumaAptitud/tamPob));
	}

	private void setPuntuaciones(double sumaAptitud) {
		double puntAcumulada = 0;
		for(int i = 0; i < tamPob; i++){
			this.poblacion[i].setPunt((this.poblacion[i].getFitness() / sumaAptitud));
			this.poblacion[i].setPuntAcum(poblacion[i].getPunt() + puntAcumulada);
			puntAcumulada += poblacion[i].getPunt();
		}		
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
	
	public int getPosMejor(){
		return this.posMejor;
	}
	
	public int getNumMaxGen(){
		return this.numMaxGen;
	}
	
	public String toString() {
		String cadena = " ";
		
		// He comentado esto porque ralentiza mucho la ejecucion
		// y creo que es innecesario.
		
//		for(int i = 0; i < tamPob; i++){
//			cadena += ("Individuo " + (i+1) + "\n");
//			cadena += poblacion[i].toString();
//			cadena += ("\n");
//		}
		
		cadena += "* Mejor de generación: \n";
		cadena += this.elMejor.toString();
		cadena += "\n";
		
		cadena += "* Mejor absoluto: ";
		cadena += this.mejorAbs + "\n";
		cadena += "\n";
		
		return cadena;
	}
//	
//	public void separaMejores(int tamE) {
//		Cromosoma[] eliteAux = new Cromosoma[tamE];	
//		
//		for(int i = 0; i < tamE; i++){
//			eliteAux[i] = obtieneMejor();
//		}
//		tamElite = tamE; 
//		elite = eliteAux;
//	}
//
//	private Cromosoma obtieneMejor() {
//		double mejorFitness = Double.MAX_VALUE;
//		if(maximizar) mejorFitness = Double.MIN_VALUE;
//		int pos_mejor = 0;
//		Cromosoma aux = null;
//		
//		for(int i = 0; i < tamPob; i++){
//			if(maximizar){
//				if(poblacion[i].getFitness() > mejorFitness){
//					pos_mejor = i;
//					mejorFitness = poblacion[i].getFitness();
//				}
//			}else{
//				if(poblacion[i].getFitness() < mejorFitness){
//					pos_mejor = i;
//					mejorFitness = poblacion[i].getFitness();
//				}
//			}
//		}
//		aux = poblacion[pos_mejor];
//		
//		mueveIzquierda(pos_mejor);
//		
//		return aux;
//	}
//
//	private void mueveIzquierda(int pos_mejor) {
//		tamPob--;
//		for(int i = pos_mejor; i < tamPob; i++){
//			poblacion[i] = poblacion[i+1];
//		}
//	}
//
//	public void incluyeElite() {
//		int tam = tamPob;
//		int tamPobAux = tamPob + tamElite;
//		int pos = 0;
//		Cromosoma[] auxPob = new Cromosoma[tamPobAux];
//		
//		for(int i = 0; i < tamPobAux; i++){
//			if(i < tam) auxPob[i] = poblacion[i];
//			else{
//				auxPob[i] = elite[pos];
//				pos++;
//			}
//		}
//		tamPob = tamPobAux;
//		poblacion = auxPob;
//	}
}
