package edu.ucacue.infraestructura.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.ucacue.modelo.DetalleFactura;

public interface DetalleFacturaRepositorio extends JpaRepository<DetalleFactura, Integer>  {
	
}
