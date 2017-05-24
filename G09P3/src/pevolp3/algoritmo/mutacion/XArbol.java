package pevolp3.algoritmo.mutacion;

import java.util.ArrayList;
import java.util.Random;

import pevolp3.algoritmo.arbol.Arbol;
import pevolp3.algoritmo.cromosoma.Cromosoma;

public class XArbol extends Mutacion {

	public XArbol(double prob) {
		super(prob);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int mutar(Cromosoma[] poblacion) {
		int mutaciones = 0;
		Random rnd = new Random();
		for(int i = 0; i < poblacion.length; i++)
		{
			double prob = rnd.nextDouble();
			if(prob < prob_mutacion)
			{
				Cromosoma c = poblacion[i];
				Arbol a = c.getArbol();
				ArrayList<Arbol> funciones = new ArrayList<Arbol>();
				a.getFunciones(a.getHijos(), funciones);
				
				int selecc_funcion = rnd.nextInt(funciones.size());
				
				Arbol nuevo_ar = new Arbol(a.getMax_prof(), a.isUseIF());
				nuevo_ar.inicializacionCreciente(funciones.get(selecc_funcion).getProfundidad());
									
				a.insertFuncion(a.getHijos(), nuevo_ar, selecc_funcion, 0);
				
				int nodos = (a.getNumNodos() - funciones.get(selecc_funcion).getNumNodos()) + nuevo_ar.getNumNodos();
				a.setNumNodos(nodos);
				c.setArbol(a);
					
				//c.evalua();
					
				poblacion[i] = c.copia();
				mutaciones++;
				}
			}
		
		return mutaciones;
	}

}
