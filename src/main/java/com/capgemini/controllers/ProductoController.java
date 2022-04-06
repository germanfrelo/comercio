package com.capgemini.controllers;

import java.util.List;

import com.capgemini.entities.Producto;
import com.capgemini.service.IProductoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// @RestController para que devuelva JSON
@RestController
@RequestMapping(value = "/productos")
public class ProductoController {
	// Controller pide a Service los productos
	@Autowired // Inyecta una depedencia del objeto instanciado para no instanciarla con "new"
	private IProductoService productoService;

	// Devuelve productos (paginados o no) + código de estado HTTP de la petición
	// Ejemplo sin paginar: https://localhost.8888/productos
	// Ejemplo paginados: https://localhost.8888/productos?page=1&size=5
	@GetMapping // Peticiones GET llegan aquí
	public ResponseEntity<List<Producto>> getProductos(
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer size) {

		ResponseEntity<List<Producto>> responseEntity = null;
		List<Producto> productos = null;

		Sort sort = Sort.by("nombre");

		if (page != null && size != null) {
			// Paginados (page y size)
			Pageable pageable = PageRequest.of(page, size, sort);
			productos = productoService.findAll(pageable).getContent();
		} else {
			// Sin paginar
			productos = productoService.findAll(sort);
		}

		if (productos != null) {
			responseEntity = new ResponseEntity<>(productos, HttpStatus.OK);
		} else {
			responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return responseEntity;
	}

}
