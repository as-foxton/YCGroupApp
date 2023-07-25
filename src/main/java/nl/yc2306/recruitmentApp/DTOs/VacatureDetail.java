package nl.yc2306.recruitmentApp.DTOs;


public class VacatureDetail {
	private String bedrijf;
	private String locatie;
	private String omschrijving;
	private String uitstroomRichting;
	private String functie;
	
	
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
}
