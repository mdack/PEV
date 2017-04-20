package pevolp2.algoritmo.mutacion;

import java.util.Random;

import pevolp2.algoritmo.cromosoma.Cromosoma;

public class Insercion extends Mutacion {

	private final double inserciones = 2;
	
	public Insercion(double prob) {
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
				for(int j = 0; j < inserciones; j++)
				{
					int posicionACambiar = rnd.nextInt(c.getNGenes());
					int posicionDondeInsertar = rnd.nextInt(c.getNGenes());
					while(posicionACambiar == posicionDondeInsertar) posicionDondeInsertar = rnd.nextInt(c.getNGenes());
					if(posicionACambiar < posicionDondeInsertar)
					{
						int aux = c.getGenes()[posicionACambiar].getAlelo();
						for(int k = posicionACambiar; k < posicionDondeInsertar; k++)
						{
							c.getGenes()[k].setAlelo(c.getGenes()[k+1].getAlelo());
						}
						c.getGenes()[posicionDondeInsertar].setAlelo(aux);
					}
					else if(posicionACambiar > posicionDondeInsertar)
					{
						int aux = c.getGenes()[posicionACambiar].getAlelo();
						for(int k = posicionACambiar; k > posicionDondeInsertar; k--)
						{
							c.getGenes()[k].setAlelo(c.getGenes()[k-1].getAlelo());
						}
						c.getGenes()[posicionDondeInsertar].setAlelo(aux);
					}
					pob[i] = c.copia();
				}
			}
		}
	}

}
