package nl.yc2306.recruitmentApp;

import nl.yc2306.recruitmentApp.DTOs.BeknoptCV;
import nl.yc2306.recruitmentApp.DTOs.FilterRequest;
import nl.yc2306.recruitmentApp.Login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        Iterable<CurriculumVitae> cvs = curriculumVitaeService.getFiltered(filterparams);
        List<BeknoptCV> minimalCvs = new ArrayList<BeknoptCV>();
        for (CurriculumVitae cv: cvs) {
            BeknoptCV bcv = new BeknoptCV();
            bcv.setId(cv.getId());
            bcv.setNaam(cv.getPersoon().getNaam());
            bcv.setLocatie(cv.getPersoon().getLocatie());
            bcv.setUitstroomRichting(cv.getUitstroomRichting());
            minimalCvs.add(bcv);
        }
        return minimalCvs;
    }

    @RequestMapping("curriculum_vitae/find")
    public Optional<CurriculumVitae> getSpecific(@RequestParam long id){
        return curriculumVitaeService.getOne(id);
    }

    @RequestMapping(method=RequestMethod.POST, value="curriculum_vitae/add")
    public void add(@RequestBody CurriculumVitae cv){
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
