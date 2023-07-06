package nl.yc2306.recruitmentApp.distance;

import org.springframework.data.repository.CrudRepository;

public interface ILocatieRepository extends CrudRepository<Locatie, Long> {
    Locatie findByCity(String city);
}
