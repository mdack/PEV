import java.awt.*;
import javax.swing.*;
import javax.swing.border.Border;

import org.math.plot.Plot2DPanel;

public class VistaPrincipal extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lnGen, ltamPob, lproCruce, lproMutacion, lprecision, lfuncion, lvalorN, lseleccion;
	private JTextField tnGen, ttamPob, tproCruce, tproMutacion, tprecision;
	private JComboBox<String> cFuncion, cseleccion, cvalorN;
	private JButton button;
	private JTextArea area;
	private JScrollPane scroll;
	private JProgressBar progressBar;
	Plot2DPanel plot;
	JPanel window;
	
	public VistaPrincipal(){
		initComponents();
		
		addTop();	
		addCenter();	
		addBottom();	
		addWest();
		addEast();
		
		this.add(window);
	}

	private void initComponents() {
		this.setTitle("Practica 1 - PEV");
		this.setMinimumSize(new Dimension(1000,700));	
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);	
		this.setLocationRelativeTo(null);
				
		window = new JPanel();
		window.setLayout(new BorderLayout());
		
		//label
		lnGen = new JLabel("Número de Generaciones:");
		ltamPob = new JLabel("Tamaño de la Población:");
		lproCruce = new JLabel("Probabilidad de cruce:");
		lproMutacion = new JLabel("Probabilidad de Mutación:");
		lprecision = new JLabel("Precisión:");
		lfuncion = new JLabel("Función a optimizar:");
		lvalorN = new JLabel("Valor de n:");
		lseleccion = new JLabel("Tipo de selección");
		
		//texto
		tnGen = new JTextField();
		ttamPob = new JTextField();
		tproCruce = new JTextField();
		tproMutacion = new JTextField();
		tprecision = new JTextField();
		
		//combo
		cFuncion = new JComboBox<String>();
		cFuncion.addItem("Función 1");
		cFuncion.addItem("Función 2");
		cFuncion.addItem("Función 3");
		
		cseleccion = new JComboBox<String>();
		cseleccion.addItem("Ruleta");
		cseleccion.addItem("Torneo");
		
		cvalorN = new JComboBox<String>();
		cvalorN.addItem("1");
		
		//Grafica
		plot = new Plot2DPanel();
		
		//textArea
		area = new JTextArea();
		area.setColumns(20);
		area.setRows(5);
		scroll = new JScrollPane();
		scroll.setViewportView(area);
		
		//boton
		button = new JButton("Comenzar");
		
		//barra de progreso
		progressBar = new JProgressBar();

	}

	private void addEast() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		panel.add(area, BorderLayout.CENTER);
		
		window.add(panel, BorderLayout.EAST);		
	}

	private void addWest() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(8, 2, 25, 25));
		
		panel.add(lnGen);
		panel.add(tnGen);
		
		panel.add(ltamPob);
		panel.add(ttamPob);
		
		panel.add(lproCruce);
		panel.add(tproCruce);
		
		panel.add(lproMutacion);
		panel.add(tproMutacion);
		
		panel.add(lprecision);
		panel.add(tprecision);
		
		panel.add(lfuncion);
		panel.add(cFuncion);
		
		panel.add(lvalorN);
		panel.add(cvalorN);
		
		panel.add(lseleccion);
		panel.add(cseleccion);
		
		window.add(panel, BorderLayout.WEST);
	}

	private void addCenter() {
		plot.addLegend("SOUTH");
		
		window.add(plot, BorderLayout.CENTER);
	}

	private void addBottom() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		panel.add(button, BorderLayout.CENTER);
		
		window.add(panel, BorderLayout.SOUTH);
	}

	private void addTop() {
		 progressBar.setValue(25);
		 progressBar.setStringPainted(true);
		 Border border = BorderFactory.createTitledBorder("Reading...");
		 progressBar.setBorder(border);
		 
		 window.add(progressBar, BorderLayout.NORTH);

	}

}