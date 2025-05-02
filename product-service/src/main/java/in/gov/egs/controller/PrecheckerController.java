package in.gov.egs.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prechecker")
public class PrecheckerController {
	
	@RequestMapping
	public String fetchPrechecker() {
		return "This is prechecker user";
	}

}
