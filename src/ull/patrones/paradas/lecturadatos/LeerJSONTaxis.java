package ull.patrones.paradas.lecturadatos;

import java.util.ArrayList;
import java.util.List;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

public class LeerJSONTaxis extends LeerJSON
{
	private static String JSON_URL_TAXI = "http://www.santacruzdetenerife.es/opendata/dataset/b4961b60-5310-494c-a2e5-d9a8f3d9aad3/resource/a09a1850-a9b0-45e9-b9b3-24cab6b65d31/download/paradastaxi.json";
	private List<ParadaTaxi> m_listaParadas;
	public LeerJSONTaxis()
	{
		super(JSON_URL_TAXI);
		m_listaParadas = new ArrayList<>();
		leerDatos();
	}

	public void paradaConcreta(JsonObject a_jsonObject)
	{
		ParadaTaxi t_paradaTaxi = new ParadaTaxi();
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
		t_paradaTaxi.setGeometry(t_geometry);
		t_paradaTaxi.setgeocodigo(a_jsonObject.getString("GEOCODIGO"));
		t_paradaTaxi.setlong_sig(a_jsonObject.getInt("LONG_SIG"));
		t_paradaTaxi.setBarrio(a_jsonObject.getString("Barrio"));
		t_paradaTaxi.setdireccion(a_jsonObject.getString("DIRECCION"));
		m_listaParadas.add(t_paradaTaxi);
		addZona(t_paradaTaxi.getBarrio());
	}

	@Override
	public Geometry getLocalizacion(String a_barrio)
	{
		Geometry t_geometry=new Geometry();
		for (int i = 0; i < m_listaParadas.size(); i++)
		{
			ParadaTaxi t_parada=m_listaParadas.get(i);
			if(t_parada.getBarrio()==a_barrio)
			{
				i=m_listaParadas.size();
				t_geometry = t_parada.getGeometry();
			}
		}
		return t_geometry;
	}
}
