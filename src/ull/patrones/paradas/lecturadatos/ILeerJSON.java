package ull.patrones.paradas.lecturadatos;

import java.util.List;

public interface ILeerJSON
{
	public List<String> getListaBarrios();
	public Geometry getLocalizacion(String a_barrio);
}
