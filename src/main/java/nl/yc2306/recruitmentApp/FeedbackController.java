package nl.yc2306.recruitmentApp;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nl.yc2306.recruitmentApp.DTOs.SaveFeedbackDTO;
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
	public AccountService serviceAccount;
	@Autowired
	public AanbiedingService serviceAanbieding;
	@Autowired
	public CurriculumVitaeService serviceCv;
	
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
	
	// SAVE
	@RequestMapping(method = RequestMethod.POST, value = "feedback/save")
	public void SaveFeedback(@RequestHeader String AUTH_TOKEN, @RequestBody SaveFeedbackDTO feedback)
	{
		String[] roles = {"Trainee", "Opdrachtgever"};
		if(!loginService.isAuthorised(AUTH_TOKEN, roles))
			return;
		Account account = loginService.findLoggedinUser(AUTH_TOKEN);
		if (account != null) {
			Feedback f = new Feedback();
			f.setMening(feedback.getMening());
			f.setAccount(account);
			f.setAanbieding(serviceAanbieding.find(feedback.getAanbiedingId()).get());
			service.Save(f);
		}
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
	public List<FeedbackItem> GetFeedbackList (@RequestHeader String AUTH_TOKEN, @PathVariable long id)
	{
		String[] roles = {"Accountmanager", "Trainee", "Opdrachtgever"};
		if(!loginService.isAuthorised(AUTH_TOKEN, roles))
			return null;

		Iterable<Feedback> list;
		List<FeedbackItem> dtoList = new ArrayList<FeedbackItem>();
        Account user = loginService.findLoggedinUser(AUTH_TOKEN);

		if(user.getRol().toLowerCase().equals("accountmanager"))
			list = service.GetAll();
		else
			list = user.getFeedbacks();

		for (Feedback fb: list) 
		{
			FeedbackItem fItem = new FeedbackItem();
			
			// Verander rol op basis van rol van ingelogde gebruiker
        	switch(user.getRol().toLowerCase())
            {
            	case "opdrachtgever": // Show only feedbacks of opdrachtgever
					fItem.setId(fb.getId());
					fItem.setAccountName(fb.getAanbieding().getCurriculumVitae().getPersoon().getNaam());
					fItem.setLocatie(fb.getAanbieding().getCurriculumVitae().getPersoon().getLocatie());
					fItem.setMening(fb.getMening());
					fItem.setAangenomen(fb.getAanbieding().isAangenomen());

					dtoList.add(fItem);
            		break;
            	case "trainee": // Show only feedbacks of trainees
					fItem.setId(fb.getId());
					fItem.setAccountName(user.getNaam());
					fItem.setBedrijf(fb.getAanbieding().getVacature().getBedrijf());
					fItem.setLocatie(fb.getAanbieding().getVacature().getLocatie());
					fItem.setMening(fb.getMening());
					fItem.setAangenomen(fb.getAanbieding().isAangenomen());

					dtoList.add(fItem);
            		break;
        		default: // Show all feedbacks
        			fItem.setId(fb.getId());
					fItem.setRol(fb.getAccount().getRol());
            		fItem.setAccountName(fb.getAanbieding().getCurriculumVitae().getPersoon().getNaam());
            		fItem.setBedrijf(fb.getAanbieding().getVacature().getBedrijf());
					fItem.setFunctie(fb.getAanbieding().getVacature().getFunctie());
					fItem.setLocatie(fb.getAanbieding().getVacature().getLocatie());
            		fItem.setMening(fb.getMening());
            		fItem.setAangenomen(fb.getAanbieding().isAangenomen());
            		
            		dtoList.add(fItem);
        			break;
            }
        }

		return dtoList;
	}
}
