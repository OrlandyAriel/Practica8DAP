package ull.patrones.paradas.mapa;

import java.util.List;

import ull.patrones.paradas.datos.Geometry;
/**
 * 
 * @author Orlandy Ariel Sánchez A.
 *
 */
public class Mapa
{
	/**
	 * * Método que configura el javascript y el mapa
	 * @param a_geo, recibe la lista de coordenadas a pintar en el mapa
	 * @return,devuelve un string con el código javascript y html del mapa
	 */
	public String getMapa(List<Geometry> a_geo)
	{
		String html = "";
		if(a_geo.isEmpty())
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
		}else
		{
			String paradas = "var paradas=new Array();";
			for (Geometry geometry : a_geo)
			{
				String parada="{\r\n lat:"+geometry.getM_longitud()+",\r\n lng:"+geometry.getM_latitud()+"\r\n }";
				paradas+="paradas.push("+parada+");";
			}
			String forParadas="for(i =0; i<paradas.length;i++)\n\t\t\t{\n\t\t\t\tmarker = new google.maps.Marker({ \n position: paradas[i],\n map: map,\n title: \'Parada de taxi\' \n }); \n\t\t\t}";
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
					" <div id= \"map\"></div> \n"+
					" <script type= \"text/javascript \">\n"+
					" var map; \n"+
					" function initMap() { \n"+
					"\t\t\t"+paradas+"\n"+
					" var map = new google.maps.Map(document.getElementById(\'map\'), { \n"+
					" zoom: 15,\n"+
					" center: paradas[0] \n"+
					" }); \n"+
					" \n"+
					"\t\t\t"+forParadas+"\n"+
					"\t\t}\n"+
					" marker.setMap(map); \n"+
					" </script> \n"+
					" <script async defer src= \"https://maps.googleapis.com/maps/api/js?key=AIzaSyBKLG4--vt2LLBG4Kz8K0qfCynzrBcNwdc&callback=initMap \"> \n"+
					" </script> </body> \n"+
					"\n"+
					"</html>";
		}
		return html;
	}
}
