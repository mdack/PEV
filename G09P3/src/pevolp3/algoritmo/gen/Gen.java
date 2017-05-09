package pevolp3.algoritmo.gen;
import java.util.ArrayList;
import java.util.Random;

import pevolp3.algoritmo.cromosoma.CromosomaP3;


public class Gen {

	private ArrayList<String> gen;
	private int longitud;
	
	public Gen(){}
	
	public Gen(int n){
		longitud = 0;
		gen = new ArrayList<String>();
		
		Random rnd = new Random();
		int termRestantes;
		int func = rnd.nextInt(CromosomaP3.funciones.length);
		gen.add(CromosomaP3.funciones[func]);
		if(CromosomaP3.funciones[func].equals("IF")) termRestantes = 3;
		else if(CromosomaP3.funciones[func].equals("NOT")) termRestantes = 1;
		else termRestantes = 2;
		longitud++;
		while(termRestantes > 0)
		{
			int ch = rnd.nextInt(2);
			if(ch == 0)
			{
				func = rnd.nextInt(CromosomaP3.terminales.length);
				gen.add(CromosomaP3.terminales[func]);
				termRestantes--;
			}
			else if(ch == 1)
			{
				func = rnd.nextInt(CromosomaP3.funciones.length);
				gen.add(CromosomaP3.funciones[func]);
				if(CromosomaP3.funciones[func].equals("IF")) termRestantes += 3;
				else if(CromosomaP3.funciones[func].equals("NOT")) termRestantes += 1;
				else termRestantes += 2;
				termRestantes--;
			}
		}
		
	}
		
	public void setAlelo(int alelo){
//		this.gen = alelo;
	}
	
	public ArrayList<String> getAlelo(){ return gen; }
	public int getLongAlelo(){ return longitud; }
	
	public Gen copia(){
		Gen aux = new Gen();
		aux.longitud = this.longitud;
		aux.gen = this.gen;
		return aux;
	}
}
