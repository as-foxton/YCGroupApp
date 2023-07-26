package nl.yc2306.recruitmentApp;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import nl.yc2306.recruitmentApp.DTOs.BeknoptCV;
import nl.yc2306.recruitmentApp.DTOs.CVUpdate;
import nl.yc2306.recruitmentApp.DTOs.FilterRequest;
import nl.yc2306.recruitmentApp.DTOs.VolledigCVMetNaamEnLocatie;
import nl.yc2306.recruitmentApp.Login.LoginService;

@RestController
@CrossOrigin(maxAge = 3600)
public class CurriculumVitaeController {
    @Autowired
    private CurriculumVitaeService curriculumVitaeService;

    @Autowired
    private LoginService loginService;

    @RequestMapping("curriculum_vitae/beknopt")
    public Iterable<BeknoptCV> getCVsBeknopt(@RequestHeader String AUTH_TOKEN, @RequestBody FilterRequest filterparams){
        String[] roles = {"Accountmanager"};
        if(!loginService.isAuthorised(AUTH_TOKEN, roles))
            return null;
        List<CurriculumVitae> cvs = curriculumVitaeService.getFiltered(filterparams);
        List<BeknoptCV> minimalCvs = cvs.stream().map(cv -> cv.maakBeknopt()).toList();
        return minimalCvs;
    }

    @RequestMapping("curriculum_vitae/find")
    public VolledigCVMetNaamEnLocatie getSpecific(@RequestHeader String AUTH_TOKEN, @RequestParam long id){
        String[] roles = {"Opdrachtgever"};
        if(!loginService.isAuthorised(AUTH_TOKEN, roles))
            return null;
        Optional<CurriculumVitae> result = curriculumVitaeService.getOne(id);
        if(result.isEmpty())
            return null;
        CurriculumVitae cv = result.get();
        VolledigCVMetNaamEnLocatie response = new VolledigCVMetNaamEnLocatie();
        response.setLocatie(cv.getLocatie());
        response.setNaam(cv.getPersoon().getNaam());
        response.setOmschrijving(cv.getOmschrijving());
        response.setSpecialiteit(cv.getSpecialiteit());
        response.setWerkHistorie(cv.getWerkHistorie());
        response.setUitstroomRichting(cv.getUitstroomRichting());
        return response;
    }

    @RequestMapping(method=RequestMethod.POST, value="curriculum_vitae/add")
    public void add(@RequestHeader String AUTH_TOKEN, @RequestBody CurriculumVitae cv){
        String[] roles = {"Trainee"};
        if(!loginService.isAuthorised(AUTH_TOKEN, roles))
            return;
        Account user = loginService.findLoggedinUser(AUTH_TOKEN);
        if(user.getCurriculumVitae() == null){
            cv.setPersoon(user);
            curriculumVitaeService.Save(cv);
        }else{
            CurriculumVitae updatecv = user.getCurriculumVitae();
            updatecv.setSpecialiteit(cv.getSpecialiteit());
            updatecv.setUitstroomRichting(cv.getUitstroomRichting());
            updatecv.setOmschrijving(cv.getOmschrijving());
            updatecv.setWerkHistorie(cv.getWerkHistorie());
            curriculumVitaeService.Save(updatecv);
        }
    }

    @GetMapping("curriculum_vitae/mine")
    public CurriculumVitae getMyCv(@RequestHeader String AUTH_TOKEN){
        String[] roles = {"Trainee"};
        if(!loginService.isAuthorised(AUTH_TOKEN, roles))
            return null;
        Account user = loginService.findLoggedinUser(AUTH_TOKEN);
        return user.getCurriculumVitae();
    }
}
