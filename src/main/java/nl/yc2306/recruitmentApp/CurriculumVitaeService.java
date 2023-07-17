package nl.yc2306.recruitmentApp;
import nl.yc2306.recruitmentApp.DTOs.FilterRequest;
import nl.yc2306.recruitmentApp.distance.DistanceService;
import nl.yc2306.recruitmentApp.distance.HasLocatie;
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

        public Iterable<CurriculumVitae> getFiltered(FilterRequest filterparams){
                Iterable<CurriculumVitae> cvs;
                if(!filterparams.getUitstroomRichting().equals("alle"))
                        cvs = curriculumVitaeRepository.findAllByUitstroomRichting(filterparams.getUitstroomRichting());
                else
                        cvs = getAll();
                if(!filterparams.getPlaats().equals("")){
                        return (Iterable<CurriculumVitae>)distanceService.filterDistance(cvs, filterparams.getPlaats(), filterparams.getMaxAfstand());
                }
                return cvs;
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
