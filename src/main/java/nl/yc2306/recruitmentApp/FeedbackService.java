package nl.yc2306.recruitmentApp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {
	@Autowired
	private IFeedbackRepository repo;
	@Autowired
	private CurriculumVitaeService cvService;
	@Autowired
	private AccountService accountService;
	

	public Iterable<Feedback> GetAll() {
		return repo.findAll();
	}
	
	public Feedback Save(Feedback feedback)
	{
		return repo.save(feedback);
	}

	public Feedback SaveToCV(Feedback feedback, long cvId){
		Feedback withId = Save(feedback);
		CurriculumVitae cv = cvService.getOne(cvId).get();
		cvService.Save(cv);
		return  withId;
	}

	public Optional<Feedback> Find(long id) {
		return repo.findById(id);
	}
	
	public void delete(long id)
	{
		repo.deleteById(id);
	}
	
	public Optional<Account> FindAccount(long id) {
		return accountService.vindBijId(id);
	}
}
