package pevolp2.algoritmo.cruce;

import java.util.ArrayList;
import java.util.Random;

import pevolp2.algoritmo.cromosoma.Cromosoma;

public class PMX extends Cruce {

	@Override
	public void cruzar(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1,
			Cromosoma hijo2) {
		int puntoA, puntoB;
		ArrayList<Integer> al1 = new ArrayList<Integer>();
		ArrayList<Integer> al2 = new ArrayList<Integer>();
		// Calculo de puntos de cruce.
		Random rnd = new Random();
		puntoA = rnd.nextInt(padre1.getLongitud());
		puntoB = rnd.nextInt(padre1.getLongitud() - puntoA);
		puntoB += puntoA;
		for(int i = puntoA; i < puntoB; i++)
		{
			// Hijo 1
			al1.add(i, padre1.getGenes()[i].getAlelo()[0]);
			hijo1.getGenes()[i].getAlelo()[0] = padre2.getGenes()[i].getAlelo()[0];
			// Hijo 2
			al2.add(i, padre2.getGenes()[i].getAlelo()[0]);
			hijo2.getGenes()[i].getAlelo()[0] = padre1.getGenes()[i].getAlelo()[0];
		}
		int longCadena = puntoB - puntoA;
		int it = padre1.getLongitud() - longCadena;
		int pos = puntoB;
		for(int i = 0; i < it; i++)
		{
			if(puntoB >= padre1.getLongitud()) pos = 0;
			// Hijo 1
			if(!al1.contains(padre1.getGenes()[pos].getAlelo()[0]))
			{
				al1.add(pos, padre1.getGenes()[pos].getAlelo()[0]);
				hijo1.getGenes()[pos].getAlelo()[0] = padre1.getGenes()[pos].getAlelo()[0];
			}
			else
			{
				int index = al1.indexOf(padre1.getGenes()[pos].getAlelo()[0]);
				hijo1.getGenes()[pos].getAlelo()[0] = padre2.getGenes()[index].getAlelo()[0];
				al1.add(index, padre2.getGenes()[index].getAlelo()[0]);
			}
			// Hijo 2
			if(!al2.contains(padre2.getGenes()[pos].getAlelo()[0]))
			{
				al2.add(pos, padre2.getGenes()[pos].getAlelo()[0]);
				hijo2.getGenes()[pos].getAlelo()[0] = padre2.getGenes()[pos].getAlelo()[0];
			}
			else
			{
				int index = al2.indexOf(padre2.getGenes()[pos].getAlelo()[0]);
				hijo2.getGenes()[pos].getAlelo()[0] = padre1.getGenes()[index].getAlelo()[0];
				al2.add(index, padre1.getGenes()[index].getAlelo()[0]);
			}
			pos++;
		}
	}

}
