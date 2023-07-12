package nl.yc2306.recruitmentApp;

import java.util.Optional;

import nl.yc2306.recruitmentApp.DTOs.FilterRequest;
import nl.yc2306.recruitmentApp.distance.DistanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class VacatureService {
	@Autowired
	private IVacatureRepository repository;

	@Autowired
	DistanceService distanceService;
	
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

	public Iterable<Vacature> getFiltered(FilterRequest filterparams){
		Iterable<Vacature> vacatures;
		if(!filterparams.getUitstroomRichting().equals("alle"))
			vacatures = repository.findAllByUitstroomRichting(filterparams.getUitstroomRichting());
		else
			vacatures = vindAlleVacatures();
		if(!filterparams.getPlaats().equals("")){
			return (Iterable<Vacature>)distanceService.filterDistance(vacatures, filterparams.getPlaats(), filterparams.getMaxAfstand());
		}
		return vacatures;
	}
}