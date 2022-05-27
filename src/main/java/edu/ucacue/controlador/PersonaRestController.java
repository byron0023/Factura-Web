package edu.ucacue.controlador;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import java.util.stream.Collectors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import edu.ucacue.infraestructura.repositorio.PersonaRepositorio;
import edu.ucacue.modelo.Persona;

@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/api")

public class PersonaRestController {

	@Autowired
	private PersonaRepositorio personaRepositorio;

	// listar personas
	@GetMapping("/clientes")
	public List<Persona> index() {
		return personaRepositorio.findAll();
	}

	// listar personas con paginacion
	@GetMapping("/clientes/page/{page}")
	@ResponseStatus(HttpStatus.OK)
	public Page<Persona> filtrarPersonas(@PathVariable Integer page) {
		Pageable pageable = PageRequest.of(page, 8, Sort.by("nombre"));
		Page<Persona> personas = personaRepositorio.findAll(pageable);
		return personas;
	}

	// listar personas por id
	@GetMapping("/clientes/{id}")
	public Persona getById(@PathVariable int id) {

		Persona persona = personaRepositorio.findById(id).get();
		return persona;
	}

	// listar persona por nombre
	@GetMapping("/clientes/buscar/{nombre}")
	public List<Persona> getByNombre(@PathVariable(name = "nombre") String nombre) {
		List<Persona> personas = personaRepositorio.findAllByNombre(nombre);
		return personas;
	}

	// listar los primeros
	@GetMapping("/clientes/primeros")
	public List<Persona> primeros() {
		List<Persona> personasPrimeros = personaRepositorio.findAll();
		return personasPrimeros.subList(0, 1);
	}

	// guardar persona
	@PostMapping("/cliente")
	public ResponseEntity<?> saveCliente(@RequestBody Persona persona, BindingResult result) {
		Persona personaGrabar;
		Map<String, Object> response = new HashMap<>();

		if (result.hasErrors()) {
			List<String> errores = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("Los errores son", errores);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			personaGrabar = personaRepositorio.save(persona);
		} catch (DataAccessException e) {
			response.put("mensaje", "Error al realizar el inserción en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El Cliente se ha insertado con éxito en la BD");
		response.put("Cliente", personaGrabar);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}

	// actualizar persona por id
	@PutMapping("/cliente/{id}")
	public ResponseEntity<?> modificarCliente(@RequestBody Persona persona, BindingResult result,
			@PathVariable int id) {

		Map<String, Object> response = new HashMap<>();
		Persona cliente;
		try {
			cliente = personaRepositorio.getById(id);
		} catch (DataAccessException e) {

			response.put("mensaje",
					"Error: no se pudo editar, el cliente ID: ".concat(id + " no existe en la base de datos!"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		Persona clienteModificado = new Persona();

		if (result.hasErrors()) {
			List<String> errores = result.getFieldErrors().stream()
					.map(err -> "El campo '" + err.getField() + "' " + err.getDefaultMessage())
					.collect(Collectors.toList());
			response.put("Los errores son", errores);
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
		}

		try {
			cliente.setApellido(persona.getApellido());
			cliente.setCedula(persona.getCedula());
			cliente.setNombre(persona.getNombre());
			cliente.setTelefono(persona.getTelefono());
			cliente.setNumeroHijos(persona.getNumeroHijos());

			clienteModificado = personaRepositorio.save(cliente);

		} catch (DataAccessException e) {
			response.put("mensaje", "Error al actualizar el cliente en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			response.put("mensaje", "Error al actualizar el cliente en la base de datos");
			response.put("error", e.getMessage().concat(": ").concat(e.getMessage()));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		response.put("mensaje", "El Cliente se ha modificado con éxito en la BD");
		response.put("Cliente", clienteModificado);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

	// eliminar persona por id
	@DeleteMapping("/cliente/{id}")
	public ResponseEntity<?> eliminarCliente(@PathVariable int id) {

		Map<String, Object> response = new HashMap<>();
		try {
			personaRepositorio.deleteById(id);
		} catch (DataAccessException e) {

			response.put("mensaje",
					"Error: no se pudo eliminar, el cliente ID: ".concat(id + " no existe en la base de datos!"));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		response.put("mensaje", "El Cliente se ha eliminado con éxito en la BD");

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}

}
