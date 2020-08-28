package co.com.udem.inmobiliaria.util;

import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import co.com.udem.inmobiliaria.dto.PropiedadDTO;
import co.com.udem.inmobiliaria.entities.Propiedad;

public class ConvertPropiedad {
	
	@Autowired
	private ModelMapper modelMapper;
	   
    public Propiedad convertToEntity(PropiedadDTO propiedadDTO) {
        return modelMapper.map(propiedadDTO, Propiedad.class);
    }
    
    public PropiedadDTO convertToDTO(Propiedad propiedad) {
        return modelMapper.map(propiedad, PropiedadDTO.class);
    }
    
    public Iterable<PropiedadDTO> convertToIterableDTO(Iterable<Propiedad> propiedad){
    	return Arrays.asList(modelMapper.map(propiedad, PropiedadDTO[].class));
    }

}
