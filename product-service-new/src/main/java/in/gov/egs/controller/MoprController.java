package in.gov.egs.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/mopr")
public class MoprController {

	@RequestMapping
	public String fetchMopr() {
		return "This is Mopr user";
	}
}
