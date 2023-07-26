package nl.yc2306.recruitmentApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
				.filter(aanbieding -> !(aanbieding.isUitgenodigd() || aanbieding.isAfgewezen() || aanbieding.isAangenomen())).toList();
	}

	public List<Aanbieding> getUitgenodigdenVanVacature(long id, Account user){
		Vacature vacature = vacatureService.findVacatureById(id).get();;
		if(!vacature.getAccount().equals(user))
			return new ArrayList<>();

		return vacature.getAanbiedingen().stream()
				.filter(aanbieding -> aanbieding.isUitgenodigd() && !(aanbieding.isAfgewezen() || aanbieding.isAangenomen())).toList();
	}

	public List<Aanbieding> getOnbeoordeeldVanVacature(long id, Account user){
		Vacature vacature = vacatureService.findVacatureById(id).get();;
		if(!vacature.getAccount().equals(user))
			return new ArrayList<>();

		return vacature.getAanbiedingen().stream()
				.filter(aanbieding -> aanbieding.isUitgenodigd() && (aanbieding.isAfgewezen() || aanbieding.isAangenomen()))
				//.filter(aanbieding -> aanbieding.getFeedback().stream().anyMatch(feedback -> !feedback.getAccount().equals(user)))
				.toList();
	}

	public List<Aanbieding> getAanbiedingenAanTrainee(Account user){
		return user.getCurriculumVitae().getAanbiedingen().stream()
				.filter(aanbieding -> aanbieding.isUitgenodigd() && !(aanbieding.isAfgewezen() || aanbieding.isAangenomen())).toList();
	}

	public List<Aanbieding> getOnbeoordeeldDoorTrainee(Account user){
		return user.getCurriculumVitae().getAanbiedingen().stream()
				.filter(aanbieding -> aanbieding.isUitgenodigd() && (aanbieding.isAfgewezen() || aanbieding.isAangenomen()))
				//.filter(aanbieding -> aanbieding.getFeedback().stream().anyMatch(feedback -> !feedback.getAccount().equals(user)))
				.toList();
	}

	public void verstuurUitnodiging(Account user, long aanbiedingId){
		Optional<Aanbieding> a = repo.findById(aanbiedingId);
		if(!a.isPresent())
			return;
		Aanbieding aanbieding = a.get();
		if(!aanbieding.getVacature().getAccount().equals(user))
			return;
		aanbieding.setUitgenodigd(true);
		repo.save(aanbieding);
	}

	public void wijsAf(Account user, long aanbiedingId){
		Optional<Aanbieding> a = repo.findById(aanbiedingId);
		if(!a.isPresent())
			return;
		Aanbieding aanbieding = a.get();
		if(!aanbieding.getVacature().getAccount().equals(user))
			return;
		aanbieding.setAfgewezen(true);
		repo.save(aanbieding);
	}
}
