package ull.patrones.paradas.datos;

public class ParadaGuagua
{
	private Geometry geometry;
	private String geocodigo;
	private double utm_x;
	private double utm_y;
	private String parada;
	private String nombre;
	private double grad_x;
	private double grad_y;
	
	public Geometry getGeometry()
	{
		return geometry;
	}
	public void setGeometry(Geometry geometry)
	{
		this.geometry = geometry;
	}
	public String getGeocodigo()
	{
		return geocodigo;
	}
	public void setGeocodigo(String geocodigo)
	{
		this.geocodigo = geocodigo;
	}
	public double getUtm_x()
	{
		return utm_x;
	}
	public void setUtm_x(double d)
	{
		this.utm_x = d;
	}
	public double getUtm_y()
	{
		return utm_y;
	}
	public void setUtm_y(double utm_y)
	{
		this.utm_y = utm_y;
	}
	public String getParada()
	{
		return parada;
	}
	public void setParada(String parada)
	{
		this.parada = parada;
	}
	public String getNombre()
	{
		return nombre;
	}
	public void setNombre(String nombre)
	{
		this.nombre = nombre;
	}
	public double getGrad_x()
	{
		return grad_x;
	}
	public void setGrad_x(double grad_x)
	{
		this.grad_x = grad_x;
	}
	public double getGrad_y()
	{
		return grad_y;
	}
	public void setGrad_y(double grad_y)
	{
		this.grad_y = grad_y;
	}
	@Override
	public String toString()
	{
		String t_resul = "Geocodigo:"+getGeocodigo()+"\nutm:x:"+ utm_x+"\nutm_y:"+utm_y+"\nNombre:"+nombre+"\nParada"+parada+"\nGrad_x"+grad_x+"\nGrad_y"+grad_y+"\nGeometria{\n"+geometry+"\n.................................";
		return t_resul;
	}
}
