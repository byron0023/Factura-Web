package edu.ucacue.controlador;
//ultimo commit

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import edu.ucacue.infraestructura.repositorio.DetalleFacturaRepositorio;
import edu.ucacue.infraestructura.repositorio.FacturaCabeceraRepositorio;
import edu.ucacue.modelo.DetalleFactura;
import edu.ucacue.modelo.FacturaCabecera;

@CrossOrigin(origins = {"http://localhost:4200"})
@RestController
@RequestMapping("/api")

public class FacturaRestController {

	@Autowired
	private DetalleFacturaRepositorio dFR;
	
	//listar detalles por id
	@GetMapping("/detallefacturas/{idCabeceraFactura}")
	public List<DetalleFactura> index(@PathVariable int idCabeceraFactura){
		return dFR.findAllByIdCabeceraFactura(idCabeceraFactura);
	}
	
	@Autowired
	private FacturaCabeceraRepositorio fCR;
	
	//listar facturas
	@GetMapping("/facturas")
	public List<FacturaCabecera> index3(){
		return fCR.findAll();
	}
	
	//listar facturas con paginacion
	@GetMapping("/facturas/page/{page}")
	@ResponseStatus(HttpStatus.OK)
	public Page<FacturaCabecera> filtrarFacturas(@PathVariable Integer page){
		Pageable pageable = PageRequest.of(page, 8, Sort.by("fechaEmision"));
		Page<FacturaCabecera> facturas = fCR.findAll(pageable);
		return facturas;
	}
	
	@PostMapping("/savefactura")
	public ResponseEntity<?> saveFactura1(@RequestBody FacturaCabecera fC, BindingResult result) {
		FacturaCabecera facturaGrabar;
		Map<String, Object> response = new HashMap<>();
		
		for(DetalleFactura dF: fC.getDetallesFacturas())
		{
			dF.setIdFactura(null);
			dF.setFacturaCabecera(fC);
		}

		if (result.hasErrors()) {
			List<String> errores = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("Los errores son", errores);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			fC.setId(null);
			facturaGrabar = fCR.save(fC);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar la inserción en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "La Factura se ha insertado con éxito en la BD");
		response.put("Cliente", facturaGrabar);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	//guardar detalle factura
//	@PostMapping("/savefactura")
//	public ResponseEntity<?> saveFactura(@RequestBody DetalleFactura dF, BindingResult result) {
//		DetalleFactura facturaGrabar;
//		Map<String, Object> response = new HashMap<>();
//
//		if (result.hasErrors()) {
//			List<String> errores = result.getFieldErrors().stream()
//					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
//					.collect(Collectors.toList());
//			response.put("Los errores son", errores);
//			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
//		}
//
//		try {
//			facturaGrabar = dFR.save(dF);
//		} catch (DataAccessException e) {
//			response.put("mensaje", "Error al realizar la inserción en la base de datos");
//			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
//			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//
//		response.put("mensaje", "La Factura se ha insertado con éxito en la BD");
//		response.put("Cliente", facturaGrabar);
//
//		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
//	}
	
	//guardar factura cabecera
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
