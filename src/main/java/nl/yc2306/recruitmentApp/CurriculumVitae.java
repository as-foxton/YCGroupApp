package nl.yc2306.recruitmentApp;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import nl.yc2306.recruitmentApp.DTOs.BeknoptCV;
import nl.yc2306.recruitmentApp.distance.HasLocatie;

@Entity
public class CurriculumVitae  implements HasLocatie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;    
    @Column(length = 30, nullable = false)
    private String uitstroomRichting;
    @Column(length = 30, nullable = false)
    private String specialiteit;
    @Column(length = 500, nullable = false)
    private String omschrijving;
    @Column(length = 500, nullable = false)
    private String werkHistorie;

    @JsonIgnore
    @OneToOne
    private Account persoon;
    @JsonIgnore
	@OneToMany(mappedBy = "curriculumVitae")
	private List<Aanbieding> aanbiedingen;

    public Account getPersoon() {
		return persoon;
	}

	public void setPersoon(Account persoon) {
		this.persoon = persoon;
	}

	public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUitstroomRichting() {
        return uitstroomRichting;
    }

    public void setUitstroomRichting(String uitstroomRichting) {
        this.uitstroomRichting = uitstroomRichting;
    }

    public String getSpecialiteit() {
        return specialiteit;
    }

    public void setSpecialiteit(String specialiteit) {
        this.specialiteit = specialiteit;
    }

    public String getOmschrijving() {
        return omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        this.omschrijving = omschrijving;
    }

    public String getWerkHistorie() {
        return werkHistorie;
    }

    public void setWerkHistorie(String werkHistorie) {
        this.werkHistorie = werkHistorie;
    }

    public String getLocatie(){
        return persoon.getLocatie();
    }

    public List<Aanbieding> getAanbiedingen() {
        return aanbiedingen;
    }

    public void setAanbiedingen(List<Aanbieding> aanbiedingen) {
        this.aanbiedingen = aanbiedingen;
    }

    public BeknoptCV maakBeknopt(){
        BeknoptCV beknopt = new BeknoptCV();
        beknopt.setId(id);
        beknopt.setLocatie(getLocatie());
        beknopt.setNaam(getPersoon().getNaam());
        beknopt.setUitstroomRichting(uitstroomRichting);
        return beknopt;
    }
}
