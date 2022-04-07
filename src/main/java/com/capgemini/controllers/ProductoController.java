package com.capgemini.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import com.capgemini.entities.Producto;
import com.capgemini.service.IProductoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
			// @RequestParam --> "/productos?page=__&size=__"
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

	// Recupera un producto por su ID ("/productos/id")
	@GetMapping(value = "/{id}")
	public ResponseEntity<Producto> getProductoPorId(@PathVariable long id) {

		ResponseEntity<Producto> responseEntity = null;
		Producto producto = productoService.findById(id);

		if (producto != null) {
			responseEntity = new ResponseEntity<>(producto, HttpStatus.OK);
		} else {
			responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return responseEntity;

	}

	// Persiste (guarda o inserta) un producto en la tabla producto
	@PostMapping
	// El producto viene en el cuerpo de la petición --> @RequestBody
	// @Valid + BindingResult: para validar producto
	public ResponseEntity<Map<String, Object>> insertar(@Valid @RequestBody Producto producto, BindingResult result) {

		Map<String, Object> responseAsMap = new HashMap<>();

		// Comprueba si producto tiene errores basándose en anotaciones atributos
		// Producto
		List<String> errores = null;

		ResponseEntity<Map<String, Object>> responseEntity = null;

		if (result.hasErrors()) {
			errores = new ArrayList<>();

			for (ObjectError error : result.getAllErrors()) {
				errores.add(error.getDefaultMessage());
			}

			responseAsMap.put("errores", errores);
			responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.BAD_REQUEST);
		} else {
			try {
				Producto productoDB = productoService.save(producto);

				if (productoDB != null) {
					responseAsMap.put("mensaje", "El producto con ID " + productoDB.getId() + " se ha guardado.");
					responseAsMap.put("producto", productoDB);
					responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.CREATED);
				} else {
					responseAsMap.put("error", "No se ha podido crear el producto");
					responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} catch (DataAccessException e) {
				responseAsMap.put("error crítico", e.getMostSpecificCause());
				responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return responseEntity;

	}

	// Actualiza un producto
	@PutMapping(value = "/{id}")
	public ResponseEntity<Map<String, Object>> actualizar(@Valid @RequestBody Producto producto, BindingResult result,
			@PathVariable int id) {

		Map<String, Object> responseAsMap = new HashMap<>();
		List<String> errores = null;
		ResponseEntity<Map<String, Object>> responseEntity = null;

		if (result.hasErrors()) {
			errores = new ArrayList<>();

			for (ObjectError error : result.getAllErrors()) {
				errores.add(error.getDefaultMessage());
			}

			responseAsMap.put("errores", errores);
			responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.BAD_REQUEST);
		} else {
			try {
				// Antes de persistir el producto en la BD, establecerle el ID
				producto.setId(id);

				Producto productoDB = productoService.save(producto);

				if (productoDB != null) {
					responseAsMap.put("mensaje",
							"El producto con ID " + productoDB.getId() + " se ha actualizado con éxito.");
					responseAsMap.put("producto", productoDB);
					responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.OK);
				} else {
					responseAsMap.put("error", "No se ha podido actualizar el producto.");
					responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
				}
			} catch (DataAccessException e) {
				responseAsMap.put("error crítico", e.getMostSpecificCause());
				responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}

		return responseEntity;

	}

	// Elimina un producto
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Map<String, Object>> eliminar(@PathVariable int id) {

		Map<String, Object> responseAsMap = new HashMap<>();
		ResponseEntity<Map<String, Object>> responseEntity = null;

		try {
			Producto productoBuscado = productoService.findById(id);

			if (productoBuscado != null) {
				productoService.delete(id);
				responseAsMap.put("mensaje",
						"Se ha eliminado el producto con ID " + productoBuscado.getId() + ".");
				responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.NO_CONTENT);
			} else {
				responseAsMap.put("error", "No se ha podido eliminar el producto con ID " + id + " porque no existe.");
				responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.NOT_FOUND);
			}
		} catch (DataAccessException e) {
			responseAsMap.put("error crítico", e.getMostSpecificCause());
			responseEntity = new ResponseEntity<>(responseAsMap, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return responseEntity;

	}
}
