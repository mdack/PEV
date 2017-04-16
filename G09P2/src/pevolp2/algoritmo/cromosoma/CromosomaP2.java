package pevolp2.algoritmo.cromosoma;

import pevolp2.algoritmo.AGenetico;
import pevolp2.algoritmo.gen.Gen;

public class CromosomaP2 extends Cromosoma {
	
	public CromosomaP2(int n){
		N_GENES = n;
		genes = new Gen[N_GENES];
				
		for(int i = 0; i < N_GENES; i++){	
			this.genes[i] = new Gen(n);
		}		
		this.fenotipo = fenotipo(0);
		this.fitness_bruto = evalua();
	}

	@Override
	public double fenotipo(int pos) {
		return 0;
	}

	@Override
	public double evalua() {
		int fitness = 0;
		int[][] flujos = AGenetico.getFlujos();
		int[][] distancias = AGenetico.getDistancias();
		for(int i = 0; i < N_GENES; i++)
		{
			for(int j = 0; i < N_GENES; j++)
			{
				// Faltan las matrices de flujos y distancias.
				fitness += (flujos[i][j] * distancias[genes[i].getAlelo()][genes[j].getAlelo()]); 
			}
		}
		this.fitness_bruto = fitness;
		return fitness;
	}

	@Override
	public String toString() {
		return null;
	}

	@Override
	public Cromosoma copia() {
		CromosomaP2 aux = new CromosomaP2(N_GENES);
		aux.fitness = this.fitness;
		aux.fitness_bruto = this.fitness_bruto;
		aux.fenotipo = this.fenotipo;
		aux.setLongitud(this.getLongitud());
		aux.punt = this.punt;
		aux.puntAcum = this.puntAcum;
		aux.genes = new Gen[N_GENES];
		for(int i = 0; i < N_GENES; i++)
		{
			aux.genes[i] = this.genes[i].copia();
		}
		return aux;
	}

}
