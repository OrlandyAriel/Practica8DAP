package ull.patrones.paradas.lecturadatos;

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

public abstract class LeerJSON
{
	private String m_jsonFile;
	private boolean m_termino;
	private List<String> m_listaZona;

	public LeerJSON(String a_jsonFile)
	{
		m_jsonFile = a_jsonFile;
		m_listaZona = new ArrayList<>();
		m_termino = false;
	}



	public void leerDatos()
	{
		Thread hiloDescarga = new Thread()// Hilo para evitar que se bloquee la
		// aplicación
		{
			public void run()
			{
				URLConnection urlConn = null;
				InputStreamReader in = null;
				try
				{
					URL url = new URL(m_jsonFile);
					urlConn = url.openConnection();// abre la conexión con el
					// servidor
					if (urlConn != null)
					{
						// establece tiempo de espera para descargar, 1 minuto
						urlConn.setReadTimeout(60 * 1000);
					}
					if (urlConn != null && urlConn.getInputStream() != null)
					{
						// Charset.defaultCharset() utilizado para formatear el
						// fichero de entrada al juego de caracteres de la
						// maquina
						in = new InputStreamReader(urlConn.getInputStream(), Charset.defaultCharset());
						JsonReader jsonReader = Json.createReader(in);
						JsonObject json = jsonReader.readObject();
						JsonArray jsonArray = json.getJsonArray("docs");
						for (JsonValue jsonValue : jsonArray)
						{
							paradaConcreta((JsonObject) jsonValue);
						}
						setTermino(true);
					}
					in.close();
				} catch (Exception e)
				{
					throw new RuntimeException("Error mientras se conectaba a la URL:" + m_jsonFile, e);
				}
			}
		};
		hiloDescarga.start();
	}

	protected abstract void paradaConcreta(JsonObject jsonValue);

	public boolean getTermino()
	{
		return m_termino;
	}

	public void addZona(String a_zona)
	{

		m_listaZona.add(a_zona);
	}

	public void setTermino(boolean a_estado)
	{
		m_termino = a_estado;
	}

	public List<String> getListaZona()
	{
		while (m_termino != true)
		{
			System.out.print("");
		}
		return m_listaZona;
	}

	public abstract Geometry getLocalizacion(String a_barrio);
}
