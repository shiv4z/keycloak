package in.gov.egs.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/items")
public class ItemsController {
	
	
	@GetMapping
	public List<String> fetchItems(){
		return Arrays.asList("Electronics", "Appliances", "Funitures", "Clothes");
	}


}
