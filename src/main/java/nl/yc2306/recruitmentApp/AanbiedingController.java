package nl.yc2306.recruitmentApp;

import nl.yc2306.recruitmentApp.DTOs.BeknoptCV;
import nl.yc2306.recruitmentApp.Login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(maxAge = 3600)
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

	@GetMapping("/aanbieding/nieuw/{vacatureId}")
	public Iterable<BeknoptCV> nieuweAanbiedingenPerVacature(@RequestHeader String AUTH_TOKEN, @PathVariable long vacatureId){
		String[] pages = {"/mijnaanbiedingen.html"};
		if(!loginService.isAuthorised(AUTH_TOKEN, pages))
			return null;
		Account user = loginService.findLoggedinUser(AUTH_TOKEN);
		List<CurriculumVitae> cvs = aanbiedingService.getAanbiedingenVanVacature(vacatureId,user);
		return cvs.stream().map(cv -> cv.maakBeknopt()).toList();
	}

	@GetMapping("/aanbieding/uitgenodigd/{vacatureId}")
	public Iterable<BeknoptCV> uitgenodigdeKandidaten(@RequestHeader String AUTH_TOKEN, @PathVariable long vacatureId){
		String[] pages = {"/mijnaanbiedingen.html"};
		if(!loginService.isAuthorised(AUTH_TOKEN, pages))
			return null;
		Account user = loginService.findLoggedinUser(AUTH_TOKEN);
		List<CurriculumVitae> cvs = aanbiedingService.getUitgenodigdenVanVacature(vacatureId,user);
		return cvs.stream().map(cv -> cv.maakBeknopt()).toList();
	}
}
