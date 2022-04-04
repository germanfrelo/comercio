package com.capgemini.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

// @Entity -> Para mapear esta clase con una base de datos
@Entity
// Para que esta clase pueda usarse como una interfaz -> "implements
// Serializable"
public class Producto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id // @Id -> Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY -> Not NULL y autoincremental
	private long id;
	private String nombre;
	private String descripcion;
	private double precio;
	private long stock;

	public Producto() {
		super();
	}

	public Producto(long id, String nombre, String descripcion, double precio, long stock) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.stock = stock;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public long getStock() {
		return stock;
	}

	public void setStock(long stock) {
		this.stock = stock;
	}

}
