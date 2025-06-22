package com.practica.modelo;

// Prestamo.java
import java.util.Date;
import java.util.Objects;

public class Prestamo {
	private String idPrestamo;	
    private Libro libro;
    private String nombreUsuario;
    private Date fechaPrestamo;
    private Date fechaDevolucion;
    
    
	public Prestamo(String idPrestamo) {
		super();
		this.idPrestamo = idPrestamo;
	}


	public Prestamo(String idPrestamo, Libro libro, String nombreUsuario, Date fechaPrestamo, Date fechaDevolucion) {
		super();
		this.idPrestamo = idPrestamo;
		this.libro = libro;
		this.nombreUsuario = nombreUsuario;
		this.fechaPrestamo = fechaPrestamo;
		this.fechaDevolucion = fechaDevolucion;
	}


	public String getIdPrestamo() {
		return idPrestamo;
	}


	public void setIdPrestamo(String idPrestamo) {
		this.idPrestamo = idPrestamo;
	}


	public Libro getLibro() {
		return libro;
	}


	public void setLibro(Libro libro) {
		this.libro = libro;
	}


	public String getNombreUsuario() {
		return nombreUsuario;
	}


	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}


	public Date getFechaPrestamo() {
		return fechaPrestamo;
	}


	public void setFechaPrestamo(Date fechaPrestamo) {
		this.fechaPrestamo = fechaPrestamo;
	}


	public Date getFechaDevolucion() {
		return fechaDevolucion;
	}


	public void setFechaDevolucion(Date fechaDevolucion) {
		this.fechaDevolucion = fechaDevolucion;
	}


	@Override
	public int hashCode() {
		return Objects.hash(idPrestamo);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Prestamo other = (Prestamo) obj;
		return Objects.equals(idPrestamo, other.idPrestamo);
	}


	@Override
	public String toString() {
		return "Prestamo [idPrestamo=" + idPrestamo + ", libro=" + libro + ", nombreUsuario=" + nombreUsuario
				+ ", fechaPrestamo=" + fechaPrestamo + ", fechaDevolucion=" + fechaDevolucion + "]";
	}



}
