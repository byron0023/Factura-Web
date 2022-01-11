package edu.ucacue.infraestructura.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import edu.ucacue.modelo.Producto;

public interface ProductoRepositorio extends JpaRepository<Producto, Integer>{

	@Query("select p from Producto p where p.nombre like %:nombre%")
	Producto findByNombreLike(String nombre);
}
