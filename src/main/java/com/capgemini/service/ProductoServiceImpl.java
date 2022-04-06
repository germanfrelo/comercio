package com.capgemini.service;

import java.util.List;

import com.capgemini.dao.IPresentacionDao;
import com.capgemini.dao.IProductoDao;
import com.capgemini.entities.Presentacion;
import com.capgemini.entities.Producto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductoServiceImpl implements IProductoService {

	// Spring buscará algo del tipo IProductoDao
	@Autowired
	private IProductoDao productoDao;

	// Spring buscará algo del tipo IPresentacionDao
	@Autowired
	private IPresentacionDao presentacionDao;

	@Override
	public List<Producto> findAll(Sort sort) {
		return productoDao.findAll(sort);
	}

	@Override
	public Page<Producto> findAll(Pageable pageable) {
		return productoDao.findAll(pageable);
	}

	@Override
	public Producto findById(long id) {
		return productoDao.findById(id);
	}

	@Override
	public void delete(long id) {
		productoDao.deleteById(id);
	}

	// Método para persistir el producto en la BD
	@Override
	public Producto save(Producto producto) {
		// Antes de persistir el producto en la BD, hay que establecerle la presentación
		// Presentación buscada por el ID de la presentación del producto que se
		// recibe
		Presentacion presentacion = null;

		presentacion = presentacionDao.findById(producto.getPresentacion().getId()).orElse(null);

		producto.setPresentacion(presentacion);

		return productoDao.save(producto);
	}

}
