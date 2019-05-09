package com.libre.taxi;

public class Fotos {
	
	public String getConductor() {
	return conductor;
	}
	public void setConductor(String conductor) {
	this.conductor = conductor;
	}
	public String getTelefono() {
	return telefono;
	}
	public void setTelefono(String telefono) {
	this.telefono = telefono;
	}
	public String getFecha() {
	return fecha;
	}
	public void setFecha(String fecha) {
	this.fecha = fecha;
	}
	
	public int getDisponible() {
	return disponible;
	}
	public void setDisponible(int disponible) {
	this.disponible = disponible;
	}
	
	public String getDistancia() {
	return distancia;
	}
	public void setDistancia(String distancia) {
	this.distancia = distancia;
	}
	
	public int getImageNumber() {
	return imageNumber;
	}
	public void setImageNumber(int imageNumber) {
	this.imageNumber = imageNumber;
	}
	 
	private String conductor ;
	private String telefono;
	private String fecha;
	private String distancia;
	private int imageNumber;
	private int disponible;

}
