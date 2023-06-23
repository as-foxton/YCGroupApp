package nl.yc2306.recruitmentApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CurriculumVitaeService {
        @Autowired
        private ICurriculumVitaeRepository curriculumVitaeRepository;

        public Iterable<CurriculumVitae> getAll(){
                return curriculumVitaeRepository.findAll();
        }

        public Optional<CurriculumVitae> getOne(long id){
                return curriculumVitaeRepository.findById(id);
        }

        public void Save(CurriculumVitae cv){
                curriculumVitaeRepository.save(cv);
        }

        public void delete(long id){
                curriculumVitaeRepository.deleteById(id);
        }
}
