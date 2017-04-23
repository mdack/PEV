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
		ArrayList<Integer> p1 = new ArrayList<Integer>();
		ArrayList<Integer> p2 = new ArrayList<Integer>();
		for(int i = 0; i < padre1.getNGenes(); i++)
		{
			al1.add(i, -1);
			al2.add(i, -1);
			p1.add(i, padre1.getGenes()[i].getAlelo());
			p2.add(i, padre2.getGenes()[i].getAlelo());
		}
		// Calculo de puntos de cruce
		Random rnd = new Random();
		puntoA = rnd.nextInt(padre1.getNGenes());
		puntoB = rnd.nextInt(padre1.getNGenes() - puntoA);
		puntoB += puntoA+1;
		for(int i = puntoA; i < puntoB; i++)
		{
			// Hijo 1
			al1.set(i, padre2.getGenes()[i].getAlelo());
			hijo1.getGenes()[i].setAlelo(padre2.getGenes()[i].getAlelo());
			// Hijo 2
			al2.set(i, padre1.getGenes()[i].getAlelo());
			hijo2.getGenes()[i].setAlelo(padre1.getGenes()[i].getAlelo());
		}
		int longCadena = puntoB - puntoA;
		int it = padre1.getNGenes() - longCadena;
		int pos = puntoB;
		for(int i = 0; i < it; i++)
		{
			if(pos >= padre1.getNGenes()) pos = 0;
			// Hijo 1
			if(!al1.contains(padre1.getGenes()[pos].getAlelo()))
			{
				al1.set(pos, padre1.getGenes()[pos].getAlelo());
				hijo1.getGenes()[pos].setAlelo(padre1.getGenes()[pos].getAlelo());
			}
			else
			{
				int index = p2.indexOf(padre1.getGenes()[pos].getAlelo());
				if(!al1.contains(padre1.getGenes()[index].getAlelo()))
				{
					hijo1.getGenes()[pos].setAlelo(padre1.getGenes()[index].getAlelo());
					al1.set(pos, padre1.getGenes()[index].getAlelo());
				}
				else
				{
					int n = rnd.nextInt(padre1.getNGenes()) + 1;
					while(al1.contains(n)) n = rnd.nextInt(padre1.getNGenes()) + 1;
					hijo1.getGenes()[pos].setAlelo(n);
					al1.set(pos, n);
				}
			}
			// Hijo 2
			if(!al2.contains(padre2.getGenes()[pos].getAlelo()))
			{
				al2.set(pos, padre2.getGenes()[pos].getAlelo());
				hijo2.getGenes()[pos].setAlelo(padre2.getGenes()[pos].getAlelo());
			}
			else
			{
				int index = p1.indexOf(padre2.getGenes()[pos].getAlelo());
				if(!al2.contains(padre2.getGenes()[index].getAlelo()))
				{
					hijo2.getGenes()[pos].setAlelo(padre2.getGenes()[index].getAlelo());
					al2.set(pos, padre2.getGenes()[index].getAlelo());
				}
				else
				{
					int n = rnd.nextInt(padre1.getNGenes()) + 1;
					while(al2.contains(n)) n = rnd.nextInt(padre1.getNGenes()) + 1;
					hijo2.getGenes()[pos].setAlelo(n);
					al2.set(pos, n);
				}
			}
			pos++;
		}
		hijo1.setFitness_bruto(hijo1.evalua());
		hijo2.setFitness_bruto(hijo2.evalua());
	}

}
