package ull.patrones.paradas.datos;

public class ParadaTaxi
{
	private Geometry geometry;
	private String geocodigo;
	private int long_sig;
	private String barrio;
	private String direccion;
	public ParadaTaxi()
	{
	}
	public Geometry getGeometry()
	{
		return geometry;
	}
	public void setGeometry(Geometry geometry)
	{
		this.geometry = geometry;
	}
	public String getgeocodigo()
	{
		return geocodigo;
	}
	public void setgeocodigo(String geocodigo)
	{
		this.geocodigo = geocodigo;
	}
	public int getlong_sig()
	{
		return long_sig;
	}
	public void setlong_sig(int long_sig)
	{
		this.long_sig = long_sig;
	}
	public String getBarrio()
	{
		return barrio;
	}
	public void setBarrio(String barrio)
	{
		this.barrio = barrio;
	}
	public String getdireccion()
	{
		return direccion;
	}
	public void setdireccion(String direccion)
	{
		this.direccion = direccion;
	}
	@Override
	public boolean equals(Object obj)
	{
		String barrio = (String) obj;
		return this.barrio.equals(barrio);
	}
	@Override
	public String toString()
	{
		String t_resul = "Geocodigo:"+getgeocodigo()+"\nlong_sing:"+ long_sig+"\nBarrio:"+barrio+"\ndireccion:"+direccion+"\nGeometria{\n"+geometry+"\n.................................";
		return t_resul;
	}
}
