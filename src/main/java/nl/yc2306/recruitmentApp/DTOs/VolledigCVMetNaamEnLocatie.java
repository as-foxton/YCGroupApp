package nl.yc2306.recruitmentApp.DTOs;

import jakarta.persistence.Column;

public class VolledigCVMetNaamEnLocatie {
    private String naam;
    private String locatie;
    private String uitstroomRichting;
    private String specialiteit;
    private String omschrijving;
    private String werkHistorie;

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getLocatie() {
        return locatie;
    }

    public void setLocatie(String locatie) {
        this.locatie = locatie;
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
}
