package nl.yc2306.recruitmentApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

	public List<CurriculumVitae> getAanbiedingenVanVacature(long id){
		return vacatureService.findVacatureById(id).get().getAanbiedingen().stream().map(aanbieding -> aanbieding.getCurriculumVitae()).toList();
	}
}
