package nl.yc2306.recruitmentApp.DTOs;

public class AanbiedingAanKandidaat {
    private long id;
    private long vacatureId;
    private String bedrijf;
    private String functie;
    private String locatie;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVacatureId() {
        return vacatureId;
    }

    public void setVacatureId(long vacatureId) {
        this.vacatureId = vacatureId;
    }

    public String getBedrijf() {
        return bedrijf;
    }

    public void setBedrijf(String bedrijf) {
        this.bedrijf = bedrijf;
    }

    public String getFunctie() {
        return functie;
    }

    public void setFunctie(String functie) {
        this.functie = functie;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }
}
