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

	public void deleteVacature(Vacature vacature) {
		repository.delete(vacature);
	}
	
	public Optional<Vacature> findVacature(long id) {
		return repository.findById(id);
	}

}