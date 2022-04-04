package com.capgemini.service;

import java.util.List;

import com.capgemini.dao.IProductoDao;
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

	@Override
	public Producto save(Producto producto) {
		return productoDao.save(producto);
	}

}
