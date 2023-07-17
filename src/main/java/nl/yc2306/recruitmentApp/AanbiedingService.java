package nl.yc2306.recruitmentApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AanbiedingService {

	@Autowired
	private IAanbiedingRepository repo;

	@Autowired
	private VacatureService vacatureService;
	
	public Aanbieding create(Aanbieding aanbieding) {
		// Validatie 
		
		Aanbieding newAanbieding = repo.save(aanbieding);
		return newAanbieding;
	}

	public Iterable<Aanbieding> getAanbiedingenVanVacature(long id){
		return null;
	}
}
