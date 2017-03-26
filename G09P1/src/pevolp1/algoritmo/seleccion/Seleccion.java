package pevolp1.algoritmo.seleccion;

import pevolp1.algoritmo.cromosoma.Cromosoma;
import pevolp1.algoritmo.cromosoma.CromosomaP1F1;
import pevolp1.algoritmo.cromosoma.CromosomaP1F2;
import pevolp1.algoritmo.cromosoma.CromosomaP1F3;
import pevolp1.algoritmo.cromosoma.CromosomaP1F4;
import pevolp1.algoritmo.cromosoma.CromosomaP1F5;

public abstract class Seleccion {
	
	protected int funcion;
	
	public Seleccion(int func){
		funcion = func;
	}
	
	public abstract void selecciona(Cromosoma[] poblacion, int tamPob);
	
	protected Cromosoma[] creaPoblacion(int tamPob) {
		switch(funcion){
		case 0:
			return new CromosomaP1F1[tamPob];
		case 1:
			return new CromosomaP1F2[tamPob];
		case 2:
			return new CromosomaP1F3[tamPob];
		case 3:
			return new CromosomaP1F4[tamPob];
		case 4:
			return new CromosomaP1F5[tamPob];
		}
		return null;
	}
}
