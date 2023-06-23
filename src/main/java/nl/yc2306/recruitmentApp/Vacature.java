package nl.yc2306.recruitmentApp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Vacature {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long vactureId;
	
	@Column(nullable = false)
	private int personid;
	
	@Column(length = 100, nullable = false)
	private String bedrijf;
	
	@Column(length = 100, nullable = false)
	private String locatie;
	
	@Column(length = 150, nullable = false)
	private String omschrijving;
		
	@Column(length = 100, nullable = false)
	private String uitstroomrichting;
	
	@Column(length = 200, nullable = false)
	private String functie;

	public long getVactureId() {
		return vactureId;
	}

	public void setVactureId(long vactureId) {
		this.vactureId = vactureId;
	}

	public int getPersonid() {
		return personid;
	}

	public void setPersonid(int personid) {
		this.personid = personid;
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

	public String getUitstroomrichting() {
		return uitstroomrichting;
	}

	public void setUitstroomrichting(String uitstroomrichting) {
		this.uitstroomrichting = uitstroomrichting;
	}

	public String getFunctie() {
		return functie;
	}

	public void setFunctie(String functie) {
		this.functie = functie;
	}
	
}
	