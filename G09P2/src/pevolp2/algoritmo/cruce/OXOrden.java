package pevolp2.algoritmo.cruce;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import pevolp2.algoritmo.cromosoma.Cromosoma;

public class OXOrden extends Cruce {

	@Override
	public void cruzar(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1,
			Cromosoma hijo2) {
		int tam = padre1.getNGenes();
		tam = tam / 2;
		ArrayList<Integer> points = new ArrayList<Integer>();
		ArrayList<Integer> al1 = new ArrayList<Integer>();
		ArrayList<Integer> al2 = new ArrayList<Integer>();
		// Calculo de puntos de cruce
		Random rnd = new Random();
		for(int i = 0; i < tam; i++){
			int r = rnd.nextInt(padre1.getNGenes());
			if(!points.contains(r)){
				points.add(r);
				int a = padre1.getGenes()[r].getAlelo();
				int j = 0;
				while(padre2.getGenes()[j].getAlelo() != a) j++;
				al1.add(j);
				int b = padre2.getGenes()[r].getAlelo();
				j = 0;
				while(padre1.getGenes()[j].getAlelo() != b) j++;
				al2.add(j);
			}
			else i--;
		}
		for(int i = 0; i < padre1.getNGenes(); i++)
		{
			if(!al1.contains(i)){
				hijo1.getGenes()[i].setAlelo(padre2.getGenes()[i].getAlelo());
			}
			if(!al2.contains(i)){
				hijo2.getGenes()[i].setAlelo(padre1.getGenes()[i].getAlelo());
			}
		}
		Iterator<Integer> it = points.iterator();
		int pos = -1;
		boolean used = false;
		for(int i = 0; i < padre1.getNGenes(); i++)
		{
			if(pos == -1 && it.hasNext()){
				pos = it.next();
				used = false;
			}
			if(al1.contains(i)){
				hijo1.getGenes()[i].setAlelo(padre1.getGenes()[pos].getAlelo());
				used = true;
			}
			if(used) pos = -1;
		}
		pos = -1;
		used = false;
		it = points.iterator();
		for(int i = 0; i < padre1.getNGenes(); i++)
		{
			if(pos == -1 && it.hasNext()){
				pos = it.next();
				used = false;
			}
			if(al2.contains(i)){
				hijo2.getGenes()[i].setAlelo(padre2.getGenes()[pos].getAlelo());
				used = true;
			}
			if(used) pos = -1;
		}
		hijo1.setFitness_bruto(hijo1.evalua());
		hijo2.setFitness_bruto(hijo2.evalua());
	}

}
