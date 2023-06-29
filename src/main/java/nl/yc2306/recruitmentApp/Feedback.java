package nl.yc2306.recruitmentApp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Feedback {
	//public int vacature Nr;		// Bespreken om deze aanpassing in het diagram te plaatsen
	public long feedback_nr;
	public int cv_nr;
	public int persoon_nr;
	public String mening;
	public String aangenomen;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long getfeedback_nr() {
		return feedback_nr;
	}
	
	public void setfeedback_nr(long feedback_nr) {
		this.feedback_nr = feedback_nr;
	}
	
	public int getCvNr() {
		return cv_nr;
	}
	
	public void setCvNr(int cv_nr) {
		this.cv_nr = cv_nr;
	}
	
	public int getPersoonNr() {
		return persoon_nr;
	}
	
	public void setPersoonNr(int persoon_nr) {
		this.persoon_nr = persoon_nr;
	}
	
	public String getMening() {
		return mening;
	}
	
	public void setMening(String mening) {
		this.mening = mening;
	}
	
	public String getAangenomen() {
		return aangenomen;
	}
	
	public void setAangenomen(String aangenomen) {
		this.aangenomen = aangenomen;
	}
}
