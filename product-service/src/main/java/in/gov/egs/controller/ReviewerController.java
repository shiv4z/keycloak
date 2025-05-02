package in.gov.egs.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reviewer")
public class ReviewerController {

	@RequestMapping
	public String fetchReviewer() {
		return "This is Reviewer user";
	}

}
