package pevolp2.algoritmo.mutacion;

import java.util.Random;
import java.util.Stack;

import pevolp2.algoritmo.cromosoma.Cromosoma;

public class Inversion extends Mutacion {


	public Inversion(double prob) {
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
				for(int j = puntoA; j < puntoB; j++)
				{
					c.getGenes()[j].setAlelo(s.pop());
				}
				c.setFitness_bruto(c.evalua());
				pob[i] = c.copia();
			}
		}
	}

}
