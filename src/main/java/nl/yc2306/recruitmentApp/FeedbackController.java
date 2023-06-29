package nl.yc2306.recruitmentApp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class FeedbackController {
	@Autowired
	public FeedbackService service;
	
	// GET ALL
	@RequestMapping("feedback/all")
	public Iterable<Feedback> GetAllFeedback()
	{
		return service.GetAll();
	}

	// GET
	@RequestMapping("feedback/find/{id}")
	public Optional<Feedback> FindFeedback(@PathVariable long id)
	{
		return service.Find(id);
	}
	
	// SAVE
	@RequestMapping(method = RequestMethod.POST, value = "feedback/save")
	public void SaveFeedback(@RequestBody Feedback feedback)
	{
		service.Save(feedback);
	}
	@RequestMapping(method = RequestMethod.POST, value = "feedback/savetocv")
	public void SaveFeedbackToCV(@RequestBody Feedback feedback, @RequestParam long cvId)
	{
		service.SaveToCV(feedback, cvId);
	}

	// UPDATE
	@RequestMapping(method = RequestMethod.PUT, value = "feedback/update/{id}")
	public void UpdateFeedback(@RequestBody Feedback newFeedback, @PathVariable long id)
	{
		Optional<Feedback> optional = service.Find(id);
		if (optional.isEmpty())
		{
			return;
		}
		
		Feedback dbFeedback = optional.get();
		newFeedback.setPersoonNr(newFeedback.getPersoonNr());
		newFeedback.setCvNr(newFeedback.getCvNr());
		newFeedback.setMening(newFeedback.getMening());
		newFeedback.setAangenomen(newFeedback.getAangenomen());
				
		service.Save(dbFeedback);
	}
	
	// DELETE
	@RequestMapping(method = RequestMethod.POST, value = "feedback/delete/{id}")
	public void DeleteFeedback(@PathVariable long id)
	{
		service.delete(id);
	}
}
