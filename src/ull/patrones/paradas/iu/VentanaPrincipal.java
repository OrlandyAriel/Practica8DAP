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

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import ull.patrones.paradas.mapa.Mapa;

public class VentanaPrincipal extends JFrame
{
	private static final long serialVersionUID = -8439451759008893300L;
	private JRadioButton m_RJParadasGuaguas;
	private JRadioButton m_RJParadasTaxis;
	private JPanel m_panelSuperior;
	private JPanel m_panelCentral;

	private JComboBox<String> m_JCParadaGuaguas;
	private JComboBox<String> m_JCParadaTaxis;
	private ButtonGroup m_BGRadios;
	private Browser m_browser;
	private BrowserView m_browserView;

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
	 * Método para configurar el panel superior
	 */
	private void configPanelSuperior()
	{
		m_panelSuperior = new JPanel();
		m_panelSuperior.setLayout(new FlowLayout());

		JPanel t_panelIzquierda = new JPanel();
		t_panelIzquierda.setLayout(new BoxLayout(t_panelIzquierda, BoxLayout.Y_AXIS));
		t_panelIzquierda.add(m_RJParadasTaxis);
		t_panelIzquierda.add(m_JCParadaTaxis, Box.LEFT_ALIGNMENT);

		JPanel t_panelDerecha = new JPanel();
		t_panelDerecha.setLayout(new BoxLayout(t_panelDerecha, BoxLayout.Y_AXIS));
		t_panelDerecha.add(m_RJParadasGuaguas);
		t_panelDerecha.add(m_JCParadaGuaguas);

		m_panelSuperior.add(t_panelIzquierda, FlowLayout.LEFT);
		m_panelSuperior.add(t_panelDerecha, FlowLayout.LEFT);
	}

	/**
	 * Método para crear los combobox y añadirle sus datos
	 */
	private void configCombos()
	{
		String[] t_barrios = obtenerBarrios();
		m_JCParadaTaxis = new JComboBox<String>(t_barrios);
		m_JCParadaTaxis.setVisible(true);
		m_JCParadaTaxis.setEnabled(false);

		m_JCParadaGuaguas = new JComboBox<String>(t_barrios);
		m_JCParadaGuaguas.setVisible(true);
		m_JCParadaGuaguas.setEnabled(false);
	}

	private void configMapa()
	{
		m_browser = new Browser();
		m_browserView = new BrowserView(m_browser);
	}

	/**
	 * Método para configurar el panel central, donde irá el mapa
	 */
	private void configPanelCentral()
	{
		m_panelCentral = new JPanel();
		m_panelCentral.setVisible(true);
		m_panelCentral.add(m_browserView);
	}

	private String[] obtenerBarrios()
	{
		String[] t_result =
		{ "Barrio la salud", "Taco", "cruz del señor" };
		return t_result;
	}

	/**
	 * Método que configura los radiosButton
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
	 * Método para añadir los componentes a la ventana principal y configurarla
	 */
	private void initComponent()
	{
		this.setTitle("Práctica 8-DAP");
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		this.setSize(500, 500);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		this.add(m_panelSuperior, BorderLayout.NORTH);
		configPanelCentral();
		this.add(m_browserView, BorderLayout.CENTER);
		double lat = 28.445665227752624;
		double lng = -16.308826310768087;
		Thread a = new Thread()
		{
			@Override
			public void run()
			{
				Mapa t = new Mapa(lat, lng);
				System.err.println(t.getMapa());
				m_browser.loadHTML(t.getMapa());
			}

		};
		a.start();

	}
}
