package nl.yc2306.recruitmentApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(maxAge = 3600)
public class CurriculumVitaeController {
    @Autowired
    private CurriculumVitaeService curriculumVitaeService;

    @RequestMapping("curriculum_vitae/all")
    public Iterable<CurriculumVitae> getCVs(){
        return curriculumVitaeService.getAll();
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
        current.setPersonId(cv.getPersonId());
        current.setWerkHistorie(cv.getWerkHistorie());
        current.setUitstroomRichting(cv.getUitstroomRichting());
        current.setSpecialiteit(cv.getSpecialiteit());
        curriculumVitaeService.Save(current);
    }
}
