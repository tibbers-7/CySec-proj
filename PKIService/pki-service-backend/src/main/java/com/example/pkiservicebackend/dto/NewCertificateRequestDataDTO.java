public class NewCertificateRequestDataDTO {

    private Long id;

    private Date startDate;
    private  Date endDate;
    private DigitalEntity issuer;
    private DigitalEntity subject;
    private String certificateRole;

    public NewCertificateRequestDataDTO() {
    }

    public NewCertificateRequestDataDTO(Long id, DateTime startDate, DateTime endDate, DigitalEntity issuer, DigitalEntity subject, String certificateRole) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.issuer = issuer;
        this.subject = subject;
        this.certificateRole = certificateRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    public DigitalEntity getIssuer() {
        return issuer;
    }

    public void setIssuer(DigitalEntity issuer) {
        this.issuer = issuer;
    }

    public DigitalEntity getSubject() {
        return subject;
    }

    public void setSubject(DigitalEntity subject) {
        this.subject = subject;
    }

    public String getCertificateRole() {
        return certificateRole;
    }

    public void setCertificateRole(String certificateRole) {
        this.certificateRole = certificateRole;
    }
}
