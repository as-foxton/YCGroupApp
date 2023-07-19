package nl.yc2306.recruitmentApp;

import nl.yc2306.recruitmentApp.DTOs.BeknoptCV;
import nl.yc2306.recruitmentApp.DTOs.FilterRequest;
import nl.yc2306.recruitmentApp.DTOs.VolledigCVMetNaamEnLocatie;
import nl.yc2306.recruitmentApp.Login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(maxAge = 3600)
public class CurriculumVitaeController {
    @Autowired
    private CurriculumVitaeService curriculumVitaeService;

    @Autowired
    private LoginService loginService;

    @RequestMapping("curriculum_vitae/all")
    public Iterable<CurriculumVitae> getCVs(){
        return curriculumVitaeService.getAll();
    }

    @RequestMapping("curriculum_vitae/beknopt")
    public Iterable<BeknoptCV> getCVsBeknopt(@RequestHeader String AUTH_TOKEN, @RequestBody FilterRequest filterparams){
        String[] pages = {"/showcvs.html"};
        if(!loginService.isAuthorised(AUTH_TOKEN, pages))
            return null;
        List<CurriculumVitae> cvs = curriculumVitaeService.getFiltered(filterparams);
        List<BeknoptCV> minimalCvs = cvs.stream().map(cv -> cv.maakBeknopt()).toList();
        return minimalCvs;
    }

    @RequestMapping("curriculum_vitae/find")
    public VolledigCVMetNaamEnLocatie getSpecific(@RequestHeader String AUTH_TOKEN, @RequestParam long id){
        String[] pages = {"/aanbiedingenpervacature.html"};
        if(!loginService.isAuthorised(AUTH_TOKEN, pages))
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
        String[] pages = {"/CreateCV.html"};
        if(!loginService.isAuthorised(AUTH_TOKEN, pages))
            return;
        Account user = loginService.findLoggedinUser(AUTH_TOKEN);
        cv.setPersoon(user);
        curriculumVitaeService.Save(cv);
    }

    @RequestMapping(method=RequestMethod.DELETE, value="curriculum_vitae/remove")
    public void remove(@RequestParam long id){
        curriculumVitaeService.delete(id);
    }

    @RequestMapping(method=RequestMethod.PUT, value="curriculum_vitae/update")
    public void update(@RequestParam long id, @RequestBody CurriculumVitae cv){
        CurriculumVitae current = curriculumVitaeService.getOne(id).get();
        current.setOmschrijving(cv.getOmschrijving());
        current.setWerkHistorie(cv.getWerkHistorie());
        current.setUitstroomRichting(cv.getUitstroomRichting());
        current.setSpecialiteit(cv.getSpecialiteit());
        curriculumVitaeService.Save(current);
    }
}
