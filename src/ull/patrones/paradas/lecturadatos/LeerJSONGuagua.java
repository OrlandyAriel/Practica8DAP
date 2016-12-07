package ull.patrones.paradas.lecturadatos;

import java.util.ArrayList;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

public class LeerJSONGuagua extends LeerJSON
{
	private final static String JSON_URL_GUAGUA= "http://www.santacruzdetenerife.es/opendata/dataset/07e107d2-209a-4bfa-862e-00fcf497ae1d/resource/5ba90d75-1fd4-4304-b41c-7c2537891fa1/download/busturisticoparadas.json";
	
	private List<ParadaGuagua> m_listaParadas;
	
	public LeerJSONGuagua()
	{
		super(JSON_URL_GUAGUA);
		m_listaParadas = new ArrayList<>();
		leerDatos();
	}

		
	public void paradaConcreta(JsonObject a_jsonObject)
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
		addZona(t_paradaGuagua.getNombre());
	}
	@Override
	public List<Geometry> getGeometrys(String a_barrio)
	{
		
			ArrayList<Geometry> t_list = new ArrayList<>();
			
			for (int i = 0; i < m_listaParadas.size(); i++)
			{
				ParadaGuagua t_parada = m_listaParadas.get(i);
				if(t_parada.getNombre().equals(a_barrio))
				{
					t_list.add(t_parada.getGeometry());
				}
			}
			return t_list;
		
	}

}
