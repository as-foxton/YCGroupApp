package nl.yc2306.recruitmentApp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {
	@Autowired
	private IFeedbackRepository repo;

	public Iterable<Feedback> GetAll() {
		return repo.findAll();
	}
	
	public Feedback Save(Feedback feedback)
	{
		return repo.save(feedback);
	}

	public Optional<Feedback> Find(long id) {
		return repo.findById(id);
	}
	
	public void delete(long id)
	{
		repo.deleteById(id);
	}
}
