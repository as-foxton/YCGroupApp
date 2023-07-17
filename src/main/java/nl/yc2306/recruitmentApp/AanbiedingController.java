package nl.yc2306.recruitmentApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

	@GetMapping("/aanbieding/{vacatureId}")
	public Iterable<Aanbieding> aanbiedingenPerVacature(long vacatureId){
		return aanbiedingService.getAanbiedingenVanVacature(vacatureId);
	}
}
