package pevolp2.presentacion;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ALVistaPrincipal{
	
	int nGeneracion, tamPoblacion, funcion, n, tipoSel;
	int tmutacion, tcruce;
	double probCruce;
	double precision;
	double probMutacion;
	boolean elitismo = true;
	
	public ALVistaPrincipal(JTextField tnGen, JTextField ttamPob, JTextField tproCruce, JTextField tproMutacion,
			JTextField tprecision, JComboBox<String> cseleccion,JComboBox<String> celitismo,   JComboBox<String> cfuncion, JComboBox<String> cmutacion, JComboBox<String> ccruce, String n2) {
		try{
			nGeneracion = Integer.parseInt(tnGen.getText());
			tamPoblacion = Integer.parseInt(ttamPob.getText());
			probCruce = Double.parseDouble(tproCruce.getText());
			precision = Double.parseDouble(tprecision.getText());
			
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
					
					if(tmutacion == 3){
						int aux = Integer.parseInt(n2);
						if(aux < tamPoblacion && aux >= 0){
							n = aux;
						}else{
							JOptionPane.showMessageDialog(new JFrame(),
								    "El valor de n debe estar entre 0 y el tamaño de la población",
								    "Valor de n incorrecto",
								    JOptionPane.ERROR_MESSAGE);
						}
					}
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
				    "¡Deben ser números!",
				    JOptionPane.ERROR_MESSAGE);
		}
	}

	public void action() {/*
		AGenetico AG = new AGenetico(tamPoblacion, nGeneracion, probCruce, probMutacion, precision, elitismo, funcion, n);
		
		algoritmoGenetico(AG, funcion+1);*/
	}
/*
	private void algoritmoGenetico(AGenetico aG, int f) {
		String cadena = "";
		cadena += "***************** Función " + f + " *********************\n";
		
		aG.inicializar();
		aG.evaluar();
		for(int i = 0; i < aG.getNumMaxGen(); i++)
		{	
			cadena += "--------------------------------------------------------------\n";
			cadena += ("* Generación " + (i+1) + "\n");
			cadena += "--------------------------------------------------------------\n";
			cadena += aG.toString();
			if(elitismo){
				aG.seleccionaElite();
			}
			aG.seleccion(tipoSel);
			aG.reproduccion();
			aG.mutacion();
			if(elitismo){
				aG.insertaElite();
			}
			aG.evaluar();
		}
		VistaPrincipal.addText(cadena);
	}*/
}
