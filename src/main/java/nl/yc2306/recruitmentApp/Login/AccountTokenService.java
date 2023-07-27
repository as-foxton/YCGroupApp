package nl.yc2306.recruitmentApp.Login;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class AccountTokenService {

	@Autowired
	private IAccountTokenRepository repository;

	public AccountToken save(AccountToken accountToken) throws TokenInUseException{
		try{
			return repository.save(accountToken);
		}catch(DataAccessException e){
			AccountToken existingToken = findByToken(accountToken.getToken()).get();
			if(existingToken.getExpiresAt().isBefore(LocalDateTime.now())){
				delete(existingToken);
				return repository.save(accountToken);
			}else{
				throw new TokenInUseException(accountToken.getToken());
			}
		}
	}

	public Optional<AccountToken> findByToken(String token) {
		return repository.findByToken(token);
	}
	
	public void delete(AccountToken accountToken) {
		repository.delete(accountToken);
	}
}
