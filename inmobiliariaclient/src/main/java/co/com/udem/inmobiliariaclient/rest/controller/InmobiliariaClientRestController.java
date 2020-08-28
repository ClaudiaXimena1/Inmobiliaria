package co.com.udem.inmobiliariaclient.rest.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import co.com.udem.inmobiliariaclient.domain.AutenticationRequestDTO;
import co.com.udem.inmobiliariaclient.domain.AutenticationResponseDTO;
import co.com.udem.inmobiliariaclient.domain.PropiedadDTO;
import co.com.udem.inmobiliariaclient.entities.UserToken;
import co.com.udem.inmobiliariaclient.repositories.UserTokenRepository;


@RestController
public class InmobiliariaClientRestController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
    UserTokenRepository userTokenRepository;
   
    @Autowired
    UserToken userToken;
    
    @Autowired
    private LoadBalancerClient loadBalancer;
    
    private static final Logger log = LoggerFactory.getLogger(InmobiliariaClientRestController.class);
   
    @PostMapping("/autenticar")
    public String autenticar(@RequestBody AutenticationRequestDTO autenticationRequestDTO) {
        ServiceInstance serviceInstance=loadBalancer.choose("inmobiliaria");
        String baseUrl=serviceInstance.getUri().toString();       
        baseUrl=baseUrl+"/auth/signin";
        ResponseEntity<String> postResponse = restTemplate.postForEntity(baseUrl, autenticationRequestDTO, String.class);
        Gson g = new Gson();
        AutenticationResponseDTO autenticationResponseDTO = g.fromJson(postResponse.getBody(), AutenticationResponseDTO.class);
        userToken.setUsername(autenticationResponseDTO.getUsername());
        userToken.setToken(autenticationResponseDTO.getToken());
        userTokenRepository.save(userToken);
        
        return autenticationResponseDTO.getToken();
       
    }
   
    @GetMapping("/consultarPropiedades")
    public List<PropiedadDTO> consultarPropiedades() {
    	List<PropiedadDTO> propiedades = null;
    	ServiceInstance serviceInstance=loadBalancer.choose("inmobiliaria");
        String baseUrl=serviceInstance.getUri().toString();       
        baseUrl=baseUrl+"/propiedades/consultarPropiedades/";
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer "+userToken.getToken());
		HttpEntity<String> entity = new HttpEntity<>(headers);
		
		ResponseEntity<String> response = restTemplate.exchange(baseUrl,
				HttpMethod.GET, entity, String.class);
		
		try {
			propiedades = new ObjectMapper().readValue(response.getBody(), new TypeReference<List<PropiedadDTO>>() {});
        } catch (JsonProcessingException e) {
        	log.error("consultarPropiedades", e);
        }
		
		return propiedades;
       
    }

}
