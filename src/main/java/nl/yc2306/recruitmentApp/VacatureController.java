package nl.yc2306.recruitmentApp;


import java.util.ArrayList;
import java.util.Collections;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(maxAge=2030, origins = "*")
public class VacatureController {

@Autowired
private VacatureService service;

@Autowired
private LoginService loginService;
	
@RequestMapping(method=RequestMethod.PUT, value="vacature/update/{id}")
public void update(@PathVariable long id, @RequestBody Vacature newVacature){
	Vacature current = service.findVacatureById(id).get();
    current.setBedrijf(newVacature.getBedrijf());
    current.setLocatie(newVacature.getLocatie());
    current.setOmschrijving(newVacature.getOmschrijving());
    current.setUitstroomRichting(newVacature.getUitstroomRichting());
    current.setFunctie(newVacature.getFunctie());
    service.saveVacature(current);
}
	
@RequestMapping(method = RequestMethod.DELETE, value = "vacature/delete/{id}")
public void delete(@PathVariable long id) {
		service.deleteVacature(id);
	}
	

@RequestMapping(method=RequestMethod.POST, value="vacature/create")
public void add(@RequestHeader String AUTH_TOKEN, @RequestBody Vacature vacature){
   
    Account user = loginService.findLoggedinUser(AUTH_TOKEN);
    System.out.println("test");
    vacature.setAccount(user);
    service.saveVacature(vacature);
    System.out.println("saveVacature()");
    System.out.println(vacature);
}
	
@RequestMapping("vacature/all")
public Iterable<Vacature> all() {
		return service.vindAlleVacatures();
	}


@RequestMapping(method = RequestMethod.GET, value = "vacatures/myvacatures/{AUTH_TOKEN}")
public Iterable<Vacature> getAccountVacatures(@PathVariable String AUTH_TOKEN) {
    // Fetch the account using the token
    Account account = loginService.findLoggedinUser(AUTH_TOKEN);

    if (account == null) {
        System.out.println("No account found for AUTH_TOKEN: " + AUTH_TOKEN);
        // Handle the case when the user is not logged in or the token is invalid
        // You can return an empty list or an error response here
        return Collections.emptyList();
    }

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
