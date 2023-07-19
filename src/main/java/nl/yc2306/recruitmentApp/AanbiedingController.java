package nl.yc2306.recruitmentApp;

import nl.yc2306.recruitmentApp.DTOs.AanbiedingDTO;
import nl.yc2306.recruitmentApp.DTOs.BeknoptCV;
import nl.yc2306.recruitmentApp.Login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class AanbiedingController {
	
	@Autowired
	private AanbiedingService aanbiedingService;

	@Autowired
	private LoginService loginService;
	
	@Autowired
	private VacatureService vacatureService;
	
	@Autowired
	private CurriculumVitaeService curriculumVitaeService;

	// @RequestMapping(method = RequestMethod.POST)
	@PostMapping("/aanbieding/aanmaken")
	public Aanbieding create(@RequestBody AanbiedingDTO aanbieding) {
		Aanbieding a=new Aanbieding();
		a.setVacature(vacatureService.findVacatureById(aanbieding.getVacatureID()).get());
		a.setCurriculumVitae(curriculumVitaeService.getOne(aanbieding.getCvID()).get());
		a.setCreatedOn(LocalDateTime.now());
		Aanbieding newAanbieding = aanbiedingService.create(a);
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
