package nl.yc2306.recruitmentApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import nl.yc2306.recruitmentApp.DTOs.FeedbackItem;
import nl.yc2306.recruitmentApp.Login.LoginService;

@RestController
@CrossOrigin(maxAge = 3600)
public class FeedbackController {
	@Autowired
	public FeedbackService service;
	
	@Autowired
	public LoginService loginService;
	
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
	
	// GET
	@RequestMapping("feedback/findaccount/{id}")
	public Optional<Account> FindAccount(@PathVariable long id)
	{
		return service.FindAccount(id);
	}
	
	// SAVE
	@RequestMapping(method = RequestMethod.POST, value = "feedback/save")
	public void SaveFeedback(@RequestHeader String AUTH_TOKEN, @RequestBody Feedback feedback)
	{
		Account account = loginService.findLoggedinUser(AUTH_TOKEN);
		if (account != null) {
			feedback.setAccount(account);
			service.Save(feedback);
		}
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
        String rol = ""; // rollen filter voor feedbacks
        
        if (service.FindAccount(id).isPresent())
		{
        	// Get Account from Optional<Account>
			user = service.FindAccount(id).get();
		}
        
        rol = user.getRol();
        
		for (Feedback fb: list) {
			FeedbackItem fItem = new FeedbackItem();

			// Verander rol op basis van rol van ingelogde gebruiker
        	switch(rol)
            {
//            	case "administrator": // Show all feedbacks
//            		break;
//            	case "accountmanager": // Show all feedbacks
//            		break;
            	case "opdrachtgever": // Show only feedbacks of trainees
            		rol = "trainee";
            		break;
            	case "trainee": // Show only feedbacks of opdrachtgever
            		rol = "opdrachtgever";
            		break;
        		default: // Show all feedbacks
        			rol = "";
        			break;
            }
        	
        	if (fb.getAccount().getRol() == rol)
        	{
        		fItem.setId(fb.getId());
                fItem.setAccountName(fb.getAccount().getNaam());
                fItem.setBedrijf(fb.getAccount().getBedrijf());
                fItem.setMening(fb.getMening());
                // Get "aangenomen" van Aanbieding Class
                //fItem.setAangenomen();
        	}
        	else
        	{
        		fItem.setId(fb.getId());
                fItem.setAccountName(fb.getAccount().getNaam());
                fItem.setBedrijf(fb.getAccount().getBedrijf());
                fItem.setMening(fb.getMening());
                // Get "aangenomen" van Aanbieding Class
                //fItem.setAangenomen();
        	}

            dtoList.add(fItem);
        }

		return dtoList;
	}
}
