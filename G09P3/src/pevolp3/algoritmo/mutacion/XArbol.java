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
				Arbol a = c.getArbol();
				boolean muta = false;
				boolean func = false;
				ArrayList<Arbol> funciones = new ArrayList<Arbol>();
				a.getFunciones(a.getHijos(), funciones);
				
				if(funciones.size() == 0){
					a.getTerminales(a.getHijos(), funciones);
					muta = existeTerminalAMutar(funciones , a.getMax_prof());
				}else{
					muta = true;
					func = true;
				}
				
				if(muta){
					int selecc_funcion = rnd.nextInt(funciones.size());
					
					Arbol nuevo_ar = new Arbol(a.getMax_prof(), a.isUseIF());
					nuevo_ar.inicializacionCreciente(funciones.get(selecc_funcion).getProfundidad());
										
					if(func)
						a.insertFuncion(a.getHijos(), nuevo_ar, selecc_funcion, 0);
					else
						a.insertTerminal(a.getHijos(), nuevo_ar, selecc_funcion, 0);
						
					int antiguos_nodos =  funciones.get(selecc_funcion).calculaNodos(0);
					int nuevos_nodos = nuevo_ar.getNumNodos();
					
					int nodos = (a.getNumNodos() - antiguos_nodos) + nuevos_nodos;
					a.setNumNodos(nodos);
					c.setArbol(a);
						
					c.evalua();
						
					poblacion[i] = c.copia();
					mutaciones++;
				}
				}
			}
		
		return mutaciones;
	}

	private boolean existeTerminalAMutar(ArrayList<Arbol> funciones, int max_prof) {
		boolean muta = false;
		ArrayList<Arbol> aux = new ArrayList<Arbol>();
		
		for(Arbol a : funciones){
			if(a.getProfundidad() != max_prof){
				aux.add(a);
			}
		}
		
		if(aux.size() > 0){
			muta = true;
			funciones.clear();
			for(Arbol a : aux){
				funciones.add(a);
			}
		}
		
		return muta;
	}

}
