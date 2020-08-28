package co.com.udem.inmobiliaria.util;

import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import co.com.udem.inmobiliaria.dto.UsuarioDTO;
import co.com.udem.inmobiliaria.entities.Usuario;

public class ConvertUsuario {
	
	@Autowired
	private ModelMapper modelMapper;
	   
    public Usuario convertToEntity(UsuarioDTO usuarioDTO) {
        return modelMapper.map(usuarioDTO, Usuario.class);
    }
    
    public UsuarioDTO convertToDTO(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioDTO.class);
    }
    
    public Iterable<UsuarioDTO> convertToIterableDTO(Iterable<Usuario> usuario){
    	return Arrays.asList(modelMapper.map(usuario, UsuarioDTO[].class));
    }

}
