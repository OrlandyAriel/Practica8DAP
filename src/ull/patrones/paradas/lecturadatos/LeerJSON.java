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

import ull.patrones.paradas.datos.Geometry;

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

	public abstract void paradaConcreta(JsonObject jsonValue);

	public abstract List<Geometry> getGeometrys(String a_barrio);

	public String getURL()
	{
		return m_jsonFile;
	}

	public boolean leerDatos()
	{
		long a =System.currentTimeMillis();
		long b =0;
		long aa = 0;
		long bb=0;
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
				b=System.currentTimeMillis();
				aa = System.currentTimeMillis();
				for (JsonValue jsonValue : jsonArray)
				{
					paradaConcreta((JsonObject) jsonValue);
				}
				bb = System.currentTimeMillis();
			}
			in.close();
			m_termino = true;
			System.err.println("tiempo de descarga"+(a-b));
			System.out.println("tiepo de consturcción" +(aa-bb));
		} catch (Exception e)
		{
			throw new RuntimeException("Error mientras se conectaba a la URL:" + m_jsonFile, e);
		}
		return m_termino;
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
