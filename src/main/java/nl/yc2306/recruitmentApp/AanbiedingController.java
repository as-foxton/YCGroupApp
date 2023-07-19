package nl.yc2306.recruitmentApp;

import nl.yc2306.recruitmentApp.DTOs.BeknoptCV;
import nl.yc2306.recruitmentApp.Login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class AanbiedingController {
	
	@Autowired
	private AanbiedingService aanbiedingService;

	@Autowired
	private LoginService loginService;

	// @RequestMapping(method = RequestMethod.POST)
	@PostMapping("/aanbieding/aanmaken")
	public Aanbieding create(@RequestBody Aanbieding aanbieding) {
		Aanbieding newAanbieding = aanbiedingService.create(aanbieding);
		return newAanbieding;
	}

	@GetMapping("/aanbieding/{vacatureId}")
	public Iterable<BeknoptCV> aanbiedingenPerVacature(@RequestHeader String AUTH_TOKEN, @PathVariable long vacatureId){
		String[] pages = {"/temp.html"};
		if(!loginService.isAuthorised(AUTH_TOKEN, pages))
			return null;
		List<CurriculumVitae> cvs = aanbiedingService.getAanbiedingenVanVacature(vacatureId);
		return cvs.stream().map(cv -> cv.getBeknopt()).toList();
	}
}
