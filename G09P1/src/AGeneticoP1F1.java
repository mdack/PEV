
public class AGeneticoP1F1 extends AGenetico {
	
	public AGeneticoP1F1(int poblacion, int generaciones, float porcCruces, float porcMutacion)
	{
		super(poblacion, generaciones, porcCruces, porcMutacion);
	}

	@Override
	public void inicializar() {
		this.poblacion = new Cromosoma[tamPob];
		for(int i = 0; i < tamPob; i++)
			this.poblacion[i] = new CromosomaP1F1();		
	}

	@Override
	public void evaluar() {
		float puntAcumulada = 0;
		float maxFitness = -250f;
		int posMax = -1;
		for(int i = 0; i < tamPob; i++){
			float fitness = this.poblacion[i].getFitness();
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
	public void seleccion() {
		int seleccion = 1; // Entraria por parametro; seleccionaria el tipo de seleccion.
		if(seleccion == 1) // Ruleta
			seleccionRuleta();
		else if(seleccion == 2) // Otra seleccion
			seleccionTorneo();
		// ...
	}
	
	private void seleccionRuleta(){
		int selSuperv[] = new int[tamPob]; // Supervivientes
		float prob; // Probabilidad de supervivencia
		int posSuperv; // Posicion del superviviente
		for(int i = 0; i < tamPob; i++){
			Random rnd = new Random();
			prob = rnd.nextFloat();
			while((prob > poblacion[posSuperv].puntAcum) && (posSuperv < tamPob))
			{
				posSuperv++;
				selSuperv[i] = posSuperv;
			}
		}
	}
	
	private void seleccionTorneo(){
		
	}

	@Override
	public void mutacion() {
		booleano mutado;
		int i, j;
		real prob;
		
		for(i = 0; i < this.){
			mutado = false;
			para cada j desde 0 hasta lcrom hacer{
			// se genera un numero aleatorio en [0 1)
			prob = alea();
			// mutan los genes con prob<prob_mut
			si (prob<prob_mut){
			pob[i].genes[j] = not( pob[i].genes[j]);
			mutado = true;
			}
			si (mutado)
			pob[i].aptitud = pob[i].evalua();
			}
			}
	}

}
