package co.com.udem.inmobiliaria.rest.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.udem.inmobiliaria.dto.PropiedadDTO;
import co.com.udem.inmobiliaria.dto.TipoIdentificacionDTO;
import co.com.udem.inmobiliaria.dto.UsuarioDTO;
import co.com.udem.inmobiliaria.entities.Propiedad;
import co.com.udem.inmobiliaria.repositories.IPropiedad;
import co.com.udem.inmobiliaria.util.Constantes;
import co.com.udem.inmobiliaria.util.ConvertPropiedad;
import co.com.udem.inmobiliaria.util.ConvertTipoIdentificacion;
import co.com.udem.inmobiliaria.util.ConvertUsuario;

@RestController
@RequestMapping("/propiedades")
public class PropiedadRestController {
	
	private static final Logger log = LoggerFactory.getLogger(PropiedadRestController.class);
	
	@Autowired
	private IPropiedad propiedadRepo;

	@Autowired
	private ConvertPropiedad convertPropiedad;

	@Autowired
	private ConvertUsuario convertUsuario;
	
	@Autowired
	private ConvertTipoIdentificacion convertTipoIdentificacion;
	
	@PostMapping("/crearPropiedad")
	public Map<String, String> crearPropiedad(@RequestBody PropiedadDTO propiedadDTO) {
		Map<String, String> response = new HashMap<>();

		try {
			propiedadRepo.save(convertPropiedad.convertToEntity(propiedadDTO));
			response.put(Constantes.CODIGO_HTTP, "200");
			response.put(Constantes.MENSAJE_EXITO, "Registrado insertado exitosamente");
			return response;
		} catch (Exception e) {
			response.put(Constantes.CODIGO_HTTP, "500");
			response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al insertar");
			return response;

		}

	}
	
	@DeleteMapping("/borrarPropiedad/{id}")
	public Map<String, String> borrarPropiedad(@PathVariable Long id) {
		Map<String, String> response = new HashMap<>();

		try {
			propiedadRepo.deleteById(id);
			response.put(Constantes.CODIGO_HTTP, "200");
			response.put(Constantes.MENSAJE_EXITO, "Registrado eliminado exitosamente");
			return response;
		} catch (Exception e) {
			response.put(Constantes.CODIGO_HTTP, "500");
			response.put(Constantes.MENSAJE_ERROR, "Ocurrió un problema al eliminar");
			return response;

		}

	}
	
