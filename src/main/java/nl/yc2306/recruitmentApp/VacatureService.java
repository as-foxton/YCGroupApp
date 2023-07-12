package nl.yc2306.recruitmentApp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class VacatureService {
	@Autowired
	private IVacatureRepository repository;
	
	@Autowired
    private IAccountRepository accountRepository;
	
	
	public Iterable<Vacature> vindAlleVacatures() {
		return repository.findAll();		
	}
	
	public Optional<Vacature> findVacatureById(long vacatureId) {
		return repository.findById(vacatureId);
	}
	
	public void saveVacature(Vacature vacature) {
		System.out.println(vacature + "TEST");
	    // Use your data access object or repository to save the vacature to the database
	    Account user = accountRepository.findById(vacature.getAccount().getId()).get();
    	vacature.setAccount(user);
        
        repository.save(vacature);
	}


	public void deleteVacature(long vacatureId) {
		repository.deleteById(vacatureId);
	}
	}
