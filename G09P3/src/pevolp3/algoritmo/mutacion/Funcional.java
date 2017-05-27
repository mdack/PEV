package pevolp3.algoritmo.mutacion;

import java.util.ArrayList;
import java.util.Random;

import pevolp3.algoritmo.arbol.Arbol;
import pevolp3.algoritmo.cromosoma.Cromosoma;

public class Funcional extends Mutacion {

	public Funcional(double prob) {
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
				Cromosoma c = poblacion[i].copia();
				Arbol a = c.getArbol().copia();
				
				//Se obtienen las funciones del árbol
				ArrayList<Arbol> funciones = new ArrayList<Arbol>();
				a.getFunciones(a.getHijos(), funciones);
				
				//Se comprueba que se pueda mutar, es decir, que sean AND u OR
				//ya que si se intenta mutar IF se volvería a poner IF, al igual que con NOT
				if(existenFuncionesAMutar(funciones)){
					//Selecciona una función al azar de la lista
					int selecc_funcion = rnd.nextInt(funciones.size());
					String val = "";
					
					//Cambia el valor de la función seleccionada
					if(funciones.get(selecc_funcion).getValor().equals("OR"))
						val = "AND";
					else
						val = "OR";
					
					funciones.get(selecc_funcion).setValor(val);
					
					//Inserta la nueva función en el árbol
					a.insertFuncion(a.getHijos(), funciones.get(selecc_funcion), selecc_funcion, 0);
					
					c.setArbol(a.copia());
					
					c.evalua();
					
					poblacion[i] = c.copia();
					mutaciones++;
				}
			}
		}
		return mutaciones;
	}

	//Se encarga de encontrar funciones que se puedan mutar
	//dejando así una lista solo de esas funciones
	private boolean existenFuncionesAMutar(ArrayList<Arbol> funciones) {
		boolean existe = false;
		ArrayList<Arbol> copia = new ArrayList<Arbol>();
		
		for(Arbol a : funciones){
			if(a.getValor().equals("OR") || a.getValor().equals("AND")){
				copia.add(a.copia());
				existe = true;
			}
		}
		
		funciones.clear();

		for(Arbol a : copia)
			funciones.add(a.copia());
		
		return existe;
	}

}
