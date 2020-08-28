package co.com.udem.inmobiliaria.rest.controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.com.udem.inmobiliaria.dto.UserDTO;
import co.com.udem.inmobiliaria.entities.User;
import co.com.udem.inmobiliaria.repositories.UserRepository;
import co.com.udem.inmobiliaria.util.Constantes;

@RestController
public class UserRestController {
	
	@Autowired
    private UserRepository userRepository;
   
    @Autowired
    PasswordEncoder passwordEncoder;
   
    @PostMapping("/users/addUser")
    public Map<String, String> adicionarUsuario(@RequestBody UserDTO userDTO) {
        Map<String, String> response = new HashMap<>();
       
        userRepository.save(User.builder()
                .username(userDTO.getUsername())
                .password(this.passwordEncoder.encode(userDTO.getPassword()))
                .roles(Arrays.asList( "ROLE_USER"))
                .build());
        response.put(Constantes.CODIGO_HTTP, "200");
        response.put(Constantes.MENSAJE_EXITO, "Registrado insertado exitosamente");
        return response;
       
    }

}
