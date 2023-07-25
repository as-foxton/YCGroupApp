package nl.yc2306.recruitmentApp;

import java.util.ArrayList;
import java.util.List;

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

	public List<Aanbieding> getAanbiedingenVanVacature(long id, Account user){
		Vacature vacature = vacatureService.findVacatureById(id).get();;
		if(!vacature.getAccount().equals(user))
			return new ArrayList<>();

		return vacature.getAanbiedingen().stream()
				.filter(aanbieding -> !(aanbieding.isUitgenodigd() || aanbieding.isAfgewezen())).toList();
	}

	public List<Aanbieding> getUitgenodigdenVanVacature(long id, Account user){
		Vacature vacature = vacatureService.findVacatureById(id).get();;
		if(!vacature.getAccount().equals(user))
			return new ArrayList<>();

		return vacature.getAanbiedingen().stream()
				.filter(aanbieding -> aanbieding.isUitgenodigd() && !aanbieding.isAfgewezen()).toList();
	}
}
