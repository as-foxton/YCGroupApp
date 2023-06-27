package nl.yc2306.recruitmentApp;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.JsonPath;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(maxAge=2030)
public class VacatureController {

	@Autowired
	private VacatureService service;


	@RequestMapping(method = RequestMethod.PUT, value = "vacature/update/{vactureId}")
	public void update(@PathVariable long vacatureId, @RequestBody Vacature newVacature) {
		// Steps to adjust
		// Step 1 - find current person
		Optional<Vacature> optional = service.findVacature(vacatureId);
		
		
		//Step 2 - Adjust
		Vacature dbVacature = optional.get();
		dbVacature.setBedrijf(newVacature.getBedrijf());
		
		// Step 3 - Save
		service.saveVacature(dbVacature);
		
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "vacature/delete/{vacatureId}")
	public void delete(@PathVariable long vacatureId) {
		
		service.deleteVacature(vacatureId);
	}
	

	@RequestMapping(method = RequestMethod.POST, value = "vacature/create")
	public void maakAan(@RequestBody Vacature vacature) {
		System.out.println("Het naam van de bedrijf is "  + vacature.getBedrijf());
		service.saveVacature(vacature);
		
	}
	
	@RequestMapping("vacature/all")
	public Iterable<Vacature> all() {
		return service.vindAlleVacatures();
	}
	
}
