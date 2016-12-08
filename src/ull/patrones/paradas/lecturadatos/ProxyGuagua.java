package ull.patrones.paradas.lecturadatos;

import java.util.ArrayList;
import java.util.List;

import javax.json.JsonObject;

import ull.patrones.paradas.datos.Geometry;
import ull.patrones.paradas.iu.VentanaPrincipal;

public class ProxyGuagua extends LeerJSON
{
	private LeerJSONGuagua guagua;
	private List<Geometry> listGeo;
	private List<String> listZona;
	public ProxyGuagua()
	{
		guagua = new LeerJSONGuagua();
		listGeo = new ArrayList<>();
		listZona = new ArrayList<>();
		listZona.add("Cargado Lista");
	}
	@Override
	public void paradasConfig()
	{
		guagua.paradasConfig();
	}
	@Override
	public void paradaConcreta(JsonObject jsonValue)
	{
		guagua.paradaConcreta(jsonValue);
	}
	@Override
	public List<Geometry> getGeometrys(String a_barrio)
	{
		if(guagua.getTermino())
			listGeo = guagua.getGeometrys(a_barrio);
		return listGeo;
	}
	@Override
	public boolean getTermino()
	{
		return guagua.getTermino();
	}
	@Override
	public List<String> getListaZona()
	{
		do
		{
			VentanaPrincipal.getInstancia().obtenerBarrios(listZona);
		}while(!guagua.getTermino());
			
		VentanaPrincipal.getInstancia().obtenerBarrios(guagua.getListaZona());
		return guagua.getListaZona();
	}
	@Override
	public String getURL()
	{
		return guagua.getURL();
	}
}
