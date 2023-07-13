package nl.yc2306.recruitmentApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AanbiedingController {
	
	@Autowired
	private AanbiedingService aanbiedingService;

	// @RequestMapping(method = RequestMethod.POST)
	@PostMapping("/aanbieding/aanmaken")
	public Aanbieding create(@RequestBody Aanbieding aanbieding) {
		Aanbieding newAanbieding = aanbiedingService.create(aanbieding);
		return newAanbieding;
	}
	
}
