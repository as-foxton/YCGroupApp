package nl.yc2306.recruitmentApp;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import nl.yc2306.recruitmentApp.distance.HasLocatie;

@Entity
public class Vacature implements HasLocatie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(length = 100, nullable = false)
	private String bedrijf;
	
	@Column(length = 100, nullable = false)
	private String locatie;
	
	@Column(length = 150, nullable = false)
	private String omschrijving;
		
	@Column(length = 100, nullable = false)
	private String uitstroomRichting;
	
	@Column(length = 200, nullable = false)
	private String functie;

	@JsonIgnore
	@ManyToOne()
	private Account account;

	@JsonIgnore
	@OneToMany(mappedBy = "vacature", fetch = FetchType.LAZY)
	private List<Aanbieding> aanbiedingen;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getBedrijf() {
		return bedrijf;
	}

	public void setBedrijf(String bedrijf) {
		this.bedrijf = bedrijf;
	}

	public String getLocatie() {
		return locatie;
	}

	public void setLocatie(String locatie) {
		this.locatie = locatie;
	}

	public String getOmschrijving() {
		return omschrijving;
	}

	public void setOmschrijving(String omschrijving) {
		this.omschrijving = omschrijving;
	}

	public String getUitstroomRichting() {
		return uitstroomRichting;
	}

	public void setUitstroomRichting(String uitstroomRichting) {
		this.uitstroomRichting = uitstroomRichting;
	}

	public String getFunctie() {
		return functie;
	}

	public void setFunctie(String functie) {
		this.functie = functie;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public List<Aanbieding> getAanbiedingen() {
		return aanbiedingen;
	}

	public void setAanbiedingen(List<Aanbieding> aanbiedingen) {
		this.aanbiedingen = aanbiedingen;
	}
}
	