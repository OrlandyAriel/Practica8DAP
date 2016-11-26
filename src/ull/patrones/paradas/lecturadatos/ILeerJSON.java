package ull.patrones.paradas.lecturadatos;

import java.util.List;

public interface ILeerJSON
{
	public List<String> getListaZona();
	public Geometry getLocalizacion(String a_barrio);
}
