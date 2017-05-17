package pevolp3.algoritmo.gen;

import java.util.ArrayList;
import java.util.Random;

import pevolp3.algoritmo.cromosoma.CromosomaP3;

public class Arbol{
	private String valor;
	private ArrayList<Arbol> hijos;
	private int numHijos;
	
	public Arbol(){
		Random rnd = new Random();
		int termRestantes;
		int func = rnd.nextInt(CromosomaP3.funciones.length);
		valor = CromosomaP3.funciones[func];
		if(CromosomaP3.funciones[func].equals("IF")) termRestantes = 3;
		else if(CromosomaP3.funciones[func].equals("NOT")) termRestantes = 1;
		else termRestantes = 2;
		inicializar(this, termRestantes);
	}
	public Arbol(String v){ valor = v; hijos = new ArrayList<Arbol>(); numHijos = 0; }
	public String getValor(){ return valor; }
	public void setValor(String v){ valor = v; }
	public ArrayList<Arbol> getHijos(){ return hijos; }
	public int getNumHijos(){ return numHijos; }	
	
	// Devuelve el arbol en forma de array
	public ArrayList<String> toArray(){
		ArrayList<String> array = new ArrayList<String>();
		toArray(array, this);
		return array;
	}
	
	// Insertar un valor en el arbol (nodo simple)
	public Arbol insert(String v, int index){
		Arbol a = new Arbol(v);
		if(index == -1){
			hijos.add(a);
			numHijos = hijos.size();
		}
		else
			hijos.set(index, a);
		return a;
	}
	
	// Insertar un arbol en otro arbol.
	public void insert(Arbol a, int index){
		if(index == -1){
			hijos.add(a);
			numHijos = hijos.size();
		}
		else
			hijos.set(index, a);
	}

	private void toArray(ArrayList<String> array, Arbol a){
		array.add(a.valor);
		for(int i = 0; i < a.hijos.size(); i++){
			toArray(array, a.hijos.get(i));
		}
	}
	
	private void inicializar(Arbol a, int termRestantes){
		Random rnd = new Random();
		for(int i = 0; i < termRestantes; i++)
		{
			int ch = rnd.nextInt(2);
			if(ch == 0)
			{
				int func = rnd.nextInt(CromosomaP3.terminales.length);
				a.insert(CromosomaP3.terminales[func], -1);
				termRestantes--;
			}
			else if(ch == 1)
			{
				int func = rnd.nextInt(CromosomaP3.funciones.length);
				Arbol aux = a.insert(CromosomaP3.funciones[func], -1);
				int newTerm = 0;
				if(CromosomaP3.funciones[func].equals("IF")) newTerm += 3;
				else if(CromosomaP3.funciones[func].equals("NOT")) newTerm += 1;
				else newTerm += 2;
				inicializar(aux, newTerm);
				termRestantes--;
			}
		}
	}
}
