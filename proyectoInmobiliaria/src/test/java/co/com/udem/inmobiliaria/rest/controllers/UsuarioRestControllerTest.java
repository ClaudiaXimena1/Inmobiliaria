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
import co.com.udem.inmobiliaria.dto.TipoIdentificacionDTO;
import co.com.udem.inmobiliaria.dto.UsuarioDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = InmobiliariaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsuarioRestControllerTest {
	
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
	public void testcrearUsuario() {
		String username = "669819111";
		String password = "Oracle123";
		token = token(username, password);
		UsuarioDTO usuarioDTO = new UsuarioDTO();
		TipoIdentificacionDTO tipoIdentificacionDTO = new TipoIdentificacionDTO();
		
		usuarioDTO.setNumeroIdentificacion(4444444);
		usuarioDTO.setNombres("Camila Valentina");
		usuarioDTO.setApellidos("Valderrama");
		usuarioDTO.setDireccion("Calle 5B con 44");
		usuarioDTO.setTelefono(4445555);
		usuarioDTO.setEmail("camila.v@hotmail.com");
		usuarioDTO.setPassword("camival123");
		tipoIdentificacionDTO.setCodigo("TI");
		tipoIdentificacionDTO.setDescripcion("Tarjeta de Identidad");
		usuarioDTO.setTipoIdentificacionDTO(tipoIdentificacionDTO);
		
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("El token es: "+this.token);
        headers.set("Authorization", "Bearer "+this.token);
        HttpEntity<UsuarioDTO> entity = new HttpEntity<UsuarioDTO>(usuarioDTO, headers);
        
		ResponseEntity<String> postResponse = restTemplate.exchange(getRootUrl() + "/usuarios/crearUsuario", HttpMethod.POST, entity, String.class);
        
		assertEquals(HttpStatus.OK, postResponse.getStatusCode());
		assertNotNull(postResponse.getBody());

	}
	
	@Test
	public void testactualizarUsuario() {
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
        UsuarioDTO usuarioDTO = g.fromJson(response.getBody(), UsuarioDTO.class);
		
		TipoIdentificacionDTO tipoIdentificacionDTO = new TipoIdentificacionDTO();
		
		usuarioDTO.setNumeroIdentificacion(4444444);
		usuarioDTO.setNombres("Camila Valentina");
		usuarioDTO.setApellidos("Valderrama");
		usuarioDTO.setDireccion("Calle 5B con 44");
		usuarioDTO.setTelefono(4445555);
		usuarioDTO.setEmail("camila.valderrama@hotmail.com");
		usuarioDTO.setPassword("camival12345");
		tipoIdentificacionDTO.setId((long) 5);
		tipoIdentificacionDTO.setCodigo("CC");
		tipoIdentificacionDTO.setDescripcion("Cédula");
		usuarioDTO.setTipoIdentificacionDTO(tipoIdentificacionDTO);
		
		HttpEntity<UsuarioDTO> requestUpdate = new HttpEntity<>(usuarioDTO, headers);
		restTemplate.exchange(getRootUrl() + "/usuarios/actualizarUsuario/" + id, HttpMethod.PUT, requestUpdate, UsuarioDTO.class);
		
		ResponseEntity<String> updateUsuarioDTO = restTemplate.exchange(getRootUrl() + "/usuarios/consultarUsuario/" + id,
				HttpMethod.GET, entity, String.class);
		
		assertNotNull(updateUsuarioDTO.getBody());
		assertEquals(true, updateUsuarioDTO.getBody().contains("4444444"));
		System.out.println("Respuesta Actualizar Usuario Actualizado: "+updateUsuarioDTO.getBody());
		
	}
	
	@Test
	public void testconsultarUsuarioPorId() {
		int id = 1;
		String username = "669819112";
		String password = "Oracle124";
		token = token(username, password);
		
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("El token en consultar por Id es: "+this.token);
        headers.set("Authorization", "Bearer "+this.token);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/usuarios/consultarUsuario/" + id,
				HttpMethod.GET, entity, String.class);

		assertNotNull(response.getBody());
		assertEquals(true, response.getBody().contains("222333444"));
		System.out.println("Respuesta Consultar Usuario ID: "+response.getBody());

	}
	
	@Test
	public void testGetconsultarUsuarios() {
		String username = "44999888";
		String password = "prueba123456";
		token = token(username, password);
		
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("El token en ConsultarUsuarios es: "+this.token);
        headers.set("Authorization", "Bearer "+this.token);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/usuarios/consultarUsuarios/",
				HttpMethod.GET, entity, String.class);
		assertNotNull(response.getBody());
		assertEquals(true, response.getBody().contains("4444444"));
	}
	
	@Test
	public void testborrarUsuario() {
		int id = 2;		
		String username = "55999777";
		String password = "w1234j";
		token = token(username, password);
		
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("El token en BorrarUsuario es: "+this.token);
        headers.set("Authorization", "Bearer "+this.token);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/usuarios/consultarUsuario/" + id,
				HttpMethod.GET, entity, String.class);

		assertNotNull(response.getBody());
		assertEquals(true, response.getBody().contains("662333555"));
		
		if (response.getBody().contains("662333555")) {
			ResponseEntity<String> requestDelete = 
			restTemplate.exchange(getRootUrl() + "/usuarios/borrarUsuario/" + id, HttpMethod.DELETE, entity, String.class, id);
			assertEquals(HttpStatus.OK, requestDelete.getStatusCode());
		}
		
		try {
			ResponseEntity<String> responseDel = restTemplate.exchange(getRootUrl() + "/usuarios/consultarUsuario/" + id,
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
