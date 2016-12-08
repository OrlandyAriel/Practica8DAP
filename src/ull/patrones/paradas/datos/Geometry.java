package ull.patrones.paradas.datos;

import java.util.List;

public class Geometry
{
	private String type;
	private List<Double> coordinates;
	private Double m_latitud;

	public Double getM_latitud()
	{
		return m_latitud;
	}

	public Double getM_longitud()
	{
		return m_longitud;
	}

	private Double m_longitud;

	public String getType()
	{
		return this.type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public List<Double> getCoordinates()
	{
		return this.coordinates;
	}

	public void setCoordinates(List<Double> coordinates)
	{
		this.coordinates = coordinates;
		m_latitud = this.coordinates.get(0);
		m_longitud = this.coordinates.get(1);
	}

	@Override
	public String toString()
	{
		return "\ttype:" + type + "\n\tCoordinadas:[" + m_longitud + " " + m_latitud + "]\n}";
	}

}
