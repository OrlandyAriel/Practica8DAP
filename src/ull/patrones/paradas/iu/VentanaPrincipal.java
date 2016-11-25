package ull.patrones.paradas.iu;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class VentanaPrincipal extends JFrame
{
	private JRadioButton m_RJParadasGuaguas;
	private JRadioButton m_RJParadasTaxis;
	private JPanel m_panelSuperior;
	private JPanel m_panelCentral;
	
	private JComboBox<String> m_JCParadaGuaguas;
	private JComboBox<String> m_JCParadaTaxis;
	private ButtonGroup m_BGRadios;
	public VentanaPrincipal()
	{
		configRadios();
		configCombos();
		configPanelSuperior();
		configMapa();
		configPanelCentral();
		initComponent();
	}
	/**
	 * M�todo para configurar el panel superior
	 */
	private void configPanelSuperior()
	{
		m_panelSuperior = new JPanel();
		m_panelSuperior.setLayout(new FlowLayout());
		
		JPanel t_panelIzquierda = new JPanel();
		t_panelIzquierda.setLayout(new BoxLayout(t_panelIzquierda, BoxLayout.Y_AXIS));
		t_panelIzquierda.add(m_RJParadasTaxis);
		t_panelIzquierda.add(m_JCParadaTaxis,Box.LEFT_ALIGNMENT);
		
		JPanel t_panelDerecha = new JPanel();
		t_panelDerecha.setLayout(new BoxLayout(t_panelDerecha, BoxLayout.Y_AXIS));
		t_panelDerecha.add(m_RJParadasGuaguas);
		t_panelDerecha.add(m_JCParadaGuaguas);
		
		m_panelSuperior.add(t_panelIzquierda,FlowLayout.LEFT);
		m_panelSuperior.add(t_panelDerecha,FlowLayout.LEFT);
	}
	/**
	 * M�todo para crear los combobox y a�adirle sus datos
	 */
	private void configCombos()
	{
		String [] t_barrios = obtenerBarrios();
		m_JCParadaTaxis = new JComboBox<String>(t_barrios);
		m_JCParadaTaxis.setVisible(true);
		m_JCParadaTaxis.setEnabled(false);
		
		m_JCParadaGuaguas = new JComboBox<String>(t_barrios);
		m_JCParadaGuaguas.setVisible(true);
		m_JCParadaGuaguas.setEnabled(false);
	}
	private void configMapa()
	{
		
	}
	/**
	 * M�todo para configurar el panel central, donde ir� el mapa
	 */
	private void configPanelCentral()
	{
		m_panelCentral = new JPanel();
		m_panelCentral.setLayout(new FlowLayout());
		m_panelCentral.setVisible(true);
	}
	
	private String[] obtenerBarrios()
	{
		String []t_result={"Barrio la salud","Taco","cruz del se�or"};
		return t_result;
	}
	/**
	 * M�todo que configura los radiosButton
	 */
	private void configRadios()
	{
		m_BGRadios = new ButtonGroup();
		
		m_RJParadasTaxis = new JRadioButton("Paradas de Taxis");
		m_RJParadasTaxis.setVisible(true);
		
		m_RJParadasGuaguas = new JRadioButton("Paradas de Guaguas");
		m_RJParadasGuaguas.setVisible(true);
		
		m_BGRadios.add(m_RJParadasTaxis);
		m_BGRadios.add(m_RJParadasGuaguas);
	}
	/**
	 * M�todo para a�adir los componentes a la ventana principal y configurarla
	 */
	private void initComponent()
	{
		this.setTitle("Pr�ctica 8-DAP");
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		this.setSize(500, 500);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		this.add(m_panelSuperior,BorderLayout.NORTH);
		this.add(m_panelCentral,BorderLayout.CENTER);
	}
}
