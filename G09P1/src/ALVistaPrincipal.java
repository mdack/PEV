import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ALVistaPrincipal{
	
	int nGeneracion, tamPoblacion, funcion, n, tipoSel;
	double probCruce;
	double precision;
	double probMutacion;
	boolean elitismo = true;
	
	public ALVistaPrincipal(JTextField tnGen, JTextField ttamPob, JTextField tproCruce, JTextField tproMutacion,
			JTextField tprecision, JComboBox<String> cseleccion,JComboBox<String> celitismo,   JComboBox<String> cfuncion, JTextField tvalorN) {
		try{
			nGeneracion = Integer.parseInt(tnGen.getText());
			tamPoblacion = Integer.parseInt(ttamPob.getText());
			probCruce = Double.parseDouble(tproCruce.getText());
			precision = Double.parseDouble(tprecision.getText());
			n = Integer.parseInt(tvalorN.getText());
			
			if(probCruce < 100 && probCruce > 0){
				probMutacion = Double.parseDouble(tproMutacion.getText());
				if(probMutacion < 100 && probMutacion > 0){
					probCruce = probCruce/100;
					probMutacion = probMutacion/100;
					if(celitismo.getSelectedIndex() == 0){
						elitismo = false;
					}
					tipoSel = cseleccion.getSelectedIndex();
					funcion = cfuncion.getSelectedIndex();	
					
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
		switch(funcion){
		case 0:
			new AGeneticoP1F1(tamPoblacion, nGeneracion, probCruce, probMutacion, precision, elitismo, tipoSel);
			break;
		case 1:
			break;
		case 2:
			new AGeneticoP1F3(tamPoblacion, nGeneracion, probCruce, probMutacion, precision,elitismo, tipoSel);
			break;
		case 3:
			break;
		case 4:
			break;
		
	}
	}
}
