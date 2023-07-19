package nl.yc2306.recruitmentApp;

import nl.yc2306.recruitmentApp.DTOs.AanbiedingDTO;
import nl.yc2306.recruitmentApp.DTOs.BeknoptCV;
import nl.yc2306.recruitmentApp.DTOs.AanbiedingAanBedrijf;
import nl.yc2306.recruitmentApp.Login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@CrossOrigin(maxAge = 3600)
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

	@GetMapping("/aanbieding/nieuw/{vacatureId}")
	public Iterable<AanbiedingAanBedrijf> nieuweAanbiedingenPerVacature(@RequestHeader String AUTH_TOKEN, @PathVariable long vacatureId){
		String[] pages = {"/aanbiedingenpervacature.html"};
		if(!loginService.isAuthorised(AUTH_TOKEN, pages))
			return null;
		Account user = loginService.findLoggedinUser(AUTH_TOKEN);
		List<Aanbieding> aanbiedingen = aanbiedingService.getAanbiedingenVanVacature(vacatureId,user);
		return aanbiedingen.stream().map(aanbieding -> aanbieding.maakAanbiedingAanBedrijf()).toList();
	}

	@GetMapping("/aanbieding/uitgenodigd/{vacatureId}")
	public Iterable<AanbiedingAanBedrijf> uitgenodigdeKandidaten(@RequestHeader String AUTH_TOKEN, @PathVariable long vacatureId){
		String[] pages = {"/aanbiedingenpervacature.html"};
		if(!loginService.isAuthorised(AUTH_TOKEN, pages))
			return null;
		Account user = loginService.findLoggedinUser(AUTH_TOKEN);
		List<Aanbieding> aanbiedingen = aanbiedingService.getUitgenodigdenVanVacature(vacatureId,user);
		return aanbiedingen.stream().map(aanbieding -> aanbieding.maakAanbiedingAanBedrijf()).toList();
	}
}
