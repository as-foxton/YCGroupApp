package nl.yc2306.recruitmentApp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class VacatureService {
	@Autowired
	private IVacatureRepository repository;
	
	
	public Iterable<Vacature> vindAlleVacatures() {
		return repository.findAll();		
	}
	
	public Optional<Vacature> findVacatureById(long vacatureId) {
		return repository.findById(vacatureId);
	}
	
	public void saveVacature(Vacature vacature) {
	    // Use your data access object or repository to save the vacature to the database
	    repository.save(vacature);
	}


	public void deleteVacature(long vacatureId) {
		repository.deleteById(vacatureId);
	}
	}
