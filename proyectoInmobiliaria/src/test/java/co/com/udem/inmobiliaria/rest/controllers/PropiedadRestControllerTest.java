package co.com.udem.inmobiliaria.rest.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.google.gson.Gson;

import co.com.udem.inmobiliaria.InmobiliariaApplication;
import co.com.udem.inmobiliaria.dto.AutenticationRequestDTO;
import co.com.udem.inmobiliaria.dto.AutenticationResponseDTO;
import co.com.udem.inmobiliaria.dto.PropiedadDTO;
import co.com.udem.inmobiliaria.dto.TipoIdentificacionDTO;
import co.com.udem.inmobiliaria.dto.UsuarioDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InmobiliariaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PropiedadRestControllerTest {
	
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}
	
	private String token;
	
	private AutenticationRequestDTO autenticationRequestDTO = new AutenticationRequestDTO();
	
	@Test
	public void testcrearPropiedad() {
		String username = "669819111";
		String password = "Oracle123";
		token = token(username, password);
		PropiedadDTO propiedadDTO = new PropiedadDTO();
		TipoIdentificacionDTO tipoIdentificacionDTO = new TipoIdentificacionDTO();
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		
		propiedadDTO.setArea("80 m2");
		propiedadDTO.setNumBanos(3);
		propiedadDTO.setNumHabitaciones(3);
		propiedadDTO.setTipoRegistro("Arriendo");
		propiedadDTO.setValor(1500000.00);
		tipoIdentificacionDTO.setCodigo("CC");
		tipoIdentificacionDTO.setDescripcion("Cédula de Ciudadanía");
		usuarioDTO.setNumeroIdentificacion(4444444);
		usuarioDTO.setNombres("Camila Valentina");
		usuarioDTO.setApellidos("Valderrama");
		usuarioDTO.setDireccion("Calle 5B con 44");
		usuarioDTO.setTelefono(4445555);
		usuarioDTO.setEmail("camila.v@hotmail.com");
		usuarioDTO.setPassword("camival123");
		usuarioDTO.setTipoIdentificacionDTO(tipoIdentificacionDTO);
		propiedadDTO.setUsuarioDTO(usuarioDTO);
		
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("El token es: "+this.token);
        headers.set("Authorization", "Bearer "+this.token);
        HttpEntity<PropiedadDTO> entity = new HttpEntity<PropiedadDTO>(propiedadDTO, headers);
        
		ResponseEntity<String> postResponse = restTemplate.exchange(getRootUrl() + "/propiedades/crearPropiedad", HttpMethod.POST, entity, String.class);
		
		assertEquals(HttpStatus.OK, postResponse.getStatusCode());
		assertNotNull(postResponse.getBody());
	}
	
	@Test
	public void testactualizarPropiedad() {
		int id = 1;
		
		String username = "669819113";
		String password = "prueba123";
		token = token(username, password);
		
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("El token en Actualizar Usuario es: "+this.token);
        headers.set("Authorization", "Bearer "+this.token);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/usuarios/consultarUsuario/" + id,
				HttpMethod.GET, entity, String.class);
        Gson g = new Gson();
        PropiedadDTO propiedadDTO = g.fromJson(response.getBody(), PropiedadDTO.class);
		
		TipoIdentificacionDTO tipoIdentificacionDTO = new TipoIdentificacionDTO();
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		
		propiedadDTO.setArea("90 m2");
		propiedadDTO.setNumBanos(3);
		propiedadDTO.setNumHabitaciones(3);
		propiedadDTO.setTipoRegistro("Arriendo");
		propiedadDTO.setValor(1500000.00);
		usuarioDTO.setNumeroIdentificacion(4444444);
		usuarioDTO.setNombres("Camila Valentina");
		usuarioDTO.setApellidos("Valderrama");
		usuarioDTO.setDireccion("Calle 5B con 44");
		usuarioDTO.setTelefono(4445555);
		usuarioDTO.setEmail("camila.valderrama@hotmail.com");
		usuarioDTO.setPassword("camival12345");
		tipoIdentificacionDTO.setId((long) 24);
		tipoIdentificacionDTO.setCodigo("CC");
		tipoIdentificacionDTO.setDescripcion("Cédula");
		usuarioDTO.setTipoIdentificacionDTO(tipoIdentificacionDTO);
		propiedadDTO.setUsuarioDTO(usuarioDTO);
		
		HttpEntity<PropiedadDTO> requestUpdate = new HttpEntity<>(propiedadDTO, headers);
		restTemplate.exchange(getRootUrl() + "/propiedades/actualizarPropiedad/" + id, HttpMethod.PUT, requestUpdate, UsuarioDTO.class);
		
		ResponseEntity<String> updatePropiedadDTO = restTemplate.exchange(getRootUrl() + "/propiedades/consultarPropiedad/" + id,
				HttpMethod.GET, entity, String.class);
		
		assertNotNull(updatePropiedadDTO.getBody());
		assertEquals(true, updatePropiedadDTO.getBody().contains("90 m2"));
		System.out.println("Respuesta Actualizar Propiedad Actualizada: "+updatePropiedadDTO.getBody());
		
	}
	
	@Test
	public void testconsultarPropiedadPorId() {
		int id = 1;
		String username = "669819112";
		String password = "Oracle124";
		token = token(username, password);
		
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("El token en consultar por Id es: "+this.token);
        headers.set("Authorization", "Bearer "+this.token);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/propiedades/consultarPropiedad/" + id,
				HttpMethod.GET, entity, String.class);
        
        Gson g = new Gson();
        PropiedadDTO propiedadDTO = g.fromJson(response.getBody(), PropiedadDTO.class);

        assertNotNull(propiedadDTO);
		System.out.println("Respuesta Consultar Propiedad ID: "+response.getBody());

	}
	
	@Test
	public void testconsultarPropiedades() {
		String username = "44999888";
		String password = "prueba123456";
		token = token(username, password);
		
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("El token en ConsultarUsuarios es: "+this.token);
        headers.set("Authorization", "Bearer "+this.token);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/propiedades/consultarPropiedades/",
				HttpMethod.GET, entity, String.class);
		assertNotNull(response.getBody());
		assertEquals(true, response.getBody().contains("80 m2"));

	}
	
	@Test
	public void testborrarPropiedad() {
		int id = 3;
		String username = "55999777";
		String password = "w1234j";
		token = token(username, password);
		
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("El token en BorrarUsuario es: "+this.token);
        headers.set("Authorization", "Bearer "+this.token);
		HttpEntity<String> entity = new HttpEntity<String>(headers);

		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/propiedades/consultarPropiedad/" + id,
				HttpMethod.GET, entity, String.class);

		assertNotNull(response.getBody());
		assertEquals(true, response.getBody().contains("120 m2"));
		
		if (response.getBody().contains("120 m2")) {
			ResponseEntity<String> requestDelete = 
			restTemplate.exchange(getRootUrl() + "/propiedades/borrarPropiedad/" + id, HttpMethod.DELETE, entity, String.class, id);
			assertEquals(HttpStatus.OK, requestDelete.getStatusCode());
		}
		
		try {
			ResponseEntity<String> responseDel = restTemplate.exchange(getRootUrl() + "/propiedades/consultarPropiedad/" + id,
					HttpMethod.GET, entity, String.class);
			assertNull(responseDel.getBody());
		} catch (final HttpClientErrorException e) {
			assertEquals(HttpStatus.NOT_FOUND, e.getStatusCode());
		}
	}
	
	private String token(String username, String password) {
        autenticationRequestDTO.setUsername(username);
        autenticationRequestDTO.setPassword(password);
        adicionarUsuario(autenticationRequestDTO);
        ResponseEntity<String> postResponse = restTemplate.postForEntity(getRootUrl() + "/auth/signin", autenticationRequestDTO, String.class);
        Gson g = new Gson();
        AutenticationResponseDTO autenticationResponseDTO = g.fromJson(postResponse.getBody(), AutenticationResponseDTO.class);
        token = autenticationResponseDTO.getToken();
        System.out.println("Token: " + token);
        return token;
    }
	
	private void adicionarUsuario(AutenticationRequestDTO autenticationRequestDTO) {
        ResponseEntity<String> postResponse = restTemplate.postForEntity(getRootUrl() + "/users/addUser", autenticationRequestDTO, String.class);
        postResponse.getBody();
    }

}
