package co.com.udem.inmobiliaria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TipoIdentificacionDTO {
	
	private Long id;
	private String codigo;
	private String descripcion;

}
