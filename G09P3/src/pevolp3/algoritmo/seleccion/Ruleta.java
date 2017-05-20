package pevolp3.algoritmo.seleccion;


import java.util.Random;

import pevolp3.algoritmo.cromosoma.Cromosoma;


public class Ruleta extends Seleccion {

	public Ruleta() {}
	
	public Cromosoma[] selecciona(Cromosoma[] poblacion, int tamPob){
		int[] sel_super = new int[tamPob];
		double prob;
		Random rnd = new Random();
		int pos_super;
		
		for(int i = 0; i < tamPob; i++)
		{
			pos_super = 0;
			prob = rnd.nextDouble();
			while(prob > poblacion[pos_super].getPuntAcum() && pos_super < tamPob) pos_super++;
			sel_super[i] = pos_super;
		}
		
		Cromosoma[] newPob = new Cromosoma[tamPob];
		for(int i = 0; i < tamPob; i++) newPob[i] = poblacion[sel_super[i]];
		for(int i = 0; i < tamPob; i++) poblacion[i] = newPob[i].copia();
		return poblacion;
	}

	
}
