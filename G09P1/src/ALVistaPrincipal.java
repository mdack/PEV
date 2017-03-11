import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ALVistaPrincipal implements ActionListener{
	
	int nGeneracion, tamPoblacion;
	float probCruce, probMutacion, precision;
	boolean esRuleta = true, elitismo = true;
	VistaPrincipal window;
	
	
	public ALVistaPrincipal(JTextField tnGen, JTextField ttamPob, JTextField tproCruce, JTextField tproMutacion,
			JTextField tprecision, JComboBox<String> cseleccion, JComboBox<String> celitismo,
			VistaPrincipal vistaPrincipal) {
		try{
			nGeneracion = Integer.parseInt(tnGen.getText());
			tamPoblacion = Integer.parseInt(ttamPob.getText());
			probCruce = Integer.parseInt(tproCruce.getText());
			
			if(probCruce < 100 && probCruce > 0){
				probMutacion = Integer.parseInt(tproMutacion.getText());
				if(probMutacion < 100 && probMutacion > 0){
					probCruce = probCruce/100;
					probMutacion = probMutacion/100;
					if(cseleccion.getSelectedIndex() == 1){
						esRuleta = false;
					}
					if(celitismo.getSelectedIndex() == 1){
						elitismo = false;
					}
					window = vistaPrincipal;					
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

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
