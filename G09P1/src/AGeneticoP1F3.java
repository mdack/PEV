import java.util.Random;

public class AGeneticoP1F3 extends AGenetico {

	public AGeneticoP1F3(int poblacion, int generaciones, double porcCruces, double porcMutacion, double precision, boolean elitismo, int tipoSel) {
		super(poblacion, generaciones, porcCruces, porcMutacion, precision, true, elitismo, tipoSel);
		this.mejorAbs = Double.MIN_VALUE;
	}

	public void inicializar() {
		poblacion = new Cromosoma[this.tamPob];
		
		for(int i = 0; i < tamPob; i++){
			poblacion[i] = new CromosomaP1F3(tolerancia);
		}

	}

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
		double prob; // Probabilidad de supervivencia
		int posSuperv = 0; // Posicion del superviviente
		
		for(int i = 0; i < tamPob; i++){
			Random rnd = new Random();
			prob = rnd.nextDouble();
			while((prob > poblacion[posSuperv].getPuntAcum()) && (posSuperv < tamPob))
			{
				posSuperv++;
				selSuperv[i] = posSuperv;
			}
		}
		Cromosoma nuevaPob[] = new CromosomaP1F3[tamPob];
		for(int i = 0; i < tamPob; i++){
			nuevaPob[i] = poblacion[selSuperv[i]];
		}
	}
	
	private void seleccionTorneo(){
		Cromosoma subpoblacion[] = new CromosomaP1F3[2]; //Conjunto a valorar
		Cromosoma poblacionAux[] = new CromosomaP1F3[tamPob]; //Nueva generación
		Random rnd = new Random();
		int posElegida;
		
		for(int j = 0; j < tamPob; j++){
			
			for(int i = 0; i < 2; i++){ //Seleccionamos 2 individuos al azar
				posElegida = (rnd.nextInt() * tamPob);
				subpoblacion[i] = poblacion[posElegida];
			}
			
			if(subpoblacion[0].getFitness() > subpoblacion[1].getFitness()){
				poblacionAux[j] = subpoblacion[0];
			}
			else{
				poblacionAux[j] = subpoblacion[1];
			}
		}
		
		poblacion = poblacionAux;
	}

	@Override
	public void reproduccion() {
		int selCruce[] = new int[tamPob];//Seleccionados para reproducir
		int numSelCruce = 0; //contador de seleccionados
		int puntoCruce1, puntoCruce2;
		double prob;
		Cromosoma hijo1 = new CromosomaP1F3(tolerancia);
		Cromosoma hijo2 = new CromosomaP1F3(tolerancia);
		Random rnd = new Random();
		
		for(int i = 0; i < tamPob; i++){
			prob = rnd.nextDouble();
			if(prob < probCruce){
				selCruce[numSelCruce] = i;
				numSelCruce++;
			}
		}
		//El número de seleccionados se hace par
		if((numSelCruce % 2) == 1) numSelCruce--;
		
		puntoCruce1 = rnd.nextInt(poblacion[0].genes[0].getLongAlelo());
		puntoCruce2 = rnd.nextInt(poblacion[0].genes[1].getLongAlelo());
		for(int i = 0; i < numSelCruce; i += 2)
		{
			cruce(poblacion[selCruce[i]], poblacion[selCruce[i+1]], hijo1, hijo2, puntoCruce1, puntoCruce2);
			poblacion[selCruce[i]] = hijo1;
			poblacion[selCruce[i+1]] = hijo2;
		}
		
	}

	private void cruce(Cromosoma cromosoma, Cromosoma cromosoma2, Cromosoma hijo1, Cromosoma hijo2, int puntoCruce1,
			int puntoCruce2) {
		
		for(int i = 0; i < puntoCruce1; i++){
			
		}
		
	}

}
