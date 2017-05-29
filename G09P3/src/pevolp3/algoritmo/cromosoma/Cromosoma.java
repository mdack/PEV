package pevolp3.algoritmo.cromosoma;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import pevolp3.algoritmo.arbol.Arbol;
//hola
public class Cromosoma {
	
	private final static String arch6 = "data/mult6.txt";
	private final static String arch11 = "data/mult11.txt";
	private static int soluciones[][];
	private static int numSoluciones;
	
	public static String terminales[];
	public static final String terminales6[] = { "A0", "A1", "D0", "D1", "D2", "D3" };
	public static final String terminales11[] = { "A0", "A1", "A2", "D0", "D1", "D2", "D3", "D4", "D5", "D6", "D7" };
	public static final String funciones[] = { "AND", "OR", "NOT", "IF" };
	
	private Arbol arbol;
	private double fitness;
	private double fitness_bruto; //Aptitud antes de transformarla
	private double punt;
	private double puntAcum;
	private String fenotipo;
		
	public Cromosoma(int profundidad, int tipoCreacion, boolean useIf, int tipoMultiplexor) {
		arbol = new Arbol(profundidad, useIf);
		switch(tipoCreacion){
		case 0:
			arbol.inicializacionCreciente(0);
			break;
		case 1:
			arbol.inicializacionCompleta(0,0);
			break;
		case 2:
			int ini = new Random().nextInt(2);
			if(ini == 0) arbol.inicializacionCreciente(0);
			else arbol.inicializacionCompleta(0,0);
			break;
		}

		if(tipoMultiplexor == 0){
			numSoluciones = 64;
			if(soluciones == null || soluciones.length != 64)
				leerArch(6);
		}
		else if(tipoMultiplexor == 1){
			numSoluciones = 2048;
			if(soluciones == null || soluciones.length != 2048)
				leerArch(11);
		}
		evalua();
		fenotipo();
	}
	public Cromosoma() {
		this.arbol = new Arbol();
	}
	public double getFitness() {
		return fitness;
	}
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	public double getPunt() {
		return punt;
	}
	public void setPunt(double d) {
		this.punt = d;
	}
	public double getPuntAcum() {
		return puntAcum;
	}
	public void setPuntAcum(double puntAcum) {
		this.puntAcum = puntAcum;
	}
	
	public double getFitness_bruto() {
		return fitness_bruto;
	}
	public void setFitness_bruto(double fitness_bruto) {
		this.fitness_bruto = fitness_bruto;
	}

	public String fenotipo() {
		String s = this.toString();
		setFenotipo(s);
		return s;
	}
	
	public String toString(){
		ArrayList<String> func = arbol.toArray();
		String s = "";
		for(int i = 0; i < func.size(); i++){
			s += func.get(i);
			if(i < func.size()-1) s += " ";
		}
		this.setFenotipo(s);
		
		return arbol.toString();
	}

	public double evalua() {
		ArrayList<String> func = arbol.toArray();
		int fallos = numSoluciones;
		for(int i = 0; i < numSoluciones; i++){
			ArrayList<String> f = convFuncion(func, i);
			int res = evaluar(f, 0);
			if(numSoluciones == 64){
				if(res == soluciones[i][6]) fallos--;
			}
			else if(numSoluciones == 2048){
				if(res == soluciones[i][11]) fallos--;
			}		
		}
		fitness_bruto = fallos;
		fitness = fallos;
		return fallos;
	}

	public Cromosoma copia() {
		Cromosoma c = new Cromosoma();
	
		c.setArbol(this.arbol.copia());
		c.setFitness(this.fitness);
		c.setFitness_bruto(this.fitness_bruto);
		c.setPunt(this.punt);
		c.setPuntAcum(this.puntAcum);
		
		return c;
	}
	
	public Arbol getArbol() {
		return arbol;
	}
	public void setArbol(Arbol arbol) {
		this.arbol = arbol;
	}
	
