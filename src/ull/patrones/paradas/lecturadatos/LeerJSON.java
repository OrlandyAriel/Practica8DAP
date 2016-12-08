package ull.patrones.paradas.lecturadatos;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;

import ull.patrones.paradas.datos.Geometry;

public abstract class LeerJSON
{
	private String m_jsonFile;
	private boolean m_termino;
	private List<String> m_listaZona;
	private URLConnection m_urlConnetion;
	private InputStreamReader m_inputStreamReader;
	private URL m_url;
	private JsonReader m_jsonReader;
	private JsonObject m_jsonObjet;

	public LeerJSON(String a_jsonFile)
	{
		m_jsonFile = a_jsonFile;
		m_listaZona = new ArrayList<>();
		m_termino = false;
		m_urlConnetion = null;
		m_inputStreamReader = null;
	}

	public LeerJSON()
	{
		m_listaZona = new ArrayList<>();
		m_termino = false;
	}

	public abstract void paradaConcreta(JsonObject jsonValue);

	public abstract List<Geometry> getGeometrys(String a_barrio);

	public String getURL()
	{
		return m_jsonFile;
	}

	public void leerDatos()
	{
		try
		{
			m_url = new URL(m_jsonFile);
			m_urlConnetion = m_url.openConnection();// abre la conexión con el
			// servidor
			if (m_urlConnetion != null)
			{
				// establece tiempo de espera para descargar, 1 minuto
				m_urlConnetion.setReadTimeout(60 * 1000);
			}
			if (m_urlConnetion != null && m_urlConnetion.getInputStream() != null)
			{
				// Charset.defaultCharset() utilizado para formatear el
				// fichero de entrada al juego de caracteres de la
				// maquina
				m_inputStreamReader = new InputStreamReader(m_urlConnetion.getInputStream(), Charset.defaultCharset());
				m_jsonReader = Json.createReader(m_inputStreamReader);
			}

		} catch (Exception e)
		{
			throw new RuntimeException("Error mientras se conectaba a la URL:" + m_jsonFile, e);
		}
	}

	public void paradasConfig()
	{
		Thread hilo = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					m_jsonObjet = m_jsonReader.readObject();
					JsonArray jsonArray = m_jsonObjet.getJsonArray("docs");
					for (JsonValue jsonValue : jsonArray)
					{
						paradaConcreta((JsonObject) jsonValue);
					}
					m_termino = true;
					m_inputStreamReader.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		});
		hilo.start();
	}

	public boolean getTermino()
	{
		return m_termino;
	}

	public void addZona(String a_zona)
	{
		if (!m_listaZona.contains(a_zona))
			m_listaZona.add(a_zona);
	}

	public List<String> getListaZona()
	{
		return m_listaZona;
	}
}
