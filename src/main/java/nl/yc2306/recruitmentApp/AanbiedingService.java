package nl.yc2306.recruitmentApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AanbiedingService {

	@Autowired
	private IAanbiedingRepository repo;
	
	public Aanbieding create(Aanbieding aanbieding) {
		// Validatie 
		
		Aanbieding newAanbieding = repo.save(aanbieding);
		return newAanbieding;
	}
	
}
