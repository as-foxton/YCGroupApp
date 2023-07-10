package nl.yc2306.recruitmentApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurriculumVitaeService {
        @Autowired
        private ICurriculumVitaeRepository curriculumVitaeRepository;
        
        @Autowired
        private IAccountRepository accountRepository;

        public Iterable<CurriculumVitae> getAll(){
                return curriculumVitaeRepository.findAll();
        }

        public Optional<CurriculumVitae> getOne(long id){
                return curriculumVitaeRepository.findById(id);
        }

        public void Save(CurriculumVitae cv){
        	Account user = accountRepository.findById(cv.getPersoon().getPersoons_nr()).get();
        	cv.setPersoon(user);
            curriculumVitaeRepository.save(cv);
            
        }

        public void delete(long id){
                curriculumVitaeRepository.deleteById(id);
        }
}
