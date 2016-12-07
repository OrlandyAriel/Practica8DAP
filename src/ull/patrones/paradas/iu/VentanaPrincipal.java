package ull.patrones.paradas.iu;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import com.teamdev.jxbrowser.chromium.Browser;
import com.teamdev.jxbrowser.chromium.swing.BrowserView;

import ull.patrones.paradas.lecturadatos.LeerJSON;
import ull.patrones.paradas.lecturadatos.LeerJSONGuagua;
import ull.patrones.paradas.lecturadatos.LeerJSONTaxis;
import ull.patrones.paradas.mapa.Mapa;
/**
 * 
 * @author Orlandy Ariel S�nchez A.
 *
 */
public class VentanaPrincipal extends JFrame
{
	private static final long serialVersionUID = -8439451759008893300L;
	private JRadioButton m_RJParadasGuaguas;
	private JRadioButton m_RJParadasTaxis;
	private JPanel m_panelSuperior;
	private JPanel m_panelCentral;

	private JComboBox<String> m_JCParadas;
	private ButtonGroup m_BGRadios;
	private Browser m_browser;
	private BrowserView m_browserView;

	private LeerJSON m_leerJson;
	private List<String> m_listaZonas;
	/**
	 * Constructor por defecto
	 */
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
	/**
	 * M�todo que crea una lista por defecto para los Barrios/zonas
	 */
	private void listaZonasPorDefecto()
	{
		m_listaZonas = new ArrayList<>();
		m_listaZonas.add("--Seleccionar--");
	}

	/**
	 * M�todo para configurar el panel superior
	 */
	private void configPanelSuperior()
	{
		m_panelSuperior = new JPanel();
		m_panelSuperior.setLayout(new BoxLayout(m_panelSuperior, BoxLayout.Y_AXIS));

		JPanel t_panelArriba = new JPanel();
		t_panelArriba.setLayout(new FlowLayout());
		t_panelArriba.add(m_RJParadasTaxis);
		t_panelArriba.add(m_RJParadasGuaguas);

		JPanel t_panelAbajo = new JPanel();
		t_panelAbajo.setLayout(new FlowLayout());
		t_panelAbajo.add(m_JCParadas);

		m_panelSuperior.add(t_panelArriba, BorderLayout.SOUTH);
		m_panelSuperior.add(t_panelAbajo, BorderLayout.NORTH);
	}

	/**
	 * M�todo para crear los combobox y a�adirle sus datos
	 */
	private void configCombos()
	{
		m_JCParadas = new JComboBox<String>();
		datosPorDefectoCombobox();
		m_JCParadas.setVisible(true);
		m_JCParadas.addItemListener(
				new ItemListener()
				{
					@Override
					public void itemStateChanged(ItemEvent arg0)
					{
						cargarMapa(m_JCParadas.getSelectedItem().toString());
					}
				}
		);
	}
	/**
	 * M�todo para poner valores por defecto al JComboBox
	 */
	private void datosPorDefectoCombobox()
	{
		DefaultComboBoxModel<String> a_com = new DefaultComboBoxModel<String>();
		a_com.addElement("--Seleecionar--");
		m_JCParadas.setModel(a_com);
	}
	/**
	 * M�todo para inicializar los valores de navegador de JxBrowser
	 */
	private void configMapa()
	{
		m_browser = new Browser();
		m_browserView = new BrowserView(m_browser);
		mapaPorDefecto();
	}

	/**
	 * M�todo para configurar el panel central, donde ir� el mapa
	 */
	private void configPanelCentral()
	{
		m_panelCentral = new JPanel();
		m_panelCentral.setVisible(true);
		m_panelCentral.add(m_browserView);
	}
	/**
	 * M�todo que a�ade los barrios/zonas al JComboBox
	 * @param a_combo
	 */
	private void obtenerBarrios(JComboBox<String> a_combo)
	{
		for (int i = 0; i < m_listaZonas.size(); i++)
		{
			a_combo.addItem((m_listaZonas.get(i)));
		}
	}

	/**
	 * M�todo utilizada para cuando se selecciona un item del combobox
	 */
	private void activarCombo()
	{
		datosPorDefectoCombobox();
		obtenerBarrios(m_JCParadas);
	}

	/**
	 * M�todo que configura los radiosButton
	 */
	private void configRadios()
	{
		m_BGRadios = new ButtonGroup();

		m_RJParadasTaxis = new JRadioButton("Paradas de Taxis");
		m_RJParadasTaxis.setVisible(true);
		m_RJParadasTaxis.addActionListener(
				new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						actionRadioButtonPerformed(new LeerJSONTaxis());
					}
				}
		);

		m_RJParadasGuaguas = new JRadioButton("Paradas de Guaguas");
		m_RJParadasGuaguas.setVisible(true);
		m_RJParadasGuaguas.addActionListener(
				new ActionListener()
				{
					@Override
					public void actionPerformed(ActionEvent arg0)
					{
						actionRadioButtonPerformed(new LeerJSONGuagua());
					}
				}
		);
		m_BGRadios.add(m_RJParadasTaxis);
		m_BGRadios.add(m_RJParadasGuaguas);
	}
	/**
	 *M�todo utilizado obtener la lista de las zonas partiendo del 
	 *par�metro, se activa cuando se pulsa alguno de los JRadioButton
	 * @param leerJSON
	 */
	private void actionRadioButtonPerformed(LeerJSON leerJSON)
	{
		m_leerJson = leerJSON;
		m_listaZonas = m_leerJson.getListaZona();

		activarCombo();
	}
	/**
	 * Permite cargar el mapa seg�n se seleccione el barrio o zona del JComboBox
	 * @param zona
	 */
	private void cargarMapa(String zona)
	{
		Thread a = new Thread()
		{
			@Override
			public void run()
			{
				Mapa t = new Mapa();
				String html = t.getMapa(m_leerJson.getGeometrys(zona));
				System.out.println(html);

				m_browser.loadHTML(html);
			}
		};

		// String htm_carga="<html>\n <head>\n <title>PROBANDO</title>\n
		// <script>\n window.onload = detectarCarga;\n function
		// detectarCarga(){\n
		// document.getElementById(\"carga\").style.display=\"none\";\n }\n
		// </script>\n </head>\n <body>\n <div id=\"carga\">\n <img
		// src=\"http://i.imgur.com/B96QZec.gif\" />\n </div>\n
		// </body>\n</html>";
		// m_browser.loadHTML(htm_carga);;
		a.start();
	}
	/**
	 * M�todo que da valores por defecto (un texto)
	 * al navegador JxBrowser
	 */
	private void mapaPorDefecto()
	{
		Mapa t = new Mapa();
		String html = t.getMapa(new ArrayList<>());
		System.out.println(html);

		m_browser.loadHTML(html);
	}

	/**
	 * M�todo para a�adir los componentes a la ventana principal y configurarla
	 */
	private void initComponent()
	{
		this.setTitle("Pr�ctica 8-DAP");
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
