package co.com.udem.inmobiliaria.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import co.com.udem.inmobiliaria.entities.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
	public Optional<User> findByUsername(String username);

}
