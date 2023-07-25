package nl.yc2306.recruitmentApp.Login;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface IAccountTokenRepository extends CrudRepository<AccountToken, Long> {

	Optional<AccountToken> findByToken(String token);

}
