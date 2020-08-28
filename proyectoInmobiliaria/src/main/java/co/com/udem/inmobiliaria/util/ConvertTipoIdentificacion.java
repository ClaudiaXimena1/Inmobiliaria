package co.com.udem.inmobiliaria.util;

import java.util.Arrays;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import co.com.udem.inmobiliaria.dto.TipoIdentificacionDTO;
import co.com.udem.inmobiliaria.entities.TipoIdentificacion;

public class ConvertTipoIdentificacion {
	
	@Autowired
	private ModelMapper modelMapper;
	   
    public TipoIdentificacion convertToEntity(TipoIdentificacionDTO tipoIdentificacionDTO) {
        return modelMapper.map(tipoIdentificacionDTO, TipoIdentificacion.class);
    }
    
    public TipoIdentificacionDTO convertToDTO(TipoIdentificacion tipoIdentificacion) {
        return modelMapper.map(tipoIdentificacion, TipoIdentificacionDTO.class);
    }
    
    public Iterable<TipoIdentificacionDTO> convertToIterableDTO(Iterable<TipoIdentificacion> tipoIdentificacion){
    	return Arrays.asList(modelMapper.map(tipoIdentificacion, TipoIdentificacionDTO[].class));
    }

}
