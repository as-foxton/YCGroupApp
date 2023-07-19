package nl.yc2306.recruitmentApp;


import org.springframework.data.repository.CrudRepository;

public interface IVacatureRepository extends CrudRepository<Vacature, Long>{
    Iterable<Vacature> findAllByUitstroomRichting(String uitstroomRichting);

	Iterable<Vacature> findByAccountId(long account);



}
