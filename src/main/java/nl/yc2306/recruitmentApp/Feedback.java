package nl.yc2306.recruitmentApp;

import jakarta.persistence.*;

@Entity
public class Feedback {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Column(nullable = false, length = 500)
	private String mening;
	@ManyToOne(optional = false)
	private Aanbieding aanbieding;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMening() {
		return mening;
	}

	public void setMening(String mening) {
		this.mening = mening;
	}


	public Aanbieding getAanbieding() {
		return aanbieding;
	}

	public void setAanbieding(Aanbieding aanbieding) {
		this.aanbieding = aanbieding;
	}

}
