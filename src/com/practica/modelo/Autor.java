package com.practica.modelo;

import java.util.Objects;

public class Autor {
	private String idAutor;
    private String nombre;
    private String nacionalidad;
    private int anioNacimiento;


	public Autor(String id) {
		super();
		this.idAutor = id;
	}

	public Autor(String id, String nombre, String nacionalidad, int anioNacimiento) {
		super();
		this.idAutor = id;
		this.nombre = nombre;
		this.nacionalidad = nacionalidad;
		this.anioNacimiento = anioNacimiento;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	public int getAnioNacimiento() {
		return anioNacimiento;
	}

	public void setAnioNacimiento(int anioNacimiento) {
		this.anioNacimiento = anioNacimiento;
	}

	public String getId() {
		return idAutor;
	}

	public void setId(String id) {
		this.idAutor = id;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idAutor);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Autor other = (Autor) obj;
		return Objects.equals(idAutor, other.idAutor);
	}

	@Override
	public String toString() {
		return "Autor [id=" + idAutor + ", nombre=" + nombre + ", nacionalidad=" + nacionalidad + ", anioNacimiento="
				+ anioNacimiento + "]";
	}

	
    
}


