
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
		
	}
	
	private void seleccionTorneo(){
		
	}

	@Override
	public void mutacion() {
		// TODO Auto-generated method stub

	}

}
