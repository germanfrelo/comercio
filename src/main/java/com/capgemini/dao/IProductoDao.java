package com.capgemini.dao;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.capgemini.entities.Producto;

@Repository
public interface IProductoDao extends JpaRepository<Producto, Long> {
	// Crearemos unas consultas personalizadas para cuando se busque un
	// producto venga la presentacion junto con dicho producto
	// y tambien para recuperar no todos los productos, sino paginados,
	// de 10 en 10, de 20 en 20, etc
	// RECORDEMOS QUE: Cuando hemos creado la relacion hemos especificado fetch
	// LAZY,
	// para que no se traiga la presentacion siempre que se busque un producto
	// porque serian dos consultas, o una consulta con una subconsulta, que es menos
	// eficiente que crear una consulta personalizada posteriormente,
	// para cuando queremos traer presentacion asociada al producto

	// Recupera un listado de productos ordenado
	@Query(value = "select p from Producto p left join fetch p.presentacion")
	public List<Producto> findAll(Sort sort);

	// Recupera un listado de productos que se puede paginar (Pageable)
	// Pageable ya incluye ordenamiento
	@Query(value = "select p from Producto p left join fetch p.presentacion", countQuery = "select count(p) from Producto p left join p.presentacion")
	public Page<Producto> findAll(Pageable pageable);

	// La sintaxis siguiente, utilizada para pasar un parametro se llama Named
	// Parameters,
	// que es tambien, en esencia, una consulta preparada, pero no utilizando el ?
	@Query(value = "select p from Producto p left join fetch p.presentacion where p.id = :id")
	public Producto findById(long id);
}
