package in.gov.egs.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/states")
public class StatesController {
	
	@RequestMapping
	public String fetchStates() {
		return "This is state user";
	}

}
