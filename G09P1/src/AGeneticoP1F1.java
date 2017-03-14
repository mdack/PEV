import java.util.Random;

public class AGeneticoP1F1 extends AGenetico {
	
	public AGeneticoP1F1(int poblacion, int generaciones, double porcCruces, double porcMutacion, double tolerancia)
	{
		super(poblacion, generaciones, porcCruces, porcMutacion, tolerancia, false);
		inicializar();
		evaluar();
		for(int i = 0; i < generaciones; i++)
		{
			seleccion(0);
			reproduccion();
			mutacion();
			evaluar();
		}
	}

	public void inicializar() {
		this.poblacion = new Cromosoma[tamPob];
		for(int i = 0; i < tamPob; i++)
			this.poblacion[i] = new CromosomaP1F1(tolerancia);		
	}

//	@Override
//	public void evaluar() {
//		double puntAcumulada = 0;
//		double maxFitness = -250.0;
//		int posMax = -1;
//		for(int i = 0; i < tamPob; i++){
//			double fitness = this.poblacion[i].getFitness();
//			if(fitness > maxFitness){
//				posMax = i;
//				maxFitness = fitness;
//			}
//			puntAcumulada = puntAcumulada + this.poblacion[i].getFitness();
//		}
//		if(maxFitness > mejorAbs) this.mejorAbs = maxFitness;
//		double puntRelAcumulada = 0.0;
//		for(int i = 0; i < tamPob; i++){
//			puntRelAcumulada += this.poblacion[i].getFitness() / puntAcumulada;
//			this.poblacion[i].setPunt((this.poblacion[i].getFitness() / puntAcumulada));
//			this.poblacion[i].setPuntAcum(puntRelAcumulada);
//		}
//		this.elMejor = this.poblacion[posMax];
//		this.posMejor = posMax;
//		VistaPrincipal.addData(mejorAbs, maxFitness, (puntAcumulada/tamPob));
//	}

	@Override
	public void seleccion(int tipo) {
		switch(tipo){
		case 0:
			seleccionRuleta();
			break;
		case 1:
			seleccionTorneo();
			break;
		}
	}
	
	private void seleccionRuleta(){
		int selSuperv[] = new int[tamPob]; // Supervivientes
		for(int i = 0; i < tamPob; i++) selSuperv[i] = -1;
		double prob; // Probabilidad de supervivencia
		
		for(int i = 0; i < tamPob; i++)
		{
			Random rnd = new Random();
			prob = rnd.nextDouble();
			for(int j = 0; j < tamPob; j++){
				if(j == 0)
				{
					if(prob >= 0.0 && prob <= poblacion[j].puntAcum)
					{
						selSuperv[i] = j;
						j = tamPob;
					}
				}
				else
				{
					if(prob >= poblacion[j-1].puntAcum && prob <= poblacion[j].puntAcum)
					{
						selSuperv[i] = j;
						j = tamPob;
					}
				}
			}
		}
		Cromosoma nuevaPob[] = new CromosomaP1F1[tamPob];
		for(int i = 0; i < tamPob; i++){
			if(selSuperv[i] != -1)
				nuevaPob[i] = poblacion[selSuperv[i]];
		}
		poblacion = nuevaPob;
	}
	
	private void seleccionTorneo(){
		Cromosoma subpoblacion[] = new CromosomaP1F1[2]; //Conjunto a valorar
		Cromosoma poblacionAux[] = new CromosomaP1F1[tamPob]; //Nueva generación
		Random rnd = new Random();
		int posElegida;
		
		for(int j = 0; j < tamPob; j++){
			
			for(int i = 0; i < 2; i++){ //Seleccionamos 2 individuos al azar
				posElegida = (rnd.nextInt() * tamPob);
				subpoblacion[i] = poblacion[posElegida];
			}
			
			if(subpoblacion[0].fitness > subpoblacion[1].fitness){
				poblacionAux[j] = subpoblacion[0];
			}
			else{
				poblacionAux[j] = subpoblacion[1];
			}
		}
		
		poblacion = poblacionAux;
	}
	
	public void reproduccion(){
		int selCruce[] = new int[tamPob];
		
		int numSelCruce = 0;
		int puntoCruce;
		double prob;
		Cromosoma hijo1 = new CromosomaP1F1(tolerancia);
		Cromosoma hijo2 = new CromosomaP1F1(tolerancia);
		Random rnd = new Random();
		
		for(int i = 0; i < tamPob; i++){
			prob = rnd.nextDouble();
			if(prob < probCruce){
				selCruce[numSelCruce] = i;
				numSelCruce++;
			}
		}
		if((numSelCruce % 2) == 1) numSelCruce--;
		
		puntoCruce = rnd.nextInt(hijo1.genes[0].getLongAlelo());
		for(int i = 0; i < numSelCruce; i += 2)
		{
			cruce(poblacion[selCruce[i]], poblacion[selCruce[i+1]], hijo1, hijo2, puntoCruce);
			poblacion[selCruce[i]] = hijo1;
			poblacion[selCruce[i+1]] = hijo2;
		}
	}
	
	private void cruce(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1, Cromosoma hijo2, int puntoCruce)
	{
		int i = 0; // Contador de gen.
		int j = 0; // Contador de bit dentro de gen.
		int k = 0; // Contador de posicion general en cromosoma.
		// Primera fase
		while(i < CromosomaP1F1.N_GENES && k < puntoCruce)
		{
			boolean[] aleloHijo1 = hijo1.genes[i].getAlelo();
			boolean[] aleloHijo2 = hijo2.genes[i].getAlelo();
			boolean[] aleloPadre1 = padre1.genes[i].getAlelo();
			boolean[] aleloPadre2 = padre2.genes[i].getAlelo();
			if(k < puntoCruce)
			{
				for(j = 0; j < hijo1.genes[i].getLongAlelo() && k < puntoCruce; j++)
				{
					aleloHijo1[j] = aleloPadre1[j];
					aleloHijo2[j] = aleloPadre2[j];
					k++;
				}
			}
			hijo1.genes[i].setAlelo(aleloHijo1);
			hijo2.genes[i].setAlelo(aleloHijo2);
		}
		// Segunda fase
		while(i < CromosomaP1F1.N_GENES)
		{
			boolean[] aleloHijo1 = hijo1.genes[i].getAlelo();
			boolean[] aleloHijo2 = hijo2.genes[i].getAlelo();
			boolean[] aleloPadre1 = padre1.genes[i].getAlelo();
			boolean[] aleloPadre2 = padre2.genes[i].getAlelo();
			while(j < hijo1.genes[i].getLongAlelo())
			{
				aleloHijo1[j] = aleloPadre2[j];
				aleloHijo2[j] = aleloPadre1[j];
				j++;
			}
			hijo1.genes[i].setAlelo(aleloHijo1);
			hijo2.genes[i].setAlelo(aleloHijo2);
			i++;
			j = 0;
		}
		
		// Evaluacion
		hijo1.evalua();
		hijo2.evalua();
	}

}
