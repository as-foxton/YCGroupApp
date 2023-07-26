package nl.yc2306.recruitmentApp;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import nl.yc2306.recruitmentApp.DTOs.AanbiedingAanBedrijf;
import nl.yc2306.recruitmentApp.DTOs.AanbiedingAanKandidaat;

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
	@OneToMany(mappedBy = "aanbieding")
	private List<Feedback> feedback;
	
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

	public List<Feedback> getFeedback() {
		return feedback;
	}

	public void setFeedback(List<Feedback> feedback) {
		this.feedback = feedback;
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

	public AanbiedingAanKandidaat maakAanbiedingAanKandidaat(){
		AanbiedingAanKandidaat response = new AanbiedingAanKandidaat();
		response.setId(id);
		response.setVacatureId(vacature.getId());
		response.setBedrijf(vacature.getBedrijf());
		response.setFunctie(vacature.getFunctie());
		response.setLocatie(vacature.getLocatie());
		return response;
	}
}
