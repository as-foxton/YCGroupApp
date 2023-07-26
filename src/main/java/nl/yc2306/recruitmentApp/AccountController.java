package nl.yc2306.recruitmentApp;

import java.util.ArrayList;
import java.util.Optional;

import nl.yc2306.recruitmentApp.DTOs.NoPWAccount;
import nl.yc2306.recruitmentApp.Login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(maxAge = 3600)

public class AccountController {
	@Autowired
	private AccountService service;

	@Autowired
	private LoginService loginService;
	// get all
	@RequestMapping ("account/all")
	public Iterable<NoPWAccount>all(@RequestHeader String AUTH_TOKEN){
		String[] roles = {"Administrator"};
		if(!loginService.isAuthorised(AUTH_TOKEN, roles))
			return null;
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
	public Optional<Account> find (@RequestHeader String AUTH_TOKEN, @PathVariable long id) {
		String[] roles = {"Administrator"};
		if(!loginService.isAuthorised(AUTH_TOKEN, roles))
			return null;
		return service.vindBijId(id);
	}

	
	// maak aan 
	@RequestMapping(method = RequestMethod.POST, value = "account/create")
	public void maakAan (@RequestHeader String AUTH_TOKEN, @RequestBody Account account) {
		String[] roles = {"Administrator"};
		if(!loginService.isAuthorised(AUTH_TOKEN, roles))
			return;
		service.voegInAccount(account);
		
	}
	// update
	@RequestMapping(method=RequestMethod.PUT, value= "account/update/{id}")
	public void update(@RequestHeader String AUTH_TOKEN, @PathVariable long id, @RequestBody Account newAccount) {
		String[] roles = {"Administrator"};
		if(!loginService.isAuthorised(AUTH_TOKEN, roles))
			return;
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
	public void delete (@RequestHeader String AUTH_TOKEN, @PathVariable long id){
		String[] roles = {"Administrator"};
		if(!loginService.isAuthorised(AUTH_TOKEN, roles))
			return;
		service.deleteAccount(id);
		return;
		
	}
}
