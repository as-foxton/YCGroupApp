package nl.yc2306.recruitmentApp.Login;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountTokenService {

	@Autowired
	private IAccountTokenRepository repository;

	public AccountToken save(AccountToken accountToken) {
		return repository.save(accountToken);
	}

	public Optional<AccountToken> findByToken(String token) {
		return repository.findByToken(token);
	}
	
	public void delete(AccountToken accountToken) {
		repository.delete(accountToken);
	}
}
