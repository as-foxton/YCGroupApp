package nl.yc2306.recruitmentApp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
	@Autowired
	private IAccountRepository repo;
	// vind alles
	public Iterable<Account> vindAlleAccounts() {
		return repo.findAll();
	}
	// vind bij id
	public Optional<Account> vindBijId(long id) {
		return repo.findById(id);
	}
	// maak aan
	public Account voegInAccount(Account account) {
		return repo.save(account);
	}
	// update
	public Optional<Account> vindDmvId(long id) {
		return repo.findById(id);
	}

	public void save(Account dbAccount) {
		repo.save(dbAccount);
		
	}
	// delete
	public void deleteAccount(long id) {
		repo.deleteById(id);
		
	}

}
