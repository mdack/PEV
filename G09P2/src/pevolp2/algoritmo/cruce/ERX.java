package pevolp2.algoritmo.cruce;

import java.util.ArrayList;
import java.util.Random;

import pevolp2.algoritmo.cromosoma.Cromosoma;

public class ERX extends Cruce {

	@Override
	public void cruzar(Cromosoma padre1, Cromosoma padre2, Cromosoma hijo1,
			Cromosoma hijo2) {
		ArrayList<Integer> c1 = new ArrayList<Integer>();
		ArrayList<Integer> c2 = new ArrayList<Integer>();
		ArrayList<Integer> h1 = new ArrayList<Integer>();
		ArrayList<Integer> h2 = new ArrayList<Integer>();
		for(int i = 0; i < padre1.getNGenes(); i++)
		{
			c1.add(i, padre1.getGenes()[i].getAlelo());
			c2.add(i, padre2.getGenes()[i].getAlelo());
		}
		
		// Bucle de mapeado de conexiones
		ArrayList<ArrayList<Integer>> mapeado = new ArrayList<ArrayList<Integer>>();
		for(int i = 0; i <= padre1.getNGenes(); i++) mapeado.add(i, null);
		for(int i = 1; i <= padre1.getNGenes(); i++)
		{
			int index1 = c1.indexOf(i);
			int index2 = c2.indexOf(i);
			ArrayList<Integer> mapGen = new ArrayList<Integer>();
			if(index1 - 1 >= 0 && !mapGen.contains(padre1.getGenes()[index1-1].getAlelo())) mapGen.add(padre1.getGenes()[index1-1].getAlelo());
			if(index1 + 1 < padre1.getNGenes() && !mapGen.contains(padre1.getGenes()[index1+1].getAlelo())) mapGen.add(padre1.getGenes()[index1+1].getAlelo());
			if(index2 - 1 >= 0 && !mapGen.contains(padre2.getGenes()[index2-1].getAlelo())) mapGen.add(padre2.getGenes()[index2-1].getAlelo());
			if(index2 + 1 < padre2.getNGenes() && !mapGen.contains(padre2.getGenes()[index2+1].getAlelo())) mapGen.add(padre2.getGenes()[index2+1].getAlelo());
			mapeado.set(i, mapGen);
		}
		int camino1 = padre2.getGenes()[0].getAlelo();
		int camino2 = padre1.getGenes()[0].getAlelo();
		hijo1.getGenes()[0].setAlelo(camino1);
		h1.add(camino1);
		hijo2.getGenes()[0].setAlelo(camino2);
		h2.add(camino2);
		boolean hijo1ok = false;
		boolean hijo2ok = false;
		boolean blocked1 = false;
		boolean blocked2 = false;
		int limit1 = 0;
		int limit2 = 0;
		while(!hijo1ok || !hijo2ok)
		{
			for(int i = 1; i < padre1.getNGenes(); i++)
			{
				if(!hijo1ok)
				{
					// Hijo 1
					int minCam = Integer.MAX_VALUE;
					ArrayList<Integer> map1 = mapeado.get(camino1);
					for(int j = 0; j < map1.size(); j++) 
						if(mapeado.get(map1.get(j)).size() < minCam && !h1.contains(map1.get(j))) minCam = mapeado.get(map1.get(j)).size();
					// Ahora que tenemos el numero minimo de conexiones se guardan los genes que tienen ese numero de conexiones.
					ArrayList<Integer> conn1 = new ArrayList<Integer>();
					for(int j = 0; j < map1.size(); j++)
						if(mapeado.get(map1.get(j)).size() == minCam && !h1.contains(map1.get(j))) conn1.add(map1.get(j));
					if(conn1.size() == 1) 
					{
						if(!h1.contains(conn1.get(0)))
						{
							hijo1.getGenes()[i].setAlelo(conn1.get(0));
							h1.add(conn1.get(0));
							camino1 = conn1.get(0);
						}
					}
					else if(conn1.size() > 1)
					{
						Random rnd = new Random();
						int ind = rnd.nextInt(conn1.size());
						int vueltas = 0;
						while(!h1.contains(conn1.get(ind)) && vueltas < conn1.size()*2){
							ind = rnd.nextInt(conn1.size());
							vueltas++;
						}
						if(!h1.contains(conn1.get(ind)))
						{
							hijo1.getGenes()[i].setAlelo(conn1.get(ind));
							h1.add(conn1.get(ind));
							camino1 = conn1.get(ind);
						}
					}
				}
				if(!hijo2ok)
				{
					// Hijo 2
					int minCam = Integer.MAX_VALUE;
					ArrayList<Integer> map2 = mapeado.get(camino2);
					for(int j = 0; j < map2.size(); j++) 
						if(mapeado.get(map2.get(j)).size() < minCam && !h2.contains(map2.get(j))) minCam = mapeado.get(map2.get(j)).size();
					// Ahora que tenemos el numero minimo de conexiones, se guardan los genes que tienen ese numero de conexiones.
					ArrayList<Integer> conn2 = new ArrayList<Integer>();
					for(int j = 0; j < map2.size(); j++)
						if(mapeado.get(map2.get(j)).size() == minCam && !h2.contains(map2.get(j))) conn2.add(map2.get(j));
					if(conn2.size() == 1) 
					{
						if(!h2.contains(conn2.get(0)))
						{
							hijo2.getGenes()[i].setAlelo(conn2.get(0));
							h2.add(conn2.get(0));
							camino2 = conn2.get(0);
						}
					}
					else if(conn2.size() > 1)
					{
						Random rnd = new Random();
						int ind = rnd.nextInt(conn2.size());
						int vueltas = 0;
						while(!h2.contains(conn2.get(ind)) && vueltas < conn2.size()*2){
							ind = rnd.nextInt(conn2.size());
							vueltas++;
						}
						if(!h2.contains(conn2.get(ind)))
						{
							hijo2.getGenes()[i].setAlelo(conn2.get(ind));
							h2.add(conn2.get(ind));
							camino2 = conn2.get(ind);
						}
					}
				}
			}
			// Comprueba que no ha habido bloqueos; si hubo bloqueo, empieza por el otro gen.
			if(h1.size() == padre1.getNGenes()) hijo1ok = true;
			else
			{
				Random rnd = new Random();
				if(!blocked1)
				{
					camino1 = padre1.getGenes()[0].getAlelo();
					blocked1 = true;
				}
				else
				{
					if(limit1 < 10)
					{
						camino1 = rnd.nextInt(padre1.getNGenes())+1;
						limit1++;
					}
					else
					{
						hijo1ok = true;
						hijo1 = padre1.copia();
					}
				}
				h1 = new ArrayList<Integer>();
				if(!hijo1ok)
					hijo1.getGenes()[0].setAlelo(camino1);
				h1.add(camino1);
			}
			if(h2.size() == padre2.getNGenes()) hijo2ok = true;
			else
			{
				Random rnd = new Random();
				if(!blocked2)
				{
					camino2 = padre2.getGenes()[0].getAlelo();
					blocked2 = true;
				}
				else
				{
					if(limit2 < 10)
					{
						camino2 = rnd.nextInt(padre2.getNGenes())+1;
						limit2++;
					}
					else
					{
						hijo2ok = true;
						hijo2 = padre2.copia();
					}
				}
				h2 = new ArrayList<Integer>();
				if(!hijo2ok)
					hijo2.getGenes()[0].setAlelo(camino2);
				h2.add(camino2);
			}
		}
		hijo1 = hijo1.copia();
		hijo2 = hijo2.copia();
	}

}
