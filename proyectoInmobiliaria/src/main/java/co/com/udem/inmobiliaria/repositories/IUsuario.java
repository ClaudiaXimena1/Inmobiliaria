package co.com.udem.inmobiliaria.repositories;

import org.springframework.data.repository.CrudRepository;

import co.com.udem.inmobiliaria.entities.Usuario;

public interface IUsuario extends CrudRepository<Usuario, Long> {
	
	public Usuario findUsuarioByIdAndPassword(Integer id, String password);

}
