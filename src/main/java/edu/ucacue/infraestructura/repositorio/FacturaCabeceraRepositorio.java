package edu.ucacue.infraestructura.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.ucacue.modelo.FacturaCabecera;

public interface FacturaCabeceraRepositorio extends JpaRepository<FacturaCabecera, Integer>{

	//@Query("select f from FacturaCabecera f where f.fechaEmision : fechaEmision")
	//List<FacturaCabecera> findAllByFecha(Date fechaEmision);
}
