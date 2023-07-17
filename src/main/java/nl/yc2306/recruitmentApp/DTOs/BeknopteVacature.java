package nl.yc2306.recruitmentApp.DTOs;

public class BeknopteVacature {
    private long id;
    private String bedrijf;
    private String uitstroomRichting;
    private String locatie;

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

    public String getUitstroomRichting() {
        return uitstroomRichting;
    }

    public void setUitstroomRichting(String uitstroomRichting) {
        this.uitstroomRichting = uitstroomRichting;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
    }
}
