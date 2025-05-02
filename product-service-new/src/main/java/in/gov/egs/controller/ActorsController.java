package in.gov.egs.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/actors")
public class ActorsController {
	
	
	@GetMapping
	public List<String> fetchActors(){
		return Arrays.asList("Sachin", "Narendra", "Trump", "Jaishankar");
		 
	}

}
