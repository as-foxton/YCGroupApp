package nl.yc2306.recruitmentApp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.yc2306.recruitmentApp.DTOs.FilterRequest;
import nl.yc2306.recruitmentApp.distance.DistanceService;

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

        public List<CurriculumVitae> getFiltered(FilterRequest filterparams){
                List<CurriculumVitae> cvs = new ArrayList<>();
                if(!filterparams.getUitstroomRichting().equals("alle"))
                        curriculumVitaeRepository.findAllByUitstroomRichting(filterparams.getUitstroomRichting()).forEach(cvs::add);
                else
                        getAll().forEach(cvs::add);
                if(!filterparams.getPlaats().equals("")){
                        return (List<CurriculumVitae>)distanceService.filterDistance(cvs, filterparams.getPlaats(), filterparams.getMaxAfstand());
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
