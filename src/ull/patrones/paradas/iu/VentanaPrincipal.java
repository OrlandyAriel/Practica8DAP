package ull.patrones.paradas.iu;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import ull.patrones.paradas.lecturadatos.Geometry;
import ull.patrones.paradas.lecturadatos.ILeerJSON;
import ull.patrones.paradas.lecturadatos.LeerJSONGuagua;
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
	
	private ILeerJSON m_leerJson;
	private List<String> m_listaZonas;

	public VentanaPrincipal()
	{
		listaZonasPorDefecto();
		configRadios();
		configCombos();
		configPanelSuperior();
		configMapa();
		configPanelCentral();
		initComponent();
	}

	private void listaZonasPorDefecto()
	{
		m_listaZonas = new ArrayList<>();
		m_listaZonas.add("--Seleccionar--");
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
		String []t_barrios = {"--Sin datos--"};
		m_JCParadaTaxis = new JComboBox<String>(t_barrios);
		m_JCParadaTaxis.setVisible(true);
		m_JCParadaTaxis.setEnabled(false);
		
		m_JCParadaGuaguas = new JComboBox<String>(t_barrios);
		m_JCParadaGuaguas.setVisible(true);
		m_JCParadaGuaguas.setEnabled(false);
		m_JCParadaGuaguas.addItemListener(new ItemListener()
		{
			
			@Override
			public void itemStateChanged(ItemEvent arg0)
			{
				cargarMapa(m_JCParadaGuaguas.getSelectedItem().toString());
				
			}
		});
		
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

	private void obtenerBarrios(JComboBox<String> a_combo)
	{
		String[] t_result =new String[m_listaZonas.size()];
		System.out.println(m_listaZonas.size());
		for (int i = 0; i < t_result.length; i++)
		{
			a_combo.addItem((m_listaZonas.get(i)));
		}
	}
	private void activarCombo()
	{
		
		if(m_RJParadasGuaguas.isSelected())
		{
			m_JCParadaGuaguas.setEnabled(true);
			obtenerBarrios(m_JCParadaGuaguas);
		}
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
		m_RJParadasGuaguas.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				actionGuaguaRadioButtonPerformed(new LeerJSONGuagua());
			}
		});
		m_BGRadios.add(m_RJParadasTaxis);
		m_BGRadios.add(m_RJParadasGuaguas);
	}
	private void actionGuaguaRadioButtonPerformed(ILeerJSON leerJSON)
	{
		m_leerJson = leerJSON;
		m_listaZonas = m_leerJson.getListaZona();
		
		activarCombo();
		
	}
	private void cargarMapa(String zona)
	{
		Geometry t_geo=m_leerJson.getLocalizacion(zona);
		Double a_lat = t_geo.getM_latitud();
		Double a_lng = t_geo.getM_longitud();
		Thread a = new Thread()
		{
			@Override
			public void run()
			{
			/*	try
				{
					sleep(10000);
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}*/
				Mapa t = new Mapa();
				System.out.println("long:"+a_lng+", lat:"+a_lat);
				m_browser.loadHTML(t.getMapa(a_lat,a_lng));
			}
		};
		
		//String htm_carga="<html>\n  <head>\n    <title>PROBANDO</title>\n    <script>\n      window.onload = detectarCarga;\n      function detectarCarga(){\n         document.getElementById(\"carga\").style.display=\"none\";\n      }\n    </script>\n  </head>\n  <body>\n  <div id=\"carga\">\n    <img src=\"http://i.imgur.com/B96QZec.gif\" />\n  </div>\n  </body>\n</html>";
		//m_browser.loadHTML(htm_carga);;
		a.start();
	}
	/**
	 * Método para añadir los componentes a la ventana principal y configurarla
	 */
	private void initComponent()
	{
		this.setTitle("Práctica 8-DAP");
		this.setLayout(new BorderLayout());
		this.setVisible(true);
		this.setSize(700, 500);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		this.add(m_panelSuperior, BorderLayout.SOUTH);
		configPanelCentral();
		this.add(m_browserView, BorderLayout.CENTER);
	}
}
