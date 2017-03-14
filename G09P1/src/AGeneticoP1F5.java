
public class AGeneticoP1F5 extends AGenetico {

	public AGeneticoP1F5(int poblacion, int generaciones, double porcCruces, double porcMutacion, double precision) {
		super(poblacion, generaciones, porcCruces, porcMutacion, precision, false);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void inicializar() {
		// TODO Auto-generated method stub

	}

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
		

		
		if(maxFitness > mejorAbs) this.mejorAbs = maxFitness;
		this.elMejor = this.poblacion[posMejor];
		
		VistaPrincipal.addData(mejorAbs, maxFitness, (puntAcumulada/tamPob));

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


	@Override
	public void seleccion(int tipo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reproduccion() {
		// TODO Auto-generated method stub
		
	}

}