	private ArrayList<String> convFuncion(ArrayList<String> func, int sol){
		ArrayList<String> funcConvertida = new ArrayList<String>();
		for(int i = 0; i < func.size(); i++)
		{
			String n = func.get(i);
			if(!n.equals("IF") && !n.equals("NOT") && !n.equals("OR") && !n.equals("AND"))
			{
				if(numSoluciones == 64)
				{
					switch(n)
					{
					case "A0":
					{
						funcConvertida.add(Integer.toString(soluciones[sol][0]));
						break;
					}
					case "A1":
					{
						funcConvertida.add(Integer.toString(soluciones[sol][1]));
						break;
					}
					case "D0":
					{
						funcConvertida.add(Integer.toString(soluciones[sol][2]));
						break;
					}
					case "D1":
					{
						funcConvertida.add(Integer.toString(soluciones[sol][3]));
						break;
					}
					case "D2":
					{
						funcConvertida.add(Integer.toString(soluciones[sol][4]));
						break;
					}
					case "D3":
					{
						funcConvertida.add(Integer.toString(soluciones[sol][5]));
						break;
					}
					}
				}
				else if(numSoluciones == 2048)
				{
					switch(n)
					{
					case "A0":
					{
						funcConvertida.add(Integer.toString(soluciones[sol][0]));
						break;
					}
					case "A1":
					{
						funcConvertida.add(Integer.toString(soluciones[sol][1]));
						break;
					}
					case "A2":
					{
						funcConvertida.add(Integer.toString(soluciones[sol][2]));
						break;
					}
					case "D0":
					{
						funcConvertida.add(Integer.toString(soluciones[sol][3]));
						break;
					}
					case "D1":
					{
						funcConvertida.add(Integer.toString(soluciones[sol][4]));
						break;
					}
					case "D2":
					{
						funcConvertida.add(Integer.toString(soluciones[sol][5]));
						break;
					}
					case "D3":
					{
						funcConvertida.add(Integer.toString(soluciones[sol][6]));
						break;
					}
					case "D4":
					{
						funcConvertida.add(Integer.toString(soluciones[sol][7]));
						break;
					}
					case "D5":
					{
						funcConvertida.add(Integer.toString(soluciones[sol][8]));
						break;
					}
					case "D6":
					{
						funcConvertida.add(Integer.toString(soluciones[sol][9]));
						break;
					}
					case "D7":
					{
						funcConvertida.add(Integer.toString(soluciones[sol][10]));
						break;
					}
					}
				}
			}
			else
			{
				funcConvertida.add(func.get(i));
			}
		}
		return funcConvertida;
	}

	private int evaluar(ArrayList<String> func, int index){
		String funcion = func.get(index);
		int resul;
		if(funcion.equals("IF")){
			int hijo1 = evaluar(func, index+1);
			int hijo2 = evaluar(func, index+2);
			int hijo3 = evaluar(func, index+3);
			if(hijo1 == 1) resul = hijo2;
			else resul = hijo3;
		}
		else if(funcion.equals("AND")){
			int hijo1 = evaluar(func, index+1);
			int hijo2 = evaluar(func, index+2);
			if(hijo1 == 1 && hijo2 == 1) resul = 1;
			else resul = 0;
		}
		else if(funcion.equals("OR")){
			int hijo1 = evaluar(func, index+1);
			int hijo2 = evaluar(func, index+2);
			if(hijo1 == 1 || hijo2 == 1) resul = 1;
			else resul = 0;
		}
		else if(funcion.equals("NOT")){
			int hijo1 = evaluar(func, index+1);
			if(hijo1 == 1) resul = 0;
			else resul = 1;
		}
		else
			resul = Integer.parseInt(funcion);
		return resul;
	}
	
	private void leerArch(int tipo){
		switch(tipo)
		{
		case 6:
		{
			File archivo = null;
		    Scanner in = null;

		    try {
		    	String file = arch6;
		        archivo = new File (file); 
		        in = new Scanner(archivo);
		         
		        soluciones = new int[64][7];
		        for(int i = 0; i < 64; i++){
		          	 for(int j = 0; j < 7; j++){
		          		soluciones[i][j] = in.nextInt(); 
		          	 } 
		        }    
		      }
		      catch(Exception e){
		    	  System.out.println(e.getMessage());
		      }finally{
		    	  try {
						if (in != null)
							in.close();
					} catch (Exception ex) {
						System.out.println("ERROR: " + ex.getMessage());
					}
		      }
		    break;
		}
		case 11:
		{
			File archivo = null;
		    Scanner in = null;

		    try {
		    	String file = arch11;
		        archivo = new File (file); 
		        in = new Scanner(archivo);
		         
		        soluciones = new int[2048][12];
		        for(int i = 0; i < 2048; i++){
		          	 for(int j = 0; j < 12; j++){
		          		soluciones[i][j] = in.nextInt(); 
		          	 } 
		        }    
		      }
		      catch(Exception e){
		    	  System.out.println(e.getMessage());
		      }finally{
		    	  try {
						if (in != null)
							in.close();
					} catch (Exception ex) {
						System.out.println("ERROR: " + ex.getMessage());
					}
		      }
		}
		}
	}
	public String getFenotipo() {
		return fenotipo;
	}
	public void setFenotipo(String fenotipo) {
		this.fenotipo = fenotipo;
	}
}
