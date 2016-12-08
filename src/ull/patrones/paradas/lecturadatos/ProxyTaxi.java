package ull.patrones.paradas.lecturadatos;

import java.util.ArrayList;
import java.util.List;

import javax.json.JsonObject;

import ull.patrones.paradas.datos.Geometry;
import ull.patrones.paradas.iu.VentanaPrincipal;

public class ProxyTaxi extends LeerJSON
{
	private LeerJSONTaxis taxy;
	private List<Geometry> listGeo;
	private List<String> listZona;
	public ProxyTaxi()
	{
		taxy = new LeerJSONTaxis();
		listGeo = new ArrayList<>();
		listZona = new ArrayList<>();
		listZona.add("Cargado Lista");
	}
	@Override
	public void paradasConfig()
	{
		taxy.paradasConfig();
	}
	@Override
	public void paradaConcreta(JsonObject jsonValue)
	{
		taxy.paradaConcreta(jsonValue);
	}
	@Override
	public List<Geometry> getGeometrys(String a_barrio)
	{
		if(taxy.getTermino())
			listGeo = taxy.getGeometrys(a_barrio);
		return listGeo;
	}
	@Override
	public boolean getTermino()
	{
		return taxy.getTermino();
	}
	@Override
	public List<String> getListaZona()
	{
		do
		{
			VentanaPrincipal.getInstancia().obtenerBarrios(listZona);
		}while(!taxy.getTermino());
			
		VentanaPrincipal.getInstancia().obtenerBarrios(taxy.getListaZona());
		return taxy.getListaZona();
	}
	@Override
	public String getURL()
	{
		return taxy.getURL();
	}
}
