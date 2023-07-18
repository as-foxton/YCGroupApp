package nl.yc2306.recruitmentApp;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nl.yc2306.recruitmentApp.DTOs.AccountGegevens;
import nl.yc2306.recruitmentApp.DTOs.BeknoptCV;
import nl.yc2306.recruitmentApp.DTOs.BeknopteVacature;
import nl.yc2306.recruitmentApp.DTOs.FilterRequest;
import nl.yc2306.recruitmentApp.Login.LoginService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.JsonPath;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(maxAge=2030)
public class VacatureController {

@Autowired
private VacatureService service;

@Autowired
private LoginService loginService;
	
@RequestMapping(method = RequestMethod.PUT, value = "vacature/update/{id}")
public void update(@PathVariable long id, @RequestBody Vacature newVacature) {
    // Step 1 - Find current vacature
    Optional<Vacature> optional = service.findVacatureById(id); // Assuming findVacatureById returns Optional<Vacature>

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

	
@RequestMapping(method = RequestMethod.DELETE, value = "vacature/delete/{id}")
public void delete(@PathVariable long id) {
		service.deleteVacature(id);
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


@RequestMapping(method = RequestMethod.GET, value = "vacature/myVacatures/{account}")
public Iterable<Vacature> getAccountVacatures(@RequestHeader String AUTH_TOKEN) {
    // Fetch the vacancies for the specified accountId using the service method (assuming you have one)
	Account account = loginService.findLoggedinUser(AUTH_TOKEN);
    Iterable<Vacature> accountVacatures = service.findVacaturesByAccountId(account.getId());

    // You may add additional logic here if needed, such as handling the case when no vacancies are found

    return accountVacatures;
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

   
	
}
