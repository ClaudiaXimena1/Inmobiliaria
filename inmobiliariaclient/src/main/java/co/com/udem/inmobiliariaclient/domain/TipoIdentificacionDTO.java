package co.com.udem.inmobiliariaclient.domain;

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
