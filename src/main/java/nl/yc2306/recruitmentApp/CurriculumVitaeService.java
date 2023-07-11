package nl.yc2306.recruitmentApp;
import nl.yc2306.recruitmentApp.DTOs.CVFilterRequest;
import nl.yc2306.recruitmentApp.distance.DistanceService;
import nl.yc2306.recruitmentApp.distance.LocatieNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class CurriculumVitaeService {
        @Autowired
        private ICurriculumVitaeRepository curriculumVitaeRepository;
        
        @Autowired
        private IAccountRepository accountRepository;

        @Autowired
        DistanceService distanceService;

        public Iterable<CurriculumVitae> getAll(){
                return curriculumVitaeRepository.findAll();
        }

        public Iterable<CurriculumVitae> getFiltered(CVFilterRequest filterparams){
                Iterable<CurriculumVitae> cvs;
                if(!filterparams.getUitstroomRichting().equals("alle"))
                        cvs = curriculumVitaeRepository.findAllByUitstroomRichting(filterparams.getUitstroomRichting());
                else
                        cvs = getAll();
                if(!filterparams.getPlaats().equals("")){
                        return filterDistance(cvs, filterparams.getPlaats(), filterparams.getMaxAfstand());
                }
                return cvs;
        }

        private Iterable<CurriculumVitae> filterDistance(Iterable<CurriculumVitae> cvs, String plaats, int maxAfstand){
                ArrayList<CurriculumVitae> response = new ArrayList<>();
                for(CurriculumVitae cv :cvs){
                        try {
                                double afstand = distanceService.calculateDistance(plaats, cv.getPersoon().getLocatie());
                                if(afstand <= maxAfstand)
                                        response.add(cv);
                        }
                        catch (LocatieNotFoundException e){
                                System.out.printf("een van de plaatsen is niet gevonden: %s, %s",plaats,cv.getPersoon().getLocatie());
                        }
                }
                return response;
        }

        public Optional<CurriculumVitae> getOne(long id){
                return curriculumVitaeRepository.findById(id);
        }

        public void Save(CurriculumVitae cv){
        	Account user = accountRepository.findById(cv.getPersoon().getId()).get();
        	cv.setPersoon(user);
            curriculumVitaeRepository.save(cv);
            
        }

        public void delete(long id){
                curriculumVitaeRepository.deleteById(id);
        }
}
