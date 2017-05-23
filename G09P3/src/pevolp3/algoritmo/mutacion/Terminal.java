package pevolp3.algoritmo.mutacion;

import java.util.ArrayList;
import java.util.Random;

import pevolp3.algoritmo.arbol.Arbol;
import pevolp3.algoritmo.cromosoma.Cromosoma;

public class Terminal extends Mutacion {

	public Terminal(double prob) {
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
				ArrayList<Arbol> terminales = new ArrayList<Arbol>();
				a.getTerminales(a.getHijos(), terminales);
				
				int selecc_terminal = rnd.nextInt(terminales.size());
				
				int nueva_terminal = rnd.nextInt(Cromosoma.terminales.length);
				
				terminales.get(selecc_terminal).setValor(Cromosoma.terminales[nueva_terminal]);
				
				a.insertTerminal(a.getHijos(), terminales.get(selecc_terminal), selecc_terminal, 0);
				
				c.setArbol(a);
				
				//c.evalua();
				
				poblacion[i] = c.copia();
				mutaciones++;
			}
		}
		return mutaciones;
	}

}
