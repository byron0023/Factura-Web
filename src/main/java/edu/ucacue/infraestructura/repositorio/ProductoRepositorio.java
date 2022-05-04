package edu.ucacue.infraestructura.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import edu.ucacue.modelo.Producto;

public interface ProductoRepositorio extends JpaRepository<Producto, Integer>{

	@Query("select pd from Producto pd where pd.nombre like %:nombre%")
	List<Producto> findAllByNombre(String nombre);
}
