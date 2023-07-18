package nl.yc2306.recruitmentApp.DTOs;

public class AanbiedingAanBedrijf {
    private long id;
    private String naam;
    private String uitstroomRichting;
    private String locatie;
    private boolean uitgenodigd;
    private boolean afgewezen;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
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

    public boolean isUitgenodigd() {
        return uitgenodigd;
    }

    public void setUitgenodigd(boolean uitgenodigd) {
        this.uitgenodigd = uitgenodigd;
    }

    public boolean isAfgewezen() {
        return afgewezen;
    }

    public void setAfgewezen(boolean afgewezen) {
        this.afgewezen = afgewezen;
    }
}
