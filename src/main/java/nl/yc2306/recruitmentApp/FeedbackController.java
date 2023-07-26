package nl.yc2306.recruitmentApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import nl.yc2306.recruitmentApp.DTOs.FeedbackItem;

@RestController
@CrossOrigin(maxAge = 3600)
public class FeedbackController {
	@Autowired
	public FeedbackService service;
	@Autowired
	public AccountService serviceAccount;
	@Autowired
	public AanbiedingService serviceAanbieding;
	@Autowired
	public CurriculumVitaeService serviceCv;
	
	// GET ALL Feedback
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
		newFeedback.setId(newFeedback.getId());
		newFeedback.setMening(newFeedback.getMening());
				
		service.Save(dbFeedback);
	}
	
	// DELETE
	@RequestMapping(method = RequestMethod.POST, value = "feedback/delete/{id}")
	public void DeleteFeedback(@PathVariable long id)
	{
		service.delete(id);
	}
	
	// GET list of feedbacks
	@RequestMapping("feedback/feedbacklist/{id}")
	public List<FeedbackItem> GetFeedbackList (@PathVariable long id)
	{
		Iterable<Feedback> list = service.GetAll();
		List<FeedbackItem> dtoList = new ArrayList<FeedbackItem>();
        Account user = new Account();
        
        if (serviceAccount.vindBijId(id).isPresent())
		{
        	// Get Account from Optional<Account>
			user = serviceAccount.vindBijId(id).get();
		}


		for (Feedback fb: list) 
		{
			FeedbackItem fItem = new FeedbackItem();
			
			// Verander rol op basis van rol van ingelogde gebruiker
        	switch(user.getRol())
            {
            	case "opdrachtgever": // Show only feedbacks of opdrachtgever
            		if (fb.getAanbieding().getVacature().getAccount().getId() == user.getId())
            		{
            			fItem.setId(fb.getId());
                		fItem.setAccountName(fb.getAanbieding().getCurriculumVitae().getPersoon().getNaam());
                		fItem.setLocatie(fb.getAanbieding().getCurriculumVitae().getPersoon().getLocatie());
                		fItem.setMening(fb.getMening());
                		fItem.setAangenomen(fb.getAanbieding().isAfgewezen());
                		
                		dtoList.add(fItem);
            		}
            		break;
            	case "trainee": // Show only feedbacks of trainees
            		if (fb.getAanbieding().getCurriculumVitae().getId() == user.getCurriculumVitae().getId())
            		{
            			fItem.setId(fb.getId());
                		fItem.setAccountName(user.getNaam());
                		fItem.setBedrijf(fb.getAanbieding().getVacature().getBedrijf());
                		fItem.setLocatie(fb.getAanbieding().getVacature().getLocatie());
                		fItem.setMening(fb.getMening());
                		fItem.setAangenomen(fb.getAanbieding().isAfgewezen());
                		
                		dtoList.add(fItem);
            		}
            		break;
        		default: // Show all feedbacks
        			fItem.setId(fb.getId());
            		fItem.setAccountName(user.getNaam());
            		fItem.setBedrijf(user.getBedrijf());
            		fItem.setMening(fb.getMening());
            		fItem.setAangenomen(fb.getAanbieding().isAfgewezen());
            		
            		dtoList.add(fItem);
        			break;
            }
        }

		return dtoList;
	}
}
