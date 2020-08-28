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


@RunWith(SpringRunner.class)
@SpringBootTest(classes = InmobiliariaApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TipoIdentificacionRestControllerTest {

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
	public void testcrearTipoIdentificacion() {
		String username = "669819111";
		String password = "Oracle123";
		token = token(username, password);
		
		TipoIdentificacionDTO tipoIdentificacionDTO = new TipoIdentificacionDTO();
		tipoIdentificacionDTO.setCodigo("CC");
		tipoIdentificacionDTO.setDescripcion("Cédula de Ciudadanía");
		
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("El token es: "+this.token);
        headers.set("Authorization", "Bearer "+this.token);
        HttpEntity<TipoIdentificacionDTO> entity = new HttpEntity<TipoIdentificacionDTO>(tipoIdentificacionDTO, headers);

        ResponseEntity<String> postResponse = restTemplate.exchange(getRootUrl() + "/tiposIden/crearTipoIden", HttpMethod.POST, entity, String.class);
        
        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
		assertNotNull(postResponse.getBody());
	}

	@Test
	public void testactualizarTipoIdentificacion() {
		int id = 1;
		String username = "669819113";
		String password = "prueba123";
		token = token(username, password);
		
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("El token en Actualizar Usuario es: "+this.token);
        headers.set("Authorization", "Bearer "+this.token);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/tiposIden/consultarTipoIden/" + id,
				HttpMethod.GET, entity, String.class);
        Gson g = new Gson();
        TipoIdentificacionDTO tipoIdentificacionDTO = g.fromJson(response.getBody(), TipoIdentificacionDTO.class);
        
		tipoIdentificacionDTO.setCodigo("CCC");
		tipoIdentificacionDTO.setDescripcion("Cédulaaaaaa");
		
		HttpEntity<TipoIdentificacionDTO> requestUpdate = new HttpEntity<>(tipoIdentificacionDTO, headers);
		restTemplate.exchange(getRootUrl() + "/tiposIden/actualizarTipoIden/" + id, HttpMethod.PUT, requestUpdate, TipoIdentificacionDTO.class);
		
		ResponseEntity<String> updateTipoIdenDTO = restTemplate.exchange(getRootUrl() + "/tiposIden/consultarTipoIden/" + id,
				HttpMethod.GET, entity, String.class);
		
		assertNotNull(updateTipoIdenDTO.getBody());
		assertEquals(true, updateTipoIdenDTO.getBody().contains("Cédulaaaaaa"));
		System.out.println("Respuesta Actualizar Usuario Actualizado: "+updateTipoIdenDTO.getBody());

	}

	@Test
	public void testconsultarTipoIdenPorId() {
		int id = 4;
		String username = "669819112";
		String password = "Oracle124";
		token = token(username, password);
		
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("El token en consultar por Id es: "+this.token);
        headers.set("Authorization", "Bearer "+this.token);
        HttpEntity<String> entity = new HttpEntity<String>(headers);
        
        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/tiposIden/consultarTipoIden/" + id,
				HttpMethod.GET, entity, String.class);

		assertNotNull(response.getBody());
		assertEquals(true, response.getBody().contains("Cédula de Extranjería"));
		System.out.println("Respuesta Consultar Tipo Identificacion ID: "+response.getBody());

	}

	@Test
	public void testGetTiposIdentificacion() {
		String username = "44999888";
		String password = "prueba123456";
		token = token(username, password);
		
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("El token en ConsultarUsuarios es: "+this.token);
        headers.set("Authorization", "Bearer "+this.token);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/tiposIden/consultarTiposIden/",
				HttpMethod.GET, entity, String.class);
		assertNotNull(response.getBody());
		assertEquals(true, response.getBody().contains("Cédula de Ciudadanía"));

	}

	@Test
	public void testborrarTipoIdentificacion() {
		int id = 5;
		String username = "55999777";
		String password = "w1234j";
		token = token(username, password);
		
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        System.out.println("El token en BorrarUsuario es: "+this.token);
        headers.set("Authorization", "Bearer "+this.token);
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/tiposIden/consultarTipoIden/" + id,
				HttpMethod.GET, entity, String.class);

		assertNotNull(response.getBody());
		assertEquals(true, response.getBody().contains("Licencia de Conducción"));
		
		if (response.getBody().contains("Licencia de Conducción")) {
			ResponseEntity<String> requestDelete = 
			restTemplate.exchange(getRootUrl() + "/tiposIden/borrarTipoIden/" + id, HttpMethod.DELETE, entity, String.class, id);
			assertEquals(HttpStatus.OK, requestDelete.getStatusCode());
		}
		
		try {
			ResponseEntity<String> responseDel = restTemplate.exchange(getRootUrl() + "/tiposIden/consultarTipoIden/" + id,
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
