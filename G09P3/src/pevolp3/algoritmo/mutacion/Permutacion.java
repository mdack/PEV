package pevolp3.algoritmo.mutacion;

import java.util.Random;

import pevolp3.algoritmo.arbol.Arbol;
import pevolp3.algoritmo.cromosoma.Cromosoma;

public class Permutacion extends Mutacion {
	
	public Permutacion(double prob){
		super(prob);
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
				int k = rnd.nextInt(a.getNumHijos()+1);
				Arbol aMutar = a.at(k);
				if(aMutar.getNumHijos() > 1)
				{
					if(aMutar.getNumHijos() == 2)
					{
						Arbol aux = aMutar.getHijos().get(0);
						aMutar.getHijos().set(0, aMutar.getHijos().get(1));
						aMutar.getHijos().set(1, aux);
					}
					else
					{
						int pos1 = rnd.nextInt(3);
						int pos2 = pos1;
						while(pos2 == pos1) pos2 = rnd.nextInt(3);
						Arbol aux = aMutar.getHijos().get(pos1);
						aMutar.getHijos().set(pos1, aMutar.getHijos().get(pos2));
						aMutar.getHijos().set(pos2, aux);
					}
				}
				mutaciones++;
			}
		}
		return mutaciones;
	}

}
