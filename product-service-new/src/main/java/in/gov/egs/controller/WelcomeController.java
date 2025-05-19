package in.gov.egs.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/welcome")
public class WelcomeController {
	
	
	@GetMapping("/callback")
	public String fetchActors(){
		return "Welcome to the rest service";
		 
	}

}
