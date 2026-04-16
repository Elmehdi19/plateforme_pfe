package sp.plateforme_intelligente.dto;

public class ModuleDTO {
    private Long id;
    private String code;
    private String intitule;
    private Integer credits;
    private Float coefficient;
    private Long semestreId;
    private String semestreNom;
    private Long filiereId;
    private String filiereNom;
    private Long professeurId;
    private String professeurNom;
    
    public ModuleDTO() {}
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    
    public String getIntitule() { return intitule; }
    public void setIntitule(String intitule) { this.intitule = intitule; }
    
    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }
    
    public Float getCoefficient() { return coefficient; }
    public void setCoefficient(Float coefficient) { this.coefficient = coefficient; }
    
    public Long getSemestreId() { return semestreId; }
    public void setSemestreId(Long semestreId) { this.semestreId = semestreId; }
    
    public String getSemestreNom() { return semestreNom; }
    public void setSemestreNom(String semestreNom) { this.semestreNom = semestreNom; }
    
    public Long getFiliereId() { return filiereId; }
    public void setFiliereId(Long filiereId) { this.filiereId = filiereId; }
    
    public String getFiliereNom() { return filiereNom; }
    public void setFiliereNom(String filiereNom) { this.filiereNom = filiereNom; }
    
    public Long getProfesseurId() { return professeurId; }
    public void setProfesseurId(Long professeurId) { this.professeurId = professeurId; }
    
    public String getProfesseurNom() { return professeurNom; }
    public void setProfesseurNom(String professeurNom) { this.professeurNom = professeurNom; }
}