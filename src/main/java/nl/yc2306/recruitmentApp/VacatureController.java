package nl.yc2306.recruitmentApp;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import nl.yc2306.recruitmentApp.DTOs.BeknopteVacature;
import nl.yc2306.recruitmentApp.DTOs.FilterRequest;
import nl.yc2306.recruitmentApp.DTOs.VacatureDetail;
import nl.yc2306.recruitmentApp.Login.LoginService;

@RestController
@CrossOrigin(maxAge=2030, origins = "*")
public class VacatureController {

    @Autowired
    private VacatureService service;

    @Autowired
    private LoginService loginService;

    @RequestMapping(method=RequestMethod.PUT, value="vacature/update/{id}")
    public void update(@RequestHeader String AUTH_TOKEN, @PathVariable long id, @RequestBody Vacature newVacature){
        String[] roles = {"Opdrachtgever"};
        if(!loginService.isAuthorised(AUTH_TOKEN, roles))
            return;
        Vacature current = service.findVacatureById(id).get();
        current.setBedrijf(newVacature.getBedrijf());
        current.setLocatie(newVacature.getLocatie());
        current.setOmschrijving(newVacature.getOmschrijving());
        current.setUitstroomRichting(newVacature.getUitstroomRichting());
        current.setFunctie(newVacature.getFunctie());
        service.saveVacature(current);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "vacature/delete/{id}")
    public void delete(@RequestHeader String AUTH_TOKEN, @PathVariable long id) {
        String[] roles = {"Opdrachtgever"};
        if(!loginService.isAuthorised(AUTH_TOKEN, roles))
            return;
            service.deleteVacature(id);
        }


    @RequestMapping(method=RequestMethod.POST, value="vacature/create")
    public void add(@RequestHeader String AUTH_TOKEN, @RequestBody Vacature vacature){
        String[] roles = {"Opdrachtgever"};
        if(!loginService.isAuthorised(AUTH_TOKEN, roles))
            return;
        Account user = loginService.findLoggedinUser(AUTH_TOKEN);
        vacature.setAccount(user);
        service.saveVacature(vacature);
    }

    @RequestMapping(method = RequestMethod.GET, value = "vacatures/myvacatures/{AUTH_TOKEN}")
    public Iterable<Vacature> getAccountVacatures(@PathVariable String AUTH_TOKEN) {
        String[] roles = {"Opdrachtgever"};
        if(!loginService.isAuthorised(AUTH_TOKEN, roles))
            return null;
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
    public Iterable<BeknopteVacature> getVacaturesBeknopt(@RequestHeader String AUTH_TOKEN, @RequestBody FilterRequest filterparams){
        String[] roles = {"Accountmanager", "Trainee"};
        if(!loginService.isAuthorised(AUTH_TOKEN, roles))
            return null;
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
	public VacatureDetail getVacatureDetail(@RequestHeader String AUTH_TOKEN, @PathVariable long vacatureId) {
        String[] roles = {"Accountmanager", "Trainee"};
        if(!loginService.isAuthorised(AUTH_TOKEN, roles))
            return null;
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
