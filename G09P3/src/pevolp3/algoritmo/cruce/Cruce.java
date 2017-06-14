package pevolp3.algoritmo.cruce;

import java.util.ArrayList;

import pevolp3.algoritmo.arbol.Arbol;
import pevolp3.algoritmo.cromosoma.Cromosoma;

public class Cruce {
	
	private static final double PROB_FUNC = 0.9;

	public Cruce() {
	}
	
	public Cromosoma[] cruzar(Cromosoma padre1, Cromosoma padre2){
		Cromosoma hijos[] = new Cromosoma[2];
		Cromosoma hijo1 = new Cromosoma();
		Cromosoma hijo2 = new Cromosoma();
		
		ArrayList<Arbol> nodos_selec1 = new ArrayList<Arbol>();
		ArrayList<Arbol> nodos_selec2 = new ArrayList<Arbol>();
		
		//Seleccionamos los nodos más relevante según la probabilidad
		//0.9 se cruzará en una función
		//resto se cruzará en un terminal
		nodos_selec1 = obtieneNodos(padre1.getArbol().copia());
		nodos_selec2 = obtieneNodos(padre2.getArbol().copia());
		
		//obtenemos los puntos de cruce a partir de los nodos seleccionados
		int puntoCruce1 = (int) (Math.random()*nodos_selec1.size());
		int puntoCruce2 = (int) (Math.random()*nodos_selec2.size());
		
		//copiamos los cromosomas padre en los hijos
		hijo1 = padre1.copia();
		hijo2 = padre2.copia();
		
		//Cogemos los nodos de cruce seleccionados
		Arbol temp1 = nodos_selec1.get(puntoCruce1).copia();
		Arbol temp2 = nodos_selec2.get(puntoCruce2).copia();
		
		//realizamos el corte sobre los arboles de los hijos
		corte(hijo1, temp2, puntoCruce1, temp1.isEsRaiz());
		corte(hijo2, temp1, puntoCruce2, temp2.isEsRaiz());
		
		int nodos = hijo1.getArbol().obtieneNodos(hijo1.getArbol(), 0);
		hijo1.getArbol().setNumNodos(nodos);
		nodos = hijo2.getArbol().obtieneNodos(hijo2.getArbol(), 0);
		hijo2.getArbol().setNumNodos(nodos);
		
		//Finalmente se evalúan
		hijo1.evalua();
		hijo2.evalua();
			
		hijos[0] = hijo1;
		hijos[1] = hijo2;

		return hijos;
	}
	
	private void corte(Cromosoma hijo, Arbol temp, int puntoCruce, boolean esRaiz) {
		
		if(!esRaiz){ //dependiendo de qué tipo era el nodo que ya no va a estar, se inserta el nuevo
			hijo.getArbol().insertTerminal(hijo.getArbol().getHijos(), temp, puntoCruce, 0);
		}else{
			hijo.getArbol().insertFuncion(hijo.getArbol().getHijos(), temp, puntoCruce, 0);
		}
		
	}

	private ArrayList<Arbol> obtieneNodos(Arbol arbol) {
		ArrayList<Arbol> nodos = new ArrayList<Arbol>();
		
		//Obtenemos una porbabilidad al azar
		if(seleccionaFunciones()){//Si devuelve true, el corte se hará en una función
			arbol.getFunciones(arbol.getHijos(), nodos);
			if(nodos.size() == 0){//Si no existen funciones, se seleccionan los terminales
				arbol.getTerminales(arbol.getHijos(), nodos);
			}
		}else{//Si devuelve false, el corte se hará por un terminal
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
