package pevolp2.algoritmo;
import java.util.Random;

import pevolp2.algoritmo.cromosoma.Cromosoma;
import pevolp2.algoritmo.cromosoma.CromosomaP2;
import pevolp2.algoritmo.cruce.CX;
import pevolp2.algoritmo.cruce.CodOrdinal;
import pevolp2.algoritmo.cruce.Cruce;
import pevolp2.algoritmo.cruce.ERX;
import pevolp2.algoritmo.cruce.OX;
import pevolp2.algoritmo.cruce.OXOrden;
import pevolp2.algoritmo.cruce.OXPosiciones;
import pevolp2.algoritmo.cruce.PMX;
import pevolp2.algoritmo.mutacion.Heuristica;
import pevolp2.algoritmo.mutacion.Insercion;
import pevolp2.algoritmo.mutacion.Intercambio;
import pevolp2.algoritmo.mutacion.Inversion;
import pevolp2.algoritmo.mutacion.Mutacion;
import pevolp2.algoritmo.seleccion.Estocastico;
import pevolp2.algoritmo.seleccion.Ruleta;
import pevolp2.algoritmo.seleccion.Torneo;
import pevolp2.presentacion.VistaPrincipal;

public class AGenetico {
	
	private static int[][] flujos;
	private static int[][] distancias;
	
	private Cromosoma[] poblacion, elite; // Poblacion
	private int tamPob; // Tamaño de la poblacion
	private int numMaxGen; // Numero maximo de generaciones
	private Cromosoma elMejor; // Mejor cromosoma de la poblacion
	private int posMejor; // Posicion en la poblacion
	private double probCruce; // Probabilidad de cruce
	private double probMut; // Probabilidad de mutacion
	private double tolerancia; // Tolerancia
	private double mejorAbs; // Fitness mejor absoluto.
	private int tamElite;
	private int func;
	private int n;
	private boolean maximizar;
	
	public AGenetico(int poblacion, int generaciones, double porcCruces, double porcMutacion, double precision, boolean elitismo,int funcion, int nVar){
		tamPob = poblacion;
		numMaxGen = generaciones;
		probCruce = porcCruces;
		probMut = porcMutacion;
		tolerancia = precision;
		if(elitismo){
			tamElite = (int) (tamPob * 0.02);
			elite = new Cromosoma[tamElite];
		}
		n = nVar;
		if(funcion != 2) maximizar = false;
		else maximizar = true;
		
		func = funcion;
	}
	
	public void inicializar() {
		this.poblacion = new Cromosoma[tamPob];
		for(int i = 0; i < tamPob; i++){
			this.poblacion[i] = new CromosomaP2(n);
		}
	}
	
	public void evaluar(){
		double sumaAdap;
		if(maximizar) sumaAdap = revisar_adaptacion_maximizar();
		else sumaAdap = revisar_adaptacion_minimizar();
		
		double fitnessBrutoAcum = 0; // Para calcular la media mas tarde.
		double bestFitness;
		double puntAcum = 0;
		if(maximizar){
			bestFitness = -1000000;
			for(int i = 0; i < tamPob; i++){
				poblacion[i].evalua();
				double fit = poblacion[i].getFitness_bruto();
				fitnessBrutoAcum += fit;
				if(fit > bestFitness){
					bestFitness = fit;
					this.posMejor = i;
				}
				poblacion[i].setPunt(poblacion[i].getFitness()/sumaAdap);
				puntAcum += poblacion[i].getFitness()/sumaAdap;
				poblacion[i].setPuntAcum(puntAcum);
			}
			this.elMejor = this.poblacion[posMejor].copia();
			if(bestFitness > this.mejorAbs) this.mejorAbs = bestFitness;
		}
		else{
			bestFitness = Double.MAX_VALUE;
			for(int i = 0; i < tamPob; i++){
				double fit = poblacion[i].getFitness_bruto();
				fitnessBrutoAcum += fit;
				if(fit < bestFitness){
					bestFitness = fit;
					this.posMejor = i;
				}
				poblacion[i].setPunt(poblacion[i].getFitness()/sumaAdap);
				puntAcum += poblacion[i].getFitness()/sumaAdap;
				poblacion[i].setPuntAcum(puntAcum);
			}
			this.elMejor = this.poblacion[posMejor].copia();
			if(bestFitness < this.mejorAbs) this.mejorAbs = bestFitness;
		}
		
		VistaPrincipal.addData(mejorAbs, elMejor.getFitness_bruto(), fitnessBrutoAcum/tamPob);
	}
	
	public double revisar_adaptacion_minimizar(){
		double cmax = -1000000;
		for(int i = 0; i < tamPob; i++)
		{
			double fit = poblacion[i].getFitness_bruto();
			if(fit > cmax) cmax = fit;
		}
		double sumaAdap = 0;
		for(int i = 0; i < tamPob; i++)
		{
			double adapFit = cmax - poblacion[i].getFitness_bruto();
			sumaAdap += adapFit;
			poblacion[i].setFitness(adapFit);
		}
		return sumaAdap;
	}
	
	public double revisar_adaptacion_maximizar(){
		double cmin = Double.MAX_VALUE;
		for(int i = 0; i < tamPob; i++)
		{
			double fit = poblacion[i].getFitness_bruto();
			if(fit < cmin) cmin = fit;
		}
		cmin = Math.abs(cmin);
		double sumaAdap = 0;
		for(int i = 0; i < tamPob; i++)
		{
			double adapFit = cmin + poblacion[i].getFitness_bruto();
			sumaAdap += adapFit;
			poblacion[i].setFitness(adapFit);
		}
		return sumaAdap;
	}
	
