package nl.yc2306.recruitmentApp;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nl.yc2306.recruitmentApp.DTOs.BeknoptCV;
import nl.yc2306.recruitmentApp.DTOs.BeknopteVacature;
import nl.yc2306.recruitmentApp.DTOs.FilterRequest;
import nl.yc2306.recruitmentApp.DTOs.VacatureDetail;

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

	
@RequestMapping(method = RequestMethod.PUT, value = "/vacature/update/{vacatureId}")
public void update(@PathVariable long vacatureId, @RequestBody Vacature newVacature) {
    // Step 1 - Find current vacature
    Optional<Vacature> optional = service.findVacatureById(vacatureId); // Assuming findVacatureById returns Optional<Vacature>

    if (optional.isPresent()) {
        Vacature dbVacature = optional.get();
        dbVacature.setAccount(newVacature.getAccount());
        dbVacature.setBedrijf(newVacature.getBedrijf());
        dbVacature.setLocatie(newVacature.getLocatie());
        dbVacature.setOmschrijving(newVacature.getOmschrijving());
        dbVacature.setUitstroomRichting(newVacature.getUitstroomRichting());
        dbVacature.setFunctie(newVacature.getFunctie());

        // Step 2 - Save the updated vacature
        service.saveVacature(dbVacature);
    }
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

    @RequestMapping("vacatures/beknopt")
    public Iterable<BeknopteVacature> getCVsBeknopt(@RequestBody FilterRequest filterparams){
        Iterable<Vacature> vacatures = service.getFiltered(filterparams);
        List<BeknopteVacature> minimalvacatures = new ArrayList<BeknopteVacature>();
        for (Vacature vacature: vacatures) {
            BeknopteVacature bkv = new BeknopteVacature();
            bkv.setId(vacature.getId());
            bkv.setBedrijf(vacature.getBedrijf());
            bkv.setLocatie(vacature.getLocatie());
            bkv.setUitstroomRichting(vacature.getUitstroomRichting());
            minimalvacatures.add(bkv);
        }
        return minimalvacatures;
    }
	

	@RequestMapping(method = RequestMethod.GET, value = "vacature/vacaturedetail/{vacatureId}")
	public VacatureDetail getVacatureDetail(@PathVariable long vacatureId) {
		Vacature vacature = new Vacature();
		VacatureDetail vd = new VacatureDetail();
		
		if (service.findVacatureById(vacatureId).isPresent())
		{
			// Get vacature from Optional<Vacature>
			vacature = service.findVacatureById(vacatureId).get();
		}
		
//		service.findVacatureById(vacatureId).ifPresent(v -> {
//            vacature = v;    
//        });
		
		vd.setBedrijf(vacature.getBedrijf());
		vd.setFunctie(vacature.getFunctie());
		vd.setLocatie(vacature.getLocatie());
		vd.setOmschrijving(vacature.getOmschrijving());
		vd.setUitstroomRichting(vacature.getUitstroomRichting());
		
		return vd;
	}	
}
