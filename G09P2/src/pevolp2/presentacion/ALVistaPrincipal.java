package pevolp2.presentacion;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import pevolp2.algoritmo.AGenetico;

public class ALVistaPrincipal{
	
	int nGeneracion, tamPoblacion, funcion,tipoSel;
	int tmutacion, tcruce;
	double probCruce;
	double probMutacion;
	boolean elitismo = true;
	
	public ALVistaPrincipal(JTextField tnGen, JTextField ttamPob, JTextField tproCruce, JTextField tproMutacion, JComboBox<String> cseleccion,JComboBox<String> celitismo,   JComboBox<String> cfuncion, JComboBox<String> cmutacion, JComboBox<String> ccruce) {
		try{
			nGeneracion = Integer.parseInt(tnGen.getText());
			tamPoblacion = Integer.parseInt(ttamPob.getText());
			probCruce = Double.parseDouble(tproCruce.getText());
			
			if(probCruce < 100 && probCruce >= 0){
				probMutacion = Double.parseDouble(tproMutacion.getText());
				if(probMutacion < 100 && probMutacion >= 0){
					probCruce = probCruce/100;
					probMutacion = probMutacion/100;
					if(celitismo.getSelectedIndex() == 0){
						elitismo = false;
					}
					tipoSel = cseleccion.getSelectedIndex();
					funcion = cfuncion.getSelectedIndex();
					tmutacion = cmutacion.getSelectedIndex();
					tcruce = ccruce.getSelectedIndex();
					
				}else{
					JOptionPane.showMessageDialog(new JFrame(),
						    "El porcentaje debe de ser entre 0 y 100%",
						    "Probabilidad de Cruce incorrecta",
						    JOptionPane.ERROR_MESSAGE);
				}
			}
			else{
				JOptionPane.showMessageDialog(new JFrame(),
					    "El porcentaje debe de ser entre 0 y 100%",
					    "Probabilidad de Cruce incorrecta",
					    JOptionPane.ERROR_MESSAGE);
			}
			
		}catch(NumberFormatException e){
			JOptionPane.showMessageDialog(new JFrame(),
				    "Campos incorrectos",
				    "�Deben ser n�meros!",
				    JOptionPane.ERROR_MESSAGE);
		}
	}

	public void action() {
		AGenetico AG = new AGenetico(tamPoblacion, nGeneracion, probCruce, probMutacion, elitismo, funcion, tcruce);

		algoritmoGenetico(AG);
	}

	private void algoritmoGenetico(AGenetico aG) {
		String cadena = "";
		cadena += "***************** Archivo " + funcion + " *********************\n";
		
		aG.inicializar();
		aG.evaluar();
		for(int i = 0; i < aG.getNumMaxGen(); i++)
		{	
			cadena += "--------------------------------------------------------------\n";
			cadena += ("* Generaci�n " + (i+1) + "\n");
			cadena += "--------------------------------------------------------------\n";
			cadena += aG.toString();
			if(elitismo){
				aG.seleccionaElite();
			}
			aG.seleccion(tipoSel);
			aG.reproduccion();
			aG.mutacion(tmutacion);
			aG.mutacion(2);
			if(elitismo){
				aG.insertaElite();
			}
			aG.evaluar();
		}
		VistaPrincipal.addText(cadena);
	}
}
