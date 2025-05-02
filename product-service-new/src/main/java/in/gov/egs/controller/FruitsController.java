package in.gov.egs.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fruits")
public class FruitsController {
	
	@GetMapping
	public List<String> fetchFruits(){
		return Arrays.asList("Apple", "Banana", "Orange");
		 
	}

}
