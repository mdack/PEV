package pevolp1.algoritmo;
import java.util.Random;

import pevolp1.algoritmo.cromosoma.Cromosoma;
import pevolp1.algoritmo.cromosoma.CromosomaP1F1;
import pevolp1.algoritmo.cromosoma.CromosomaP1F2;
import pevolp1.algoritmo.cromosoma.CromosomaP1F3;
import pevolp1.algoritmo.cromosoma.CromosomaP1F4;
import pevolp1.algoritmo.cromosoma.CromosomaP1F5;
import pevolp1.algoritmo.gen.Gen;
import pevolp1.algoritmo.seleccion.Estocastico;
import pevolp1.algoritmo.seleccion.Ruleta;
import pevolp1.algoritmo.seleccion.Torneo;
import pevolp1.presentacion.VistaPrincipal;

public class AGenetico {
	
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
			switch(func){
			case 0:
				this.poblacion[i] = new CromosomaP1F1(tolerancia);	
				break;
			case 1:
				this.poblacion[i] = new CromosomaP1F2(tolerancia);	
				break;
			case 2:
				this.poblacion[i] = new CromosomaP1F3(tolerancia);	
				break;
			case 3: 
				this.poblacion[i] = new CromosomaP1F4(tolerancia, n);	
				break;
			case 4:
				this.poblacion[i] = new CromosomaP1F5(tolerancia);	
				break;
			}
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
	
	public void mutacion() {
		boolean mutado = false;
		int i, j;
		double prob;
		Random rnd = new Random();
		
		for(i = 0; i < this.tamPob; i++){	
			for(int k = 0; k < poblacion[i].getNGenes(); k++){
				Gen gen = poblacion[i].getGenes()[k];
				mutado = false;
				
				for(j = 0; j < gen.getLongAlelo(); j++)
				{
					prob = rnd.nextDouble();
					// mutan los genes con prob<prob_mut
					if (prob < probMut){
						mutado = true;
						if(poblacion[i].getGenes()[k].getPosAlelo(j)){
							poblacion[i].getGenes()[k].setPosAlelo(j, false);
						}
						else{
							poblacion[i].getGenes()[k].setPosAlelo(j, true);
						}
						
					}
				}
			}
			if (mutado){
				poblacion[i].setFitness_bruto(poblacion[i].evalua());
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
		switch(func){
		case 0:
			return new CromosomaP1F1(tolerancia);
		case 1:
			return new CromosomaP1F2(tolerancia);
		case 2:
			return new CromosomaP1F3(tolerancia);
		case 3:
			return new CromosomaP1F4(tolerancia, n);
		case 4:
			return new CromosomaP1F5(tolerancia);
		}
		return null;
	}

	private void cruce(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1, Cromosoma hijo2, int puntoCruce)
	{
		int i = 0; // Contador de gen.
		int j = 0; // Contador de bit dentro de gen.
		int k = 0; // Contador de posicion general en cromosoma.
		// Primera fase
		while(i < padre1.getNGenes() && k < puntoCruce)
		{
			boolean[] aleloHijo1 = hijo1.getGenes()[i].getAlelo();
			boolean[] aleloHijo2 = hijo2.getGenes()[i].getAlelo();
			boolean[] aleloPadre1 = padre1.getGenes()[i].getAlelo();
			boolean[] aleloPadre2 = padre2.getGenes()[i].getAlelo();
			if(k < puntoCruce)
			{
				for(j = 0; j < hijo1.getGenes()[i].getLongAlelo() && k < puntoCruce; j++)
				{
					aleloHijo1[j] = aleloPadre1[j];
					aleloHijo2[j] = aleloPadre2[j];
					k++;
				}
			}
			hijo1.getGenes()[i].setAlelo(aleloHijo1);
			hijo2.getGenes()[i].setAlelo(aleloHijo2);
		}
		// Segunda fase
		while(i < padre1.getNGenes())
		{
			boolean[] aleloHijo1 = hijo1.getGenes()[i].getAlelo();
			boolean[] aleloHijo2 = hijo2.getGenes()[i].getAlelo();
			boolean[] aleloPadre1 = padre1.getGenes()[i].getAlelo();
			boolean[] aleloPadre2 = padre2.getGenes()[i].getAlelo();
			while(j < hijo1.getGenes()[i].getLongAlelo())
			{
				aleloHijo1[j] = aleloPadre2[j];
				aleloHijo2[j] = aleloPadre1[j];
				j++;
			}
			hijo1.getGenes()[i].setAlelo(aleloHijo1);
			hijo2.getGenes()[i].setAlelo(aleloHijo2);
			i++;
			j = 0;
		}
		
		// Evaluacion
		double f;
		f = hijo1.evalua();
		hijo1.setFitness_bruto(f);
		f = hijo2.evalua();
		hijo2.setFitness_bruto(f);
	}

	public boolean isMaximizar() {
		return maximizar;
	}
	

}
