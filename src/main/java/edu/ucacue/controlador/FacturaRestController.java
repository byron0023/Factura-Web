package edu.ucacue.controlador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.ucacue.infraestructura.repositorio.DetalleFacturaRepositorio;
import edu.ucacue.infraestructura.repositorio.FacturaCabeceraRepositorio;
import edu.ucacue.modelo.DetalleFactura;
import edu.ucacue.modelo.FacturaCabecera;

@RestController
@RequestMapping("/api")

public class FacturaRestController {

	@Autowired
	private DetalleFacturaRepositorio dFR;
	
	@GetMapping("/factura")
	public List<DetalleFactura> index(){
		return dFR.findAll();
	}
	
	@GetMapping("/factura/ventaDiaria")
	public List<DetalleFactura> index1(){
		return dFR.findAll();
	}
	
	@GetMapping("productos/inventario")
	public List<DetalleFactura> index2(){
		return dFR.findAll();
	}
	
	/*@Autowired
	private FacturaCabeceraRepositorio fCR;
	
	@GetMapping("/facturas")
	public List<FacturaCabecera> index1(){
		return fCR.findAll();
	}*/
	
	@PostMapping("/savefactura")
	public ResponseEntity<?> saveFactura(@RequestBody DetalleFactura dF, BindingResult result) {
		DetalleFactura facturaGrabar;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errores = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("Los errores son", errores);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			facturaGrabar = dFR.save(dF);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la inserción en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La Factura se ha insertado con éxito en la BD");
		response.put("Cliente", facturaGrabar);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	/*@PostMapping("/savefacturas")
	public ResponseEntity<?> saveFactura1(@RequestBody FacturaCabecera fC, BindingResult result) {
		FacturaCabecera facturaGrabar;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errores = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("Los errores son", errores);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			facturaGrabar = fCR.save(fC);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la inserción en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La Factura se ha insertado con éxito en la BD");
		response.put("Cliente", facturaGrabar);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}*/
	
}
