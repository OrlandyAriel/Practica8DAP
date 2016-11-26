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

public class LeerJSONGuagua implements ILeerJSON
{
	private final String JSON_URL_GUAGUA= "http://www.santacruzdetenerife.es/opendata/dataset/07e107d2-209a-4bfa-862e-00fcf497ae1d/resource/5ba90d75-1fd4-4304-b41c-7c2537891fa1/download/busturisticoparadas.json";
	private List<String> m_listaZona;
	private List<ParadaGuagua> m_listaParadas;
	private boolean m_terminoLeer;
	
	public LeerJSONGuagua()
	{
		m_listaZona = new ArrayList<>();
		m_terminoLeer = false;
		m_listaParadas = new ArrayList<>();
		leerDatos();
	}
	private void leerDatos()
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
					URL url = new URL(JSON_URL_GUAGUA);
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
						m_terminoLeer = true;
					}
					in.close();
				} catch (Exception e)
				{
					throw new RuntimeException("Error mientras se conectaba a la URL:" + JSON_URL_GUAGUA, e);
				}
			}
		};
		hiloDescarga.start();
	}
	private void paradaConcreta(JsonObject a_jsonObject)
	{
		ParadaGuagua t_paradaGuagua = new ParadaGuagua();

		Geometry t_geometry = new Geometry();
		
		JsonObject t_sonGeo = a_jsonObject.getJsonObject("geometry");
		t_geometry.setType(t_sonGeo.getString("type"));
		JsonArray t_arrayCoor = t_sonGeo.getJsonArray("coordinates");
		
		List<Double> t_coordenadas = new ArrayList<>();
		for (JsonValue jsonValue : t_arrayCoor)
		{
			t_coordenadas.add(Double.parseDouble(jsonValue.toString()));
		}
		t_geometry.setCoordinates(t_coordenadas);
		t_paradaGuagua.setGeometry(t_geometry);
		t_paradaGuagua.setGeocodigo(a_jsonObject.getString("GEOCODIGO"));
		t_paradaGuagua.setParada(a_jsonObject.getString("PARADA"));
		t_paradaGuagua.setUtm_x(Double.parseDouble(a_jsonObject.getJsonNumber("UTM_X").toString()));
		t_paradaGuagua.setUtm_y(Double.parseDouble(a_jsonObject.getJsonNumber("UTM_Y").toString()));
		t_paradaGuagua.setNombre(a_jsonObject.getString("NOMBRE"));
		t_paradaGuagua.setGrad_x(Double.parseDouble(a_jsonObject.getJsonNumber("GRAD_X").toString()));
		t_paradaGuagua.setGrad_y(Double.parseDouble(a_jsonObject.getJsonNumber("GRAD_Y").toString()));

		
		m_listaParadas.add(t_paradaGuagua);
		m_listaZona.add(t_paradaGuagua.getNombre());
	}
	@Override
	public List<String> getListaZona()
	{
		while(m_terminoLeer!=true)
		{
			System.out.print("");
		}
			
		return m_listaZona;
	}

	@Override
	public Geometry getLocalizacion(String a_barrio)
	{
		
		Geometry t_geometry = new Geometry();
		for (int i = 0; i < m_listaParadas.size(); i++)
		{
			ParadaGuagua t_parada=m_listaParadas.get(i);
			if(t_parada.getNombre()==a_barrio)
			{
				i=m_listaParadas.size();
				t_geometry = t_parada.getGeometry();
			}
		}
		return t_geometry;
	}

}
