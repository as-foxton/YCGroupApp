package nl.yc2306.recruitmentApp;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import nl.yc2306.recruitmentApp.DTOs.AanbiedingAanBedrijf;

@Entity
public class Aanbieding {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@JsonIgnore
	@ManyToOne(optional = false)
	private Vacature vacature;
	
	@JsonIgnore
	@ManyToOne(optional = false)
	private CurriculumVitae curriculumVitae;
	
	@Column(nullable = false)
	private LocalDateTime createdOn;
	@Column
	private boolean uitgenodigd;
	@Column
	private boolean afgewezen;
	@Column
	private boolean aangenomen;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Vacature getVacature() {
		return vacature;
	}

	public void setVacature(Vacature vacature) {
		this.vacature = vacature;
	}

	public CurriculumVitae getCurriculumVitae() {
		return curriculumVitae;
	}

	public void setCurriculumVitae(CurriculumVitae curriculumVitae) {
		this.curriculumVitae = curriculumVitae;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
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

	public boolean isAangenomen() {
		return aangenomen;
	}

	public void setAangenomen(boolean aangenomen) {
		this.aangenomen = aangenomen;
	}

	public AanbiedingAanBedrijf maakAanbiedingAanBedrijf(){
		AanbiedingAanBedrijf response = new AanbiedingAanBedrijf();
		response.setId(id);
		response.setCvId(curriculumVitae.getId());
		response.setAfgewezen(afgewezen);
		response.setUitgenodigd(uitgenodigd);
		response.setNaam(curriculumVitae.getPersoon().getNaam());
		response.setLocatie(curriculumVitae.getLocatie());
		response.setUitstroomRichting(curriculumVitae.getUitstroomRichting());
		return response;
	}
}
