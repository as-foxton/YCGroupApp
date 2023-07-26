package nl.yc2306.recruitmentApp.DTOs;

public class FeedbackItem {
	private long id;
	private String accountName;
	private String bedrijf;
	private String locatie;
	private String mening;
	private boolean aangenomen;
	
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
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
	public String getMening() {
		return mening;
	}
	public void setMening(String mening) {
		this.mening = mening;
	}
	
	public boolean isAangenomen() {
		return aangenomen;
	}
	public void setAangenomen(boolean aangenomen) {
		this.aangenomen = aangenomen;
	}
}
