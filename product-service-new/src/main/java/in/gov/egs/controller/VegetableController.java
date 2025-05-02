package in.gov.egs.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vegetable")
public class VegetableController {
	
	@GetMapping
	public List<String> fetchVegetables(){
		return Arrays.asList("Brinjal", "Cabbage", "Tomato", "Potato");
	}

}
