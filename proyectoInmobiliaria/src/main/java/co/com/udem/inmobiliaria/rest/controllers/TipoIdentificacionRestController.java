package co.com.udem.inmobiliaria.rest.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.udem.inmobiliaria.dto.TipoIdentificacionDTO;
import co.com.udem.inmobiliaria.entities.TipoIdentificacion;
import co.com.udem.inmobiliaria.repositories.ITipoIdentificacion;
import co.com.udem.inmobiliaria.util.Constantes;
import co.com.udem.inmobiliaria.util.ConvertTipoIdentificacion;

@RestController
@RequestMapping("/tiposIden")
public class TipoIdentificacionRestController {

	@Autowired
	private ITipoIdentificacion tipoIdentificacionRepo;

	@Autowired
	private ConvertTipoIdentificacion convertTipoIdentificacion;

	@PostMapping("/crearTipoIden")
	public Map<String, String> crearTipoIdentificacion(@RequestBody TipoIdentificacionDTO tipoIdentificacionDTO) {
		Map<String, String> response = new HashMap<>();

		try {
			tipoIdentificacionRepo.save(convertTipoIdentificacion.convertToEntity(tipoIdentificacionDTO));
			response.put(Constantes.CODIGO_HTTP, "200");
			response.put(Constantes.MENSAJE_EXITO, "Registrado insertado exitosamente");
			return response;
		} catch (Exception e) {
			response.put(Constantes.CODIGO_HTTP, "500");
			response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al insertar");
			return response;

		}

	}

	@DeleteMapping("/borrarTipoIden/{id}")
	public Map<String, String> borrarTipoIdentificacion(@PathVariable Long id) {
		Map<String, String> response = new HashMap<>();

		try {
			tipoIdentificacionRepo.deleteById(id);
			response.put(Constantes.CODIGO_HTTP, "200");
			response.put(Constantes.MENSAJE_EXITO, "Registrado eliminado exitosamente");
			return response;
		} catch (Exception e) {
			response.put(Constantes.CODIGO_HTTP, "500");
			response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al eliminar");
			return response;

		}

	}

	@PutMapping("/actualizarTipoIden/{id}")
	public Map<String, String> actualizarTipoIdentificacion(@RequestBody TipoIdentificacionDTO tipoIdentificacionDTO,
			@PathVariable Long id) {
		Map<String, String> response = new HashMap<>();

		try {

			Optional<TipoIdentificacion> tipoIden = tipoIdentificacionRepo.findById(id);
			if (tipoIden.isPresent()) {
				TipoIdentificacion tipoIdenNew = tipoIden.get();

				tipoIdenNew.setCodigo(tipoIdentificacionDTO.getCodigo());
				tipoIdenNew.setDescripcion(tipoIdentificacionDTO.getDescripcion());

				tipoIdentificacionRepo.save(tipoIdenNew);

			}

			response.put(Constantes.CODIGO_HTTP, "200");
			response.put(Constantes.MENSAJE_EXITO, "Registrado actualizado exitosamente");
			return response;

		} catch (Exception e) {

			response.put(Constantes.CODIGO_HTTP, "500");
			response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al actualizar");
			return response;

		}

	}

	@GetMapping("/consultarTiposIden")
	public Iterable<TipoIdentificacionDTO> consultarTipoIdentificacion() {
		Iterable<TipoIdentificacion> tipoIdentificacion = tipoIdentificacionRepo.findAll();
		return convertTipoIdentificacion.convertToIterableDTO(tipoIdentificacion);

	}

	@GetMapping("/consultarTipoIden/{id}")
	public TipoIdentificacionDTO consultarTipoIdenPorId(@PathVariable Long id) {
		Optional<TipoIdentificacion> tipoIdentificacion = tipoIdentificacionRepo.findById(id);
		TipoIdentificacionDTO tipoIdentificacionDTO = null;

		if (tipoIdentificacion.isPresent()) {

			tipoIdentificacionDTO = convertTipoIdentificacion.convertToDTO(tipoIdentificacion.get());
		}

		return tipoIdentificacionDTO;

	}

}
