package nl.yc2306.recruitmentApp.DTOs;

public class CVFilterRequest {
    private String plaats;
    private int maxAfstand;
    private String uitstroomRichting;

    public String getPlaats() {
        return plaats;
    }

    public void setPlaats(String plaats) {
        this.plaats = plaats;
    }

    public int getMaxAfstand() {
        return maxAfstand;
    }

    public void setMaxAfstand(int maxAfstand) {
        this.maxAfstand = maxAfstand;
    }

    public String getUitstroomRichting() {
        return uitstroomRichting;
    }

    public void setUitstroomRichting(String uitstroomRichting) {
        this.uitstroomRichting = uitstroomRichting;
    }
}
