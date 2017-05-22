package pevolp3.algoritmo.cruce;

import java.util.ArrayList;
import java.util.Random;

import pevolp3.algoritmo.arbol.Arbol;
import pevolp3.algoritmo.cromosoma.Cromosoma;

public class Cruce {
	
	private static final double PROB_FUNC = 0.9;

	public Cruce() {
		// TODO Auto-generated constructor stub
	}
	
	public void cruzar(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1, Cromosoma hijo2){
		ArrayList<Arbol> nodos_selec1 = new ArrayList<Arbol>();
		ArrayList<Arbol> nodos_selec2 = new ArrayList<Arbol>();
		
		ArrayList<String> p1 = padre1.getArbol().toArray();
		ArrayList<String> p2 = padre2.getArbol().toArray();
		
		nodos_selec1 = obtieneNodos(padre1.getArbol());
		nodos_selec2 = obtieneNodos(padre2.getArbol());
		
		Random rnd = new Random();
		int puntoCruce1 = rnd.nextInt(nodos_selec1.size());
		int puntoCruce2 = rnd.nextInt(nodos_selec2.size());
		
		hijo1.setArbol(padre1.getArbol());
		hijo2.setArbol(padre2.getArbol());
		
		Arbol temp = nodos_selec1.get(puntoCruce1);
		if(temp.isEsHoja()){
			hijo1.getArbol().insertTerminal(nodos_selec2.get(puntoCruce2), puntoCruce1, 0);
		}else{
			hijo2.getArbol().insertTerminal(temp, puntoCruce2, 0);
		}
		
//		hijo1.evalua();
//		hijo2.evalua();
	}
	
	private ArrayList<Arbol> obtieneNodos(Arbol arbol) {
		ArrayList<Arbol> nodos = new ArrayList<Arbol>();
		
		if(seleccionaFunciones()){
			arbol.getFunciones(arbol.getHijos(), nodos);
		}else{
			arbol.getTerminales(arbol.getHijos(), nodos);
		}
		
		return nodos;
	}

	private boolean seleccionaFunciones(){
		double prob = Math.random();
		
		if(prob < PROB_FUNC){
			return true;
		}else{
			return false;
		}
	}
	
}
