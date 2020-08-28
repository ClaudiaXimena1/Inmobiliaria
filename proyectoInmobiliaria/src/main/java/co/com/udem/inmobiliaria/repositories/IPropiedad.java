package co.com.udem.inmobiliaria.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import co.com.udem.inmobiliaria.entities.Propiedad;

public interface IPropiedad extends CrudRepository<Propiedad, Long> {
	
	public List<Propiedad> findByTipoRegistroAndAreaAndValorAndNumHabitaciones(String tipoRegistro, String area, Double valor, Integer numHabitaciones);
	
	@Query(value ="select p.* from propiedad p, usuario u where u.id = ?1 and u.id = p.usuario_id", nativeQuery = true)
	public List<Propiedad> findByUsuarioId(Long id);

}