	public void mutacion(int tipo) {
		Mutacion m;
		switch(tipo){
		case 0:
		{
			m = new Insercion();
			m.mutar();
			break;
		}
		case 1:
		{
			m = new Intercambio();
			m.mutar();
			break;
		}
		case 2:
		{
			m = new Inversion();
			m.mutar();
			break;
		}
		case 3:
		{
			m = new Heuristica();
			m.mutar();
			break;
		}
		case 4:
		{
			// Propio
			break;
		}
		}
	}
	
	public int getPosMejor(){
		return this.posMejor;
	}
	
	public int getNumMaxGen(){
		return this.numMaxGen;
	}
	
	public String toString() {
		String cadena = " ";
		
		cadena += "* Mejor de generación: \n";
		cadena += this.elMejor.toString();
		cadena += "\n";
		
		cadena += "* Mejor absoluto: ";
		cadena += this.mejorAbs + "\n";
		cadena += "\n";
		
		return cadena;
	}

	public void seleccionaElite() {
		ordenaPoblacion();
		int pos_elite = tamPob - tamElite;
		
		for(int i = 0; i < tamElite; i++){
			elite[i] = poblacion[pos_elite].copia();
			pos_elite++;
		}
	}

	private void ordenaPoblacion() {
		Cromosoma temp = null;
		
		if(maximizar)
		{
			for(int i = 0; i < tamPob; i++){
				for(int j=i+1; j < tamPob; j++){
					if(poblacion[i].getFitness_bruto() > poblacion[j].getFitness_bruto()){
							temp = poblacion[i];
							poblacion[i] = poblacion[j];
							poblacion[j] = temp;
					}
				}
			}
		}
		else{
			for(int i = 0; i < tamPob; i++){
				for(int j=i+1; j < tamPob; j++){
					if(poblacion[i].getFitness_bruto() < poblacion[j].getFitness_bruto()){
							temp = poblacion[i];
							poblacion[i] = poblacion[j];
							poblacion[j] = temp;
					}
				}
			}
		}
	}

	public void insertaElite() {
		ordenaPoblacion();
		
		for(int i = 0; i < tamElite; i++){
			poblacion[i] = elite[i].copia();
		}
	}
	
	public void seleccion(int tipo) {
		if(tipo == 0){
			poblacion = new Ruleta(func).selecciona(poblacion, tamPob);
		}else if(tipo == 1){
			poblacion = new Estocastico(func).selecciona(poblacion, tamPob);
		}else{
			poblacion = new Torneo(func, tipo).selecciona(poblacion, tamPob);
		}
	}


	public void reproduccion(){
		int selCruce[] = new int[tamPob];
		
		int numSelCruce = 0;
		int puntoCruce;
		double prob;
		Cromosoma hijo1 = nuevoCromosoma();
		Cromosoma hijo2 = nuevoCromosoma();
		Random rnd = new Random();
		
		for(int i = 0; i < tamPob; i++){
			prob = rnd.nextDouble();
			if(prob < probCruce){
				selCruce[numSelCruce] = i;
				numSelCruce++;
			}
		}
		if((numSelCruce % 2) == 1) numSelCruce--;
		Cromosoma[] nuevaPob = new Cromosoma[tamPob];
		
		puntoCruce = rnd.nextInt(poblacion[0].getLongitud());
		for(int i = 0; i < numSelCruce; i += 2)
		{
			int padre1 = selCruce[i];
			int padre2 = selCruce[i+1];
			cruce(poblacion[padre1], poblacion[padre2], hijo1, hijo2, puntoCruce);
			nuevaPob[padre1] = hijo1.copia();
			nuevaPob[padre2] = hijo2.copia();
		}
		for(int i = 0; i < tamPob; i++)
		{
			if(nuevaPob[i] == null) nuevaPob[i] = poblacion[i].copia();
		}
		for(int i = 0; i < tamPob; i++)
			poblacion[i] = nuevaPob[i].copia();
	}
		
	private Cromosoma nuevoCromosoma() {
		return new CromosomaP2(n);
	}

	private void cruce(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1, Cromosoma hijo2, int tipo)
	{
		Cruce c;
		switch(tipo)
		{
		case 0:
		{
			c = new PMX();
			c.cruzar(padre1, padre2, hijo1, hijo2);
			break;
		}
		case 1:
		{
			c = new OX();
			c.cruzar(padre1, padre2, hijo1, hijo2);
			break;
		}
		case 2:
		{
			c = new OXPosiciones();
			c.cruzar(padre1, padre2, hijo1, hijo2);
			break;
		}
		case 3:
		{
			c = new OXOrden();
			c.cruzar(padre1, padre2, hijo1, hijo2);
			break;
		}
		case 4:
		{
			c = new CX();
			c.cruzar(padre1, padre2, hijo1, hijo2);
			break;
		}
		case 5:
		{
			c = new ERX();
			c.cruzar(padre1, padre2, hijo1, hijo2);
			break;
		}
		case 6:
		{
			c = new CodOrdinal();
			c.cruzar(padre1, padre2, hijo1, hijo2);
			break;
		}
		case 7:
		{
			// Propio
			break;
		}
		}
	}

	public boolean isMaximizar() {
		return maximizar;
	}

	public static int[][] getFlujos() {
		return flujos;
	}

	public static int[][] getDistancias() {
		return distancias;
	}
}