package pevolp3.algoritmo.arbol;

import java.util.ArrayList;
import java.util.Random;

import pevolp3.algoritmo.cromosoma.Cromosoma;


public class Arbol{	
	private String valor;
	private ArrayList<Arbol> hijos;
	private int numHijos;
	private int numNodos;
	private int profundidad;
	private boolean useIF;
	private boolean esHoja;
	private boolean esRaiz;
	private Arbol padre;
	
	public Arbol getPadre() {
		return padre;
	}

	public void setPadre(Arbol padre) {
		this.padre = padre;
	}

	public Arbol(){
//		Random rnd = new Random();
//		int termRestantes;
//		int func = rnd.nextInt(Cromosoma.funciones.length);
//		valor = Cromosoma.funciones[func];
//		if(Cromosoma.funciones[func].equals("IF")) termRestantes = 3;
//		else if(Cromosoma.funciones[func].equals("NOT")) termRestantes = 1;
//		else termRestantes = 2;
//		inicializar(this, termRestantes);
	}
	
	public Arbol(int p, boolean useIf){
		valor = "";
		hijos = new ArrayList<Arbol>();
		numHijos = 0;
		profundidad = p;
		useIF = useIf;
	}
	
	public Arbol(String v){ valor = v; hijos = new ArrayList<Arbol>(); numHijos = 0; }
	public String getValor(){ return valor; }
	public void setValor(String v){ valor = v; }
	public ArrayList<Arbol> getHijos(){ return hijos; }
	public int getNumHijos(){ return numHijos; }	
	public void setNumHijos(int n){ numHijos = n; }
	
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
	
	public int inicializacionCompleta(int p, int nodos){
	int n = nodos;
	int nHijos = 2;
	if(p < profundidad){
		Random rnd = new Random();
		int func = 0;
		
		if(useIF){
			func = rnd.nextInt(Cromosoma.funciones.length);
		}else{
			func = rnd.nextInt(Cromosoma.funciones.length-1);
		}
		
		this.valor = Cromosoma.funciones[func];
		this.setEsRaiz(true);
		if(valor.equals("IF")) nHijos = 3;
		if(valor.equals("NOT")) nHijos = 1;
		
		for(int i = 0; i < nHijos; i++){
			Arbol hijo = new Arbol(profundidad, useIF);
			hijo.setPadre(this);
			n++;
			n = hijo.inicializacionCompleta(p+1, n);
			hijos.add(hijo);
			numHijos++;
		}
	}
	else{
		Random rnd = new Random();
		int terminal;
		this.setEsHoja(true);
		terminal = rnd.nextInt(Cromosoma.terminales.length);
		valor = Cromosoma.terminales[terminal];
		numHijos = 0;
	}
	
	numNodos = n;
	
	return n;
}
	
	public void inicializacionCreciente(int p){
		
		if(p < profundidad){
			Random rnd = new Random();
			int func = 0;
			
			if(useIF){
				func = rnd.nextInt(Cromosoma.funciones.length);
			}else{
				func = rnd.nextInt(Cromosoma.funciones.length-1);
			}
			
			this.valor = Cromosoma.funciones[func];
			creaHijos(p);
		}else{
			Random rnd = new Random();
			int terminal;
			terminal = rnd.nextInt(Cromosoma.terminales.length);
			valor = Cromosoma.terminales[terminal];
			numHijos = 0;
		}
	}
	
	private void inicializacionCrecienteAux(int p){
		
		if(p < profundidad){
			Random rnd = new Random();
			int rango;
			
			if(useIF){
				rango = Cromosoma.funciones.length + Cromosoma.terminales.length;
				
				int pos = rnd.nextInt(rango);
				
				if(pos >= Cromosoma.funciones.length){
					pos -= Cromosoma.funciones.length;
					valor = Cromosoma.terminales[pos];
					
				}else{
					valor = Cromosoma.funciones[pos];
					creaHijos(p);
				}
			}
			else{
				rango = Cromosoma.funciones.length + Cromosoma.terminales.length - 1;
				
				int pos = rnd.nextInt(rango);
				
				if(pos >= (Cromosoma.funciones.length-1)){
					pos -= Cromosoma.funciones.length;
					valor = Cromosoma.terminales[pos];
				}else{
					valor = Cromosoma.funciones[pos];
					creaHijos(p);
				}
			}
		}
		else{
			Random rnd = new Random();
			int terminal;
			terminal = rnd.nextInt(Cromosoma.terminales.length);
			valor = Cromosoma.terminales[terminal];
			numHijos = 0;
		}
	}

	private void creaHijos(int p) {
		int nHijos = 2;
		if(valor.equals("IF")) nHijos = 3;
		if(valor.equals("NOT")) nHijos = 1;
		
		for(int i = 0; i < nHijos; i++){
			Arbol hijo = new Arbol(profundidad, useIF);
			hijo.setPadre(this);
			hijo.inicializacionCrecienteAux(p+1);
			hijos.add(hijo);
			numHijos++;
		}
	}
	
	/**
	 * Devuelve los nodos hoja del �rbol
	 * @param hijos Hijos del �rbol a analizar
	 * @param nodos Array donde se guardan los terminales
	 */
	private void getTerminales(ArrayList<Arbol> hijos, ArrayList<Arbol> nodos) {
		
		for(int i = 0; i < hijos.size(); i++){
			if(hijos.get(i).isEsHoja()){
				nodos.add(hijos.get(i));
			}else{
				getTerminales(hijos, nodos);
			}
		}
	}
	
	/**
	 * Devuelve los nodos internos del �rbol
	 * @param hijos Hijos del �rbol a analizar
	 * @param nodos Array donde se guardan las funciones
	 */
	private void getFunciones(ArrayList<Arbol> hijos, ArrayList<Arbol> nodos) {
		
		for(int i = 0; i < hijos.size(); i++){
			if(hijos.get(i).isEsRaiz()){
				nodos.add(hijos.get(i));
				getFunciones(hijos, nodos);
			}
		}
		
	}
	
	public boolean isEsHoja() {
		return esHoja;
	}

	public void setEsHoja(boolean esHoja) {
		this.esHoja = esHoja;
	}

	public boolean isEsRaiz() {
		return esRaiz;
	}

	public void setEsRaiz(boolean esRaiz) {
		this.esRaiz = esRaiz;
	}

	
//	private void inicializar(Arbol a, int termRestantes){
//		Random rnd = new Random();
//		for(int i = 0; i < termRestantes; i++)
//		{
//			int ch = rnd.nextInt(2);
//			if(ch == 0)
//			{
//				int func = rnd.nextInt(Cromosoma.terminales.length);
//				a.insert(Cromosoma.terminales[func], -1);
//				termRestantes--;
//			}
//			else if(ch == 1)
//			{
//				int func = rnd.nextInt(Cromosoma.funciones.length);
//				Arbol aux = a.insert(Cromosoma.funciones[func], -1);
//				int newTerm = 0;
//				if(Cromosoma.funciones[func].equals("IF")) newTerm += 3;
//				else if(Cromosoma.funciones[func].equals("NOT")) newTerm += 1;
//				else newTerm += 2;
//				inicializar(aux, newTerm);
//				termRestantes--;
//			}
//		}
//	}
}
