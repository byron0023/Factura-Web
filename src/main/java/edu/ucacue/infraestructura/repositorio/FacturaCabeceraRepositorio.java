package edu.ucacue.infraestructura.repositorio;

import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import edu.ucacue.modelo.FacturaCabecera;

public interface FacturaCabeceraRepositorio extends JpaRepository<FacturaCabecera, Integer>{

	//@Query("select f from FacturaCabecera f where f.fechaEmision : fechaEmision")
	//List<FacturaCabecera> findAllByFecha(Date fechaEmision);
}
