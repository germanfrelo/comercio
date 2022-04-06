package com.capgemini.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

// @Entity -> Para mapear esta clase con una base de datos
@Entity
// Para que esta clase pueda usarse como una interfaz -> "implements
// Serializable"
public class Producto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id // @Id -> Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY -> Not NULL y autoincremental
	private long id;

	@NotEmpty(message = "El campo nombre no puede estar vacio")
	@Size(min = 4, max = 255, message = "El campo nombre tiene que tener entre 4 y 255 caracteres")
	private String nombre;

	@Size(max = 255, message = "El campo description no puede tener mas de 255 caracteres")
	private String descripcion;

	@Min(value = 0, message = "El precio no puede ser negativo")
	private double precio;

	@Min(value = 0, message = "El stock no puede ser negativo")
	private long stock;

	// Producto es tabla hija de Presentacion
	// Relaciona Producto (many) con Presentacion (one)
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	// Fetch LAZY -> no traiga las consultas inmediatamente al generar el Producto
	// Cascade PERSIST -> Persistir el cambio en la BD
	@NotNull(message = "El producto tiene que tener una presentación")
	private Presentacion presentacion;

	public Producto() {
	}

	public Producto(long id, String nombre, String descripcion, double precio, long stock) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.stock = stock;
	}

	public Producto(long id, String nombre, String descripcion, double precio, long stock, Presentacion presentacion) {
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.precio = precio;
		this.stock = stock;
		this.presentacion = presentacion;
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

	public Presentacion getPresentacion() {
		return presentacion;
	}

	public void setPresentacion(Presentacion presentacion) {
		this.presentacion = presentacion;
	}

}
