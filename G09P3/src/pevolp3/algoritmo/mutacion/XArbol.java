package pevolp3.algoritmo.mutacion;

import java.util.ArrayList;
import java.util.Random;

import pevolp3.algoritmo.arbol.Arbol;
import pevolp3.algoritmo.cromosoma.Cromosoma;

public class XArbol extends Mutacion {

	public XArbol(double prob) {
		super(prob);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int mutar(Cromosoma[] poblacion) {
		int mutaciones = 0;
		Random rnd = new Random();
		for(int i = 0; i < poblacion.length; i++)
		{
			double prob = rnd.nextDouble();
			if(prob < prob_mutacion)
			{
				Cromosoma c = poblacion[i];
				Arbol a = c.getArbol().copia();
				boolean muta = false;
				boolean func = false;
				
				//Seleccionamos las funciones del árbol
				ArrayList<Arbol> funciones = new ArrayList<Arbol>();
				a.getFunciones(a.getHijos(), funciones);
				
				//Si no existen funciones entonces se eligen terminales
				if(funciones.size() == 0){
					a.getTerminales(a.getHijos(), funciones);
					muta = existeTerminalAMutar(funciones , a.getMax_prof());
				}else{
					muta = true;
					func = true;
				}
				
				//Si existen funciones o terminales para mutar se sigue con la mutació
				if(muta){
					//Selecciona un nodo al azar
					int selecc_funcion = rnd.nextInt(funciones.size());
					
					//Genera un nuevo árbol
					Arbol nuevo_ar = new Arbol(a.getMax_prof(), a.isUseIF());
					nuevo_ar.inicializacionCreciente(funciones.get(selecc_funcion).getProfundidad());
					
					//Lo inserta donde se encontraba el nodo seleccionado
					if(func)
						a.insertFuncion(a.getHijos(), nuevo_ar, selecc_funcion, 0);
					else
						a.insertTerminal(a.getHijos(), nuevo_ar, selecc_funcion, 0);
					
					int nodos = a.obtieneNodos(a.copia(), 0);
					a.setNumNodos(nodos);
					
					c.setArbol(a.copia());
						
					c.evalua();
						
					poblacion[i] = c.copia();
					poblacion[i].evalua();
					mutaciones++;
				}
				}
			}
		
		return mutaciones;
	}
	
	//Comprueba que existan terminales a mutar
	//ya que si un terminal se encuentra en la máxima profundidad
	//no se podrá generar un árbol
	private boolean existeTerminalAMutar(ArrayList<Arbol> funciones, int max_prof) {
		boolean muta = false;
		ArrayList<Arbol> aux = new ArrayList<Arbol>();
		
		for(Arbol a : funciones){
			if(a.getProfundidad() != max_prof){
				aux.add(a.copia());
			}
		}
		
		if(aux.size() > 0){
			muta = true;
			funciones.clear();
			for(Arbol a : aux){
				funciones.add(a.copia());
			}
		}
		
		return muta;
	}

}
