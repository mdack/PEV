package pevolp2.algoritmo.mutacion;

import java.util.Random;
import java.util.Stack;

import pevolp2.algoritmo.cromosoma.Cromosoma;

public class MutPropia extends Mutacion {

	
	/**
	 * Coge una porcion del cromosoma, la invierte y la inserta
	 * al principio o al final de éste, dependiendo de cual de esas modificaciones
	 * da mejor fitness.
	 */
	public MutPropia(double prob) {
		super(prob);
	}

	@Override
	public void mutar(Cromosoma[] pob) {
		Random rnd = new Random();
		for(int i = 0; i < pob.length; i++)
		{
			double p = rnd.nextDouble();
			if(p < prob_mutacion)
			{
				Cromosoma c = pob[i];
				int puntoA = rnd.nextInt(c.getNGenes());
				int puntoB = rnd.nextInt(c.getNGenes() - puntoA);
				puntoB += puntoA+1;
				Stack<Integer> s = new Stack<Integer>();
				for(int j = puntoA; j < puntoB; j++)
				{
					s.push(c.getGenes()[j].getAlelo());
				}
				Cromosoma cPrincipio = c.copia();
				Cromosoma cFinal = c.copia();
				int j1 = 0;
				int j2 = c.getNGenes() - 1;
				while(!s.isEmpty())
				{
					int gen = s.pop();
					cPrincipio.getGenes()[j1].setAlelo(gen);
					cFinal.getGenes()[j2].setAlelo(gen);
					j1++; j2--;
				}
				for(int k = 0; k < c.getNGenes(); k++)
				{
					if(k < puntoA || k >= puntoB) 
					{
						cPrincipio.getGenes()[j1].setAlelo(c.getGenes()[k].getAlelo());
						cFinal.getGenes()[j2].setAlelo(c.getGenes()[k].getAlelo());
						j1++; j2--;
					}
				}
				double fitPrincipio = cPrincipio.evalua();
				double fitFinal = cFinal.evalua();
				if(fitPrincipio < fitFinal) pob[i] = cPrincipio.copia();
				else pob[i] = cFinal.copia();
			}
		}
	}

}
