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
	
	public Optional<Vacature> enigeVacature(long id) {
		return repository.findById(id);
	}
	
	public void saveVacature(Vacature vacature) {
		repository.save(vacature);		
	}

<<<<<<< HEAD
	public void deleteVacature(long id) {
		repository.deleteById(id);
=======
	public void deleteVacature(long vacature) {
		repository.deleteById(vacature);
>>>>>>> 529e31068ef30f2284898b6b6b666ce6cf5fea71
	}
	
	public Optional<Vacature> findVacature(long id) {
		return repository.findById(id);
	}
	

}
