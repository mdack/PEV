package pevolp2.algoritmo.cruce;

import java.util.ArrayList;

import pevolp2.algoritmo.cromosoma.Cromosoma;
import pevolp2.algoritmo.cromosoma.CromosomaP2;

public class ERX extends Cruce {

	@Override
	public void cruzar(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1,
			Cromosoma hijo2) {
		ArrayList<Integer> c1 = new ArrayList<Integer>();
		ArrayList<Integer> c2 = new ArrayList<Integer>();
		ArrayList<Integer> h1 = new ArrayList<Integer>();
		ArrayList<Integer> h2 = new ArrayList<Integer>();
		for(int i = 0; i < padre1.getNGenes(); i++)
		{
			c1.add(i, padre1.getGenes()[i].getAlelo());
			c2.add(i, padre2.getGenes()[i].getAlelo());
		}
		
		// Bucle de mapeado de conexiones
		ArrayList<ArrayList<Integer>> mapeado = new ArrayList<ArrayList<Integer>>();
		for(int i = 0; i <= padre1.getNGenes(); i++) mapeado.add(i, null);
		for(int i = 1; i <= padre1.getNGenes(); i++)
		{
			int index1 = c1.indexOf(i);
			int index2 = c2.indexOf(i);
			ArrayList<Integer> mapGen = new ArrayList<Integer>();
			if(index1 - 1 >= 0 && !mapGen.contains(padre1.getGenes()[index1-1].getAlelo())) mapGen.add(padre1.getGenes()[index1-1].getAlelo());
			if(index1 + 1 < padre1.getNGenes() && !mapGen.contains(padre1.getGenes()[index1+1].getAlelo())) mapGen.add(padre1.getGenes()[index1+1].getAlelo());
			if(index2 - 1 >= 0 && !mapGen.contains(padre2.getGenes()[index2-1].getAlelo())) mapGen.add(padre2.getGenes()[index2-1].getAlelo());
			if(index2 + 1 < padre2.getNGenes() && !mapGen.contains(padre2.getGenes()[index2+1].getAlelo())) mapGen.add(padre2.getGenes()[index2+1].getAlelo());
			mapeado.set(i, mapGen);
		}
		
		boolean[] posiciones = new boolean[mapeado.size()]; //Controlar no coger elementos ya usados
		ArrayList<Integer> conexiones = mapeado.get(padre1.getGenes()[0].getAlelo()); //conexion que se va a analizar
		posiciones[padre1.getGenes()[0].getAlelo()] = true;
		h1.add(padre1.getGenes()[0].getAlelo());
		int cont = 0;
		int elem;
		boolean fin = false;
		
		while(cont < padre1.getNGenes()-1 && !fin){
		
			elem = obtieneSiguiente(posiciones, conexiones, mapeado);
			if(elem != 0){
				h1.add(elem);
				posiciones[elem] = true;
				conexiones = mapeado.get(elem);
				cont++;
			}else{
				fin = true;
			}
		}
		
		if(fin){
			hijo1 = new CromosomaP2(padre1.getNGenes());
		}else{
			llenaHijo(h1, hijo1);
		}
		
		posiciones = new boolean[mapeado.size()];
		conexiones = mapeado.get(padre2.getGenes()[0].getAlelo());
		posiciones[padre2.getGenes()[0].getAlelo()] = true;
		h2.add(padre2.getGenes()[0].getAlelo());
		cont = 0;
		elem = 0;
		fin = false;
		
		while(cont < padre1.getNGenes()-1 && !fin){
			
			elem = obtieneSiguiente(posiciones, conexiones, mapeado);
			if(elem != 0){
				h2.add(elem);
				posiciones[elem] = true;
				conexiones = mapeado.get(elem);
				cont++;
			}else{
				fin = true;
			}
		}
		
		if(fin){
			hijo2 = new CromosomaP2(padre2.getNGenes());
		}else{
			llenaHijo(h2, hijo2);
		}
				
	}

	private void llenaHijo(ArrayList<Integer> h, Cromosoma hijo) {
		
		for(int i = 0; i < h.size(); i++){
			hijo.getGenes()[i].setAlelo(h.get(i));
		}
		
		hijo.setFitness_bruto(hijo.evalua());
		
	}

	private int obtieneSiguiente(boolean[] posiciones, ArrayList<Integer> conexiones, ArrayList<ArrayList<Integer>> mapeado) {
		int size = 5000;
		int p = 0;
		
		for(int i = 0; i < conexiones.size(); i++){
			if(!posiciones[conexiones.get(i)]){ //Si esa posicion en la tabla no ha sido ya utilizada se coge
				if(mapeado.get(conexiones.get(i)).size() < size){ //Si el tamaño de es el más pequeño se coge
					p = conexiones.get(i);
					size = mapeado.get(conexiones.get(i)).size();
				}
			}
		}
		
		
		return p;
	}

}
