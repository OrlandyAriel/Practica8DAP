package ull.patrones.paradas.mapa;

import java.util.List;

import ull.patrones.paradas.datos.Geometry;

public interface IMapa
{
	/**
	 * * M�todo que configura el javascript y el mapa
	 * @param a_geo, recibe la lista de coordenadas a pintar en el mapa
	 * @return,devuelve un string con el c�digo javascript y html del mapa
	 */
	public String getMapa(List<Geometry> a_geo);
}