	@PutMapping("/actualizarPropiedad/{id}")
	public Map<String, String> actualizarPropiedad(@RequestBody PropiedadDTO propiedadDTO, @PathVariable Long id) {
		Map<String, String> response = new HashMap<>();

		try {

			Optional<Propiedad> propiedad = propiedadRepo.findById(id);
			if (propiedad.isPresent()) {
				Propiedad propiedadNew = propiedad.get();
				UsuarioDTO usuarioDTO = null;
				
				propiedadNew.setArea(propiedadDTO.getArea());
				propiedadNew.setNumBanos(propiedadDTO.getNumBanos());
				propiedadNew.setNumHabitaciones(propiedadDTO.getNumHabitaciones());
				propiedadNew.setTipoRegistro(propiedadDTO.getTipoRegistro());
				propiedadNew.setValor(propiedadDTO.getValor());
				usuarioDTO = propiedadDTO.getUsuarioDTO();
		        propiedadNew.setUsuario(convertUsuario.convertToEntity(usuarioDTO));

				propiedadRepo.save(propiedadNew);

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
	
	@GetMapping("/consultarPropiedades")
	public List<PropiedadDTO> consultarPropiedades() {

		Iterable<Propiedad> propiedades = propiedadRepo.findAll();
		List<PropiedadDTO> listaPropiedadesDTO = new ArrayList<>();
		List<Propiedad> listaPropiedades = new ArrayList<>();
		propiedades.iterator().forEachRemaining(listaPropiedades::add);

		for (int i = 0; i < listaPropiedades.size(); i++) {

			try {

				UsuarioDTO usuarioDTO = null;
				TipoIdentificacionDTO tipoIdentificacionDTO = null;
				
				if (listaPropiedades.get(i).getUsuario().getTipoIdentificacion() != null) {
					tipoIdentificacionDTO = convertTipoIdentificacion.convertToDTO(listaPropiedades.get(i).getUsuario().getTipoIdentificacion());
				}
				
				if (listaPropiedades.get(i).getUsuario() != null) {
					usuarioDTO = convertUsuario.convertToDTO(listaPropiedades.get(i).getUsuario());
					usuarioDTO.setTipoIdentificacionDTO(tipoIdentificacionDTO);
				}

				PropiedadDTO propiedadDTO = convertPropiedad.convertToDTO(listaPropiedades.get(i));
				propiedadDTO.setUsuarioDTO(usuarioDTO);
				listaPropiedadesDTO.add(propiedadDTO);

			} catch (Exception e) {
				log.error("consultarPropiedades", e);
			}

		}

		return listaPropiedadesDTO;

	}
	
	@GetMapping("/consultarPropiedad/{id}")
	public PropiedadDTO consultarPropiedadPorId(@PathVariable Long id) {
		Optional<Propiedad> propiedad = propiedadRepo.findById(id);
		PropiedadDTO propiedadDTO = null;
		TipoIdentificacionDTO tipoIdentificacionDTO = null;
		UsuarioDTO usuarioDTO = null;

		if (propiedad.isPresent()) {
			
			tipoIdentificacionDTO = convertTipoIdentificacion.convertToDTO(propiedad.get().getUsuario().getTipoIdentificacion());
			usuarioDTO = convertUsuario.convertToDTO(propiedad.get().getUsuario());
			usuarioDTO.setTipoIdentificacionDTO(tipoIdentificacionDTO);
			propiedadDTO = convertPropiedad.convertToDTO(propiedad.get());
			propiedadDTO.setUsuarioDTO(usuarioDTO);
		}

		return propiedadDTO;

	}
	
	@GetMapping("/consultarPropiedades/{tipoRegistro}/{area}/{valor}/{numHabitaciones}")
	public List<PropiedadDTO> consultarPropiedadesFiltro(@PathVariable String tipoRegistro, @PathVariable String area, @PathVariable Double valor, @PathVariable Integer numHabitaciones) {

		Iterable<Propiedad> propiedades = propiedadRepo.findByTipoRegistroAndAreaAndValorAndNumHabitaciones(tipoRegistro, area, valor, numHabitaciones);
		List<PropiedadDTO> listaPropiedadesDTO = new ArrayList<>();
		List<Propiedad> listaPropiedades = new ArrayList<>();
		propiedades.iterator().forEachRemaining(listaPropiedades::add);

		for (int i = 0; i < listaPropiedades.size(); i++) {

			try {

				UsuarioDTO usuarioDTO = null;
				TipoIdentificacionDTO tipoIdentificacionDTO = null;
				
				if (listaPropiedades.get(i).getUsuario().getTipoIdentificacion() != null) {
					tipoIdentificacionDTO = convertTipoIdentificacion.convertToDTO(listaPropiedades.get(i).getUsuario().getTipoIdentificacion());
				}
				if (listaPropiedades.get(i).getUsuario() != null) {
					usuarioDTO = convertUsuario.convertToDTO(listaPropiedades.get(i).getUsuario());
					usuarioDTO.setTipoIdentificacionDTO(tipoIdentificacionDTO);
				}

				PropiedadDTO propiedadDTO = convertPropiedad.convertToDTO(listaPropiedades.get(i));
				propiedadDTO.setUsuarioDTO(usuarioDTO);
				listaPropiedadesDTO.add(propiedadDTO);

			} catch (Exception e) {
				log.error("consultarPropiedadesFiltro", e);
			}

		}

		return listaPropiedadesDTO;

	}
	
	@GetMapping("/consultarPropiedadUsu/{id}")
	public List<PropiedadDTO> consultarPropiedadesUsuario(@PathVariable Long id) {

		Iterable<Propiedad> propiedades = propiedadRepo.findByUsuarioId(id);
		List<PropiedadDTO> listaPropiedadesDTO = new ArrayList<>();
		List<Propiedad> listaPropiedades = new ArrayList<>();
		propiedades.iterator().forEachRemaining(listaPropiedades::add);

		for (int i = 0; i < listaPropiedades.size(); i++) {

			try {

				UsuarioDTO usuarioDTO = null;
				TipoIdentificacionDTO tipoIdentificacionDTO = null;
				
				if (listaPropiedades.get(i).getUsuario().getTipoIdentificacion() != null) {
					tipoIdentificacionDTO = convertTipoIdentificacion.convertToDTO(listaPropiedades.get(i).getUsuario().getTipoIdentificacion());
				}
				if (listaPropiedades.get(i).getUsuario() != null) {
					usuarioDTO = convertUsuario.convertToDTO(listaPropiedades.get(i).getUsuario());
					usuarioDTO.setTipoIdentificacionDTO(tipoIdentificacionDTO);
				}

				PropiedadDTO propiedadDTO = convertPropiedad.convertToDTO(listaPropiedades.get(i));
				propiedadDTO.setUsuarioDTO(usuarioDTO);
				listaPropiedadesDTO.add(propiedadDTO);

			} catch (Exception e) {
				log.error("consultarPropiedadesUsuario", e);
			}

		}

		return listaPropiedadesDTO;

	}

}
