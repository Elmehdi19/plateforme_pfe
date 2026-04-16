package sp.plateforme_intelligente.dto;

public class ElementImportDTO {
    private String nom;
    private String code;
    private Float coefficient;
    private String moduleCode;
    private String professeurEmail;
    private String ligne;
    private String erreur;
    
    public ElementImportDTO() {}
    
    // Getters et Setters
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    
    public Float getCoefficient() { return coefficient; }
    public void setCoefficient(Float coefficient) { this.coefficient = coefficient; }
    
    public String getModuleCode() { return moduleCode; }
    public void setModuleCode(String moduleCode) { this.moduleCode = moduleCode; }
    
    public String getProfesseurEmail() { return professeurEmail; }
    public void setProfesseurEmail(String professeurEmail) { this.professeurEmail = professeurEmail; }
    
    public String getLigne() { return ligne; }
    public void setLigne(String ligne) { this.ligne = ligne; }
    
    public String getErreur() { return erreur; }
    public void setErreur(String erreur) { this.erreur = erreur; }
}