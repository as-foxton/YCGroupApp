package nl.yc2306.recruitmentApp.DTOs;

public class SaveFeedbackDTO {
    private long aanbiedingId;
    private String mening;

    public long getAanbiedingId() {
        return aanbiedingId;
    }

    public void setAanbiedingId(long aanbiedingId) {
        this.aanbiedingId = aanbiedingId;
    }

    public String getMening() {
        return mening;
    }

    public void setMening(String mening) {
        this.mening = mening;
    }
}
