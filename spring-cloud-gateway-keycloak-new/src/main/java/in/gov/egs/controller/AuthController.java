package in.gov.egs.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
	
	@GetMapping("/auth-check")
	public ResponseEntity<?> checkAuth(Authentication auth) {
	    return ResponseEntity.ok(
	        auth.getAuthorities().stream().map(Object::toString).toList()
	    );
	}
}	

