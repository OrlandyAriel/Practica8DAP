package ull.patrones.paradas.mapa;

import java.util.List;

import ull.patrones.paradas.datos.Geometry;

public class MapaProxy implements IMapa
{
	private Mapa mapa;
	public MapaProxy()
	{
		mapa = new Mapa();
	}
	@Override
	public String getMapa(List<Geometry> a_geo)
	{
		String html = "";
		if (a_geo.isEmpty())
		{
			html="<!DOCTYPE html> \n"+
					"<html> \n"+
					"<head> \n"+
					" <style type=\"text/css\">\n"+
					" html,\n"+
					" body {\n"+
					" height: 100%;\n"+
					" margin: 0;\n"+
					" padding: 0;\n"+
					" }\n"+
					" \n"+
					" #map {\n"+
					" height: 100%;\n"+
					" } \n"+
					" </style> </head> \n"+
					"<body> \n"+
					"<div align=center><h1>Seleccionar una zona/barrio</h1> </div>\n"+
					" </body>\n"+
					"\n"+
					"</html>";
		}
		else
			html = mapa.getMapa(a_geo);
		return html;
	}
}