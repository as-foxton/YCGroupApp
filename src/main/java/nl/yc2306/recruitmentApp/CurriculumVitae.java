package nl.yc2306.recruitmentApp;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class CurriculumVitae {
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
    @OneToOne
    private Account persoon;

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
        omschrijving = omschrijving;
    }

    public String getWerkHistorie() {
        return werkHistorie;
    }

    public void setWerkHistorie(String werkHistorie) {
        this.werkHistorie = werkHistorie;
    }
}
