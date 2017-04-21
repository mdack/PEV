package pevolp2.algoritmo.cruce;

import java.util.ArrayList;
import java.util.Random;

import pevolp2.algoritmo.cromosoma.Cromosoma;

public class OXPosiciones extends Cruce {

	@Override
	public void cruzar(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1,
			Cromosoma hijo2) {
		int max = 0;
		int tam = padre1.getNGenes();
		tam = tam / 2;
		ArrayList<Integer> points = new ArrayList<Integer>();
		ArrayList<Integer> al1 = new ArrayList<Integer>();
		ArrayList<Integer> al2 = new ArrayList<Integer>();
		Random rnd = new Random();
		for(int i = 0; i < tam; i++){
			int r = rnd.nextInt(padre1.getNGenes());
			if(!points.contains(r)){
				points.add(r);
				if(r > max) max = r;
			}
			else i--;
		}
		for(int i = 0; i < padre1.getNGenes(); i++)
		{
			if(points.contains(i)){
				// Hijo 1
				hijo1.getGenes()[i].setAlelo(padre2.getGenes()[i].getAlelo());
				al1.add(padre2.getGenes()[i].getAlelo());
				// Hijo 2
				hijo2.getGenes()[i].setAlelo(padre1.getGenes()[i].getAlelo());
				al2.add(padre1.getGenes()[i].getAlelo());
			}
		}
		int j = max + 1;
		int k = max + 1;
		for(int i = 0; i < padre1.getNGenes(); i++)
		{
			if(j >= padre1.getNGenes()) j = 0;
			if(k >= padre1.getNGenes()) k = 0;
			if(!points.contains(j))
			{
				while(al1.contains(padre1.getGenes()[k].getAlelo())){
					k++;
					if(k >= padre1.getNGenes()) k = 0;
				}
				al1.add(padre1.getGenes()[k].getAlelo());
				hijo1.getGenes()[j].setAlelo(padre1.getGenes()[k].getAlelo());
			}
			j++;
		}
		j = max + 1;
		k = max + 1;
		for(int i = 0; i < padre1.getNGenes(); i++)
		{
			if(j >= padre1.getNGenes()) j = 0;
			if(k >= padre1.getNGenes()) k = 0;
			if(!points.contains(j))
			{
				while(al2.contains(padre2.getGenes()[k].getAlelo())){
					k++;
					if(k >= padre1.getNGenes()) k = 0;
				}
				
				al2.add(padre2.getGenes()[k].getAlelo());
				hijo2.getGenes()[j].setAlelo(padre2.getGenes()[k].getAlelo());
			}
			j++;
		}
	}

}
