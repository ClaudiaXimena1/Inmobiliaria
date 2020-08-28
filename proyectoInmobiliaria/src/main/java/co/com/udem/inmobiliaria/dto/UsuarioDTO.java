package co.com.udem.inmobiliaria.dto;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

	private Long id;
	private Integer numeroIdentificacion;
	private String nombres;
	private String apellidos;
	private String direccion;
	private Integer telefono;
	private String email;
	private String password;

	@Autowired
	private TipoIdentificacionDTO tipoIdentificacionDTO;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	public void setNumeroIdentificacion(Integer numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	public String getNombres() {
		return nombres;
	}

	public void setNombres(String nombres) {
		this.nombres = nombres;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Integer getTelefono() {
		return telefono;
	}

	public void setTelefono(Integer telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public TipoIdentificacionDTO getTipoIdentificacionDTO() {
		return tipoIdentificacionDTO;
	}

	public void setTipoIdentificacionDTO(TipoIdentificacionDTO tipoIdentificacionDTO) {
		this.tipoIdentificacionDTO = tipoIdentificacionDTO;
	}

}
