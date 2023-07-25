package nl.yc2306.recruitmentApp;

import java.util.ArrayList;
import java.util.Optional;

import nl.yc2306.recruitmentApp.DTOs.NoPWAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(maxAge = 3600)

public class AccountController {
	@Autowired
	private AccountService service;
	// get all
	@RequestMapping ("account/all")
	public Iterable<NoPWAccount>all(){
		Iterable<Account> accounts = service.vindAlleAccounts();
		ArrayList<NoPWAccount> response = new ArrayList<>();
		for(Account account : accounts){
			NoPWAccount dto = new NoPWAccount();
			dto.setId(account.getId());
			dto.setNaam(account.getNaam());
			dto.setEmail(account.getEmail());
			dto.setRol(account.getRol());
			dto.setBedrijf(account.getBedrijf());
			dto.setLocatie(account.getLocatie());
			response.add(dto);
		}
		return response;
	}
	// vind bij id

	@RequestMapping("account/{id}")
	public Optional<Account> find (@PathVariable long id) {
		return service.vindBijId(id);
	}

	
	// maak aan 
	@RequestMapping(method = RequestMethod.POST, value = "account/create")
	public void maakAan (@RequestBody Account account) {
		service.voegInAccount(account);
		
	}
	// update
	@RequestMapping(method=RequestMethod.PUT, value= "account/update/{id}")
	public void update(@PathVariable long id, @RequestBody Account newAccount) {
		//vind huidig account
		Optional<Account> optional = service.vindDmvId(id);
		// huidig account aanpassen
		Account dbAccount= optional.get();
		dbAccount.setBedrijf(newAccount.getBedrijf());
		dbAccount.setEmail(newAccount.getEmail());
		dbAccount.setLocatie(newAccount.getLocatie());
		dbAccount.setNaam(newAccount.getNaam());
		dbAccount.setRol(newAccount.getRol());
		dbAccount.setWachtwoord(newAccount.getWachtwoord());
		// opslaan
		service.save(dbAccount);
		
	}
	// delete
	@RequestMapping("account/delete/{id}")
	public void delete (@PathVariable long id){
		service.deleteAccount(id);
		return;
		
	}
}
