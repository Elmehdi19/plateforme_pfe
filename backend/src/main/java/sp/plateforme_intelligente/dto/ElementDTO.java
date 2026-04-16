package sp.plateforme_intelligente.dto;

public class ElementDTO {
    private Long id;
    private String nom;
    private String code;
    private Float coefficient;
    private Long moduleId;
    private String moduleIntitule;
    private Long professeurId;
    private String professeurNom;
    
    public ElementDTO() {}
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public Float getCoefficient() { return coefficient; }
    public void setCoefficient(Float coefficient) { this.coefficient = coefficient; }
    public Long getModuleId() { return moduleId; }
    public void setModuleId(Long moduleId) { this.moduleId = moduleId; }
    public String getModuleIntitule() { return moduleIntitule; }
    public void setModuleIntitule(String moduleIntitule) { this.moduleIntitule = moduleIntitule; }
    public Long getProfesseurId() { return professeurId; }
    public void setProfesseurId(Long professeurId) { this.professeurId = professeurId; }
    public String getProfesseurNom() { return professeurNom; }
    public void setProfesseurNom(String professeurNom) { this.professeurNom = professeurNom; }
}