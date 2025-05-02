package in.gov.egs.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cec")
public class CecController {
	
	@RequestMapping
	public String fetchCec() {
		return "This is CEC user";
	}

}
