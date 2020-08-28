package co.com.udem.inmobiliaria.dto;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropiedadDTO {
	
	private Long codigo;
	private String area;
	private Integer numHabitaciones;
	private Integer numBanos;
	private String tipoRegistro;
	private Double valor;
	
	@Autowired
	private UsuarioDTO usuarioDTO;

}
