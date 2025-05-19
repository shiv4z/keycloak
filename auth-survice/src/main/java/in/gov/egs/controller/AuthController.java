package in.gov.egs.controller;

import java.net.URI;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import in.gov.egs.pojo.LoginRequest;
import in.gov.egs.properties.KeycloakClientProperties;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final KeycloakClientProperties properties;

    public AuthController(KeycloakClientProperties properties) {
        this.properties = properties;
    }

    @GetMapping("/callback")
    public ResponseEntity<?> handleCallback(
            @RequestParam("code") String code,
            @RequestParam("state") String state 
    ) {
        KeycloakClientProperties.ClientConfig config = properties.getClients().get(state);
        if (config == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Unknown client state: " + state));
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "authorization_code");
        formData.add("code", code);
        formData.add("client_id", config.getClientId());
        formData.add("client_secret", config.getClientSecret());
        formData.add("redirect_uri", properties.getRedirectUri());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
                properties.getTokenUri(),
                HttpMethod.POST,
                request,
                Map.class
            );
            return ResponseEntity.ok(response.getBody());
        } catch (RestClientException e) {
            return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("error", "Token exchange failed", "message", e.getMessage()));
        }
    }
    
    @GetMapping("/logout")
    public ResponseEntity<Void> logout(@RequestParam("id_token_hint") String idToken) {
        String redirectUrl = UriComponentsBuilder
                .fromHttpUrl(properties.getLogoutUri())
                .queryParam("id_token_hint", idToken)
                .queryParam("post_logout_redirect_uri", properties.getPostLogoutRedurect())
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(redirectUrl));
        return new ResponseEntity<>(headers, HttpStatus.FOUND); 
    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest, BindingResult br) {
    	 KeycloakClientProperties.ClientConfig config = properties.getClients().get("client-a");
         if (config == null) {
             return ResponseEntity.badRequest().body(Map.of("error", "Unknown client state: "));
         }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("grant_type", "password");
        formData.add("client_id", config.getClientId());
        formData.add("client_secret", config.getClientSecret());
        formData.add("username", loginRequest.getUserName());
        formData.add("password", loginRequest.getUserPazz());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(
            		properties.getTokenUri(),
                    HttpMethod.POST,
                    request,
                    Map.class
            );
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Login failed", "message", e.getMessage()));
        }
    }
}
