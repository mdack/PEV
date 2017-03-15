import java.util.Random;

public class AGeneticoP1F1 extends AGenetico {
	
	public AGeneticoP1F1(int poblacion, int generaciones, double porcCruces, double porcMutacion, double tolerancia, boolean elitismo, int tipoSel)
	{
		super(poblacion, generaciones, porcCruces, porcMutacion, tolerancia, false, elitismo, tipoSel);
	}

	public void inicializar() {
		this.poblacion = new Cromosoma[tamPob];
		for(int i = 0; i < tamPob; i++)
			this.poblacion[i] = new CromosomaP1F1(tolerancia);		
	}

	@Override
	public void seleccion(int tipo) {
		if(tipo == 0){
			seleccionRuleta();
		}else if(tipo == 1){
			seleccionEstocastico();
		}else{
			seleccionTorneo(tipo);
		}
	}
	
	private void seleccionEstocastico() {
		int[] sel_super = new int[tamPob];
		double suma = getRandom();
		int pos_super = 0;
		
		for(int i = 0; i < tamPob; i++){
			while((suma > poblacion[pos_super].getPuntAcum()) && (pos_super < tamPob)){
				pos_super++;
				sel_super[i] = pos_super;
			}
			suma += (1.0/tamPob);
		}
		
		//Se genera la población intermedia
		Cromosoma[] nuevaPob = new CromosomaP1F1[tamPob];
		for(int i = 0; i < tamPob; i++){
			nuevaPob[i] = poblacion[sel_super[i]];
		}
		
		poblacion = nuevaPob;
	}

	private double getRandom() {
		double n = Math.random();
		double marca = 1.0 / tamPob;
		
		while(n > marca){
			n = Math.random();
		}
		
		return n;
	}

	private void seleccionRuleta(){
		int[] sel_super = new int[tamPob];
		double prob;
		int pos_super;
		
		for(int i = 0; i < tamPob; i++){
			prob = Math.random();
			pos_super = 0;
			
			while((prob > poblacion[pos_super].getPuntAcum()) && (pos_super < tamPob)){
				pos_super++;
				sel_super[i] = pos_super;
			}
		}
		//Se genera la población intermedia
		Cromosoma[] nuevaPob = new CromosomaP1F1[tamPob];
		for(int i = 0; i < tamPob; i++){
			nuevaPob[i] = poblacion[sel_super[i]];
		}
		
		poblacion = nuevaPob;
	}

	private void seleccionTorneo(int tipo){
		Cromosoma subpoblacion[] = new CromosomaP1F1[2]; //Conjunto a valorar
		Cromosoma poblacionAux[] = new CromosomaP1F1[tamPob]; //Nueva generación
		Random rnd = new Random();
		int posElegida;
		double prob;
		
		for(int j = 0; j < tamPob; j++){
			
			for(int i = 0; i < 2; i++){ //Seleccionamos 2 individuos al azar
				posElegida = (int) (rnd.nextDouble() * tamPob);
				subpoblacion[i] = poblacion[posElegida];
			}
			
			if(tipo == 2){
				poblacionAux[j] = getMejorSubpoblacion(subpoblacion);
			}else{
				prob = rnd.nextDouble();
				if(prob > P){
					poblacionAux[j] = getMejorSubpoblacion(subpoblacion);
				}else{
					poblacionAux[j] = getMenorSubpoblacion(subpoblacion);
				}
			}
		}
		
		poblacion = poblacionAux;
	}
	
	private Cromosoma getMenorSubpoblacion(Cromosoma[] subpoblacion) {
		if(subpoblacion[0].fitness < subpoblacion[1].fitness){
			return subpoblacion[0];
		}
		else{
			return subpoblacion[1];
		}
	}

	private Cromosoma getMejorSubpoblacion(Cromosoma[] subpoblacion) {
		if(subpoblacion[0].fitness > subpoblacion[1].fitness){
			return subpoblacion[0];
		}
		else{
			return subpoblacion[1];
		}
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
		
		puntoCruce = rnd.nextInt(poblacion[0].longitud);
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
