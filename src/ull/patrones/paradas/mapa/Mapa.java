package ull.patrones.paradas.mapa;

public class Mapa
{
	private Double m_lng;
	private Double m_lat;

	public Mapa()
	{
		m_lng = 0D;
		m_lat = 0D;
	}
	/**
	 * Método que configura el javascript y el mapa
	 *  @return,devuelve un string con el código javascript y html del mapa
	 */
	public String getMapa( Double lng,Double lat)
	{
		m_lng =lng;
		m_lat=lat;
		return "<!DOCTYPE html>\r\n<html>\r\n  <head>\r\n    <style type=\"text/css\">\r\n      html, body { height: 100%; margin: 0; padding: 0; }\r\n      #map { height: 100%; }\r\n    </style>\r\n  </head>\r\n  <body>\r\n    <div id=\"map\"></div>\r\n    <script type=\"text/javascript\">\r\nvar map;\r\nfunction initMap() {\r\n \r\n  var parada = {lat: "+m_lat+", lng: "+m_lng+"};\r\n  var map = new google.maps.Map(document.getElementById('map'), {\r\n    zoom: 15,\r\n    center: parada\r\n  });\r\n\r\n  var marker = new google.maps.Marker({\r\n    position: parada,\r\n    map: map,\r\n    title: 'Parada de taxi'\r\n  });\r\n}\r\nmarker.setMap(map);\r\n    </script>\r\n    <script async defer\r\n      src=\"https://maps.googleapis.com/maps/api/js?key=AIzaSyBKLG4--vt2LLBG4Kz8K0qfCynzrBcNwdc&callback=initMap\">\r\n    </script>\r\n  </body>\r\n</html>";
	}
}
