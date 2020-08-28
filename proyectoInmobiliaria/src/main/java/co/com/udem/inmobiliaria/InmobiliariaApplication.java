package co.com.udem.inmobiliaria;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import co.com.udem.inmobiliaria.dto.TipoIdentificacionDTO;
import co.com.udem.inmobiliaria.util.ConvertPropiedad;
import co.com.udem.inmobiliaria.util.ConvertTipoIdentificacion;
import co.com.udem.inmobiliaria.util.ConvertUsuario;

@SpringBootApplication
@EnableDiscoveryClient
public class InmobiliariaApplication {

	public static void main(String[] args) {
		SpringApplication.run(InmobiliariaApplication.class, args);
	}
	
	@Bean
	public ConvertUsuario convertUsuario() {
		return new ConvertUsuario();
	}
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public ConvertTipoIdentificacion convertTipoIdentificacion() {
		return new ConvertTipoIdentificacion();
	}
	
	@Bean
	public TipoIdentificacionDTO tipoIdentificacionDTO() {
		return new TipoIdentificacionDTO();
	}
	
	@Bean
	public ConvertPropiedad convertPropiedad() {
		return new ConvertPropiedad();
	}
	
	@Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder();
    }

}
