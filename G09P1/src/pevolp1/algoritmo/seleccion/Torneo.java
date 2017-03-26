package pevolp1.algoritmo.seleccion;

import java.util.Random;

import pevolp1.algoritmo.cromosoma.Cromosoma;
import pevolp1.algoritmo.cromosoma.CromosomaP1F1;

public class Torneo extends Seleccion {
	private static final double P = 0.75;
	private int tipo_torneo;
	
	public Torneo(int func, int tipo) {
		super(func);
		tipo_torneo = tipo;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void selecciona(Cromosoma[] poblacion, int tamPob) {
		Cromosoma subpoblacion[] = new CromosomaP1F1[2]; //Conjunto a valorar
		Cromosoma poblacionAux[] = new CromosomaP1F1[tamPob]; //Nueva generación
		Random rnd = new Random();
		int posElegida;
		double prob;
		
		for(int j = 0; j < tamPob; j++){
			
			for(int i = 0; i < 2; i++){ //Seleccionamos 2 individuos al azar
				posElegida = (int) (rnd.nextDouble() * tamPob);
				subpoblacion[i] = poblacion[posElegida].copia();
			}
			
			if(tipo_torneo == 2){
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
		if(subpoblacion[0].getFitness() < subpoblacion[1].getFitness()){
			return subpoblacion[0];
		}
		else{
			return subpoblacion[1];
		}
	}

	private Cromosoma getMejorSubpoblacion(Cromosoma[] subpoblacion) {
		if(subpoblacion[0].getFitness() > subpoblacion[1].getFitness()){
			return subpoblacion[0];
		}
		else{
			return subpoblacion[1];
		}
	}

}
