package nl.yc2306.recruitmentApp;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Account {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(length=150, nullable= false)
	private String naam;
	@Column (length=150, nullable=false)
	private String rol;
	@Column (length=150, nullable=false)
	private String email;
	@Column (length=150, nullable=false)
	private String wachtwoord;
	@Column (length=150, nullable=true)
	private String bedrijf;
	@Column (length=150, nullable=true)
	private String locatie;
	@OneToOne(mappedBy = "persoon", optional = true)
	private CurriculumVitae curriculumVitae;

	@OneToMany(mappedBy = "account")
	private List<Vacature> vacatures;

	@OneToMany(mappedBy = "account")
	private List<Feedback> feedbacks;

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

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWachtwoord() {
		return wachtwoord;
	}

	public void setWachtwoord(String wachtwoord) {
		this.wachtwoord = wachtwoord;
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
}
