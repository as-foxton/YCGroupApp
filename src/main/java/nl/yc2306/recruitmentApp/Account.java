package nl.yc2306.recruitmentApp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Account {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long persoons_nr;
	
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
	
	
	public long getPersoons_nr() {
		return persoons_nr;
	}
	public void setPersoons_nr(long persoons_nr) {
		this.persoons_nr = persoons_nr;
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

	public String getWachtwoord() {
		return wachtwoord;
	}

	public void setWachtwoord(String wachtwoord) {
		this.wachtwoord = wachtwoord;
	}
}
