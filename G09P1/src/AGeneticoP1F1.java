import java.util.Random;

public class AGeneticoP1F1 extends AGenetico {
	
	public AGeneticoP1F1(int poblacion, int generaciones, float porcCruces, float porcMutacion, float tolerancia)
	{
		super(poblacion, generaciones, porcCruces, porcMutacion, tolerancia);
	}

	public void inicializar() {
		this.poblacion = new Cromosoma[tamPob];
		for(int i = 0; i < tamPob; i++)
			this.poblacion[i] = new CromosomaP1F1(tolerancia);		
	}

	@Override
	public void evaluar() {
		double puntAcumulada = 0;
		double maxFitness = -250f;
		int posMax = -1;
		for(int i = 0; i < tamPob; i++){
			double fitness = this.poblacion[i].getFitness();
			if(fitness > maxFitness){
				posMax = i;
				maxFitness = fitness;
			}
			puntAcumulada = puntAcumulada + this.poblacion[i].getFitness();
		}
		float puntRelAcumulada = 0f;
		for(int i = 0; i < tamPob; i++){
			puntRelAcumulada += this.poblacion[i].getFitness() / puntAcumulada;
			this.poblacion[i].setPunt((this.poblacion[i].getFitness() / puntAcumulada));
			this.poblacion[i].setPuntAcum(puntRelAcumulada);
		}
		this.elMejor = this.poblacion[posMax];
		this.posMejor = posMax;
	}

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
		float prob; // Probabilidad de supervivencia
		int posSuperv = 0; // Posicion del superviviente
		for(int i = 0; i < tamPob; i++){
			Random rnd = new Random();
			prob = rnd.nextFloat();
			while((prob > poblacion[posSuperv].puntAcum) && (posSuperv < tamPob))
			{
				selSuperv[i] = posSuperv;
				posSuperv++;
			}
		}
		Cromosoma nuevaPob[] = new CromosomaP1F1[tamPob];
		for(int i = 0; i < tamPob; i++){
			if(selSuperv[i] != -1)
				nuevaPob[i] = poblacion[selSuperv[i]];
		}
	}
	
	private void seleccionTorneo(){
		Cromosoma subpoblacion[] = new Cromosoma[2]; //Conjunto a valorar
		Cromosoma poblacionAux[] = new Cromosoma[tamPob]; //Nueva generación
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
		float prob;
		Cromosoma hijo1 = new CromosomaP1F1(tolerancia);
		Cromosoma hijo2 = new CromosomaP1F1(tolerancia);
		Random rnd = new Random();
		
		for(int i = 0; i < tamPob; i++){
			prob = rnd.nextFloat();
			if(prob < probCruce){
				selCruce[numSelCruce] = i;
				numSelCruce++;
			}
		}
		if((numSelCruce % 2) == 1) numSelCruce--;
		
		puntoCruce = rnd.nextInt(9);
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
		for(i = 0; i < CromosomaP1F1.N_GENES && k < puntoCruce; i++)
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
		hijo1.evalua(0);
		hijo2.evalua(0);
	}

	@Override
	public void mutacion() {
		boolean mutado;
		int i, j;
		float prob;
		Random rnd = new Random();
		
		for(i = 0; i < this.tamPob; i++){			
			Gen gen = poblacion[i].genes[0];
			mutado = false;
			
			for(j = 0; j < gen.getLongAlelo(); j++)
			{
				prob = rnd.nextFloat();
				// mutan los genes con prob<prob_mut
				if (prob < probMut){
					mutado = true;
					if(gen.getPosAlelo(i)){
						gen.setPosAlelo(i, false);
					}
					else{
						gen.setPosAlelo(i, true);
					}
					
				}
			}
			if (mutado){
				poblacion[i].fitness = poblacion[i].evalua(0);
			}
		}
	}
}
