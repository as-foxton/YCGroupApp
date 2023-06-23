package nl.yc2306.recruitmentApp;

import jakarta.persistence.*;

@Entity
public class CurriculumVitae {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false)
    private long personId;
    @Column(length = 30, nullable = false)
    private String uitstroomRichting;
    @Column(length = 30, nullable = false)
    private String specialiteit;
    @Column(length = 500, nullable = false)
    private String Omschrijving;
    @Column(length = 500, nullable = false)
    private String werkHistorie;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
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
        return Omschrijving;
    }

    public void setOmschrijving(String omschrijving) {
        Omschrijving = omschrijving;
    }

    public String getWerkHistorie() {
        return werkHistorie;
    }

    public void setWerkHistorie(String werkHistorie) {
        this.werkHistorie = werkHistorie;
    }
}
