package sp.plateforme_intelligente.dto;

import java.time.LocalDate;

public class NoteDTO {
    private Long id;
    private Long elementId;
    private String elementNom;
    private Long moduleId;
    private String moduleIntitule;
    private Long etudiantId;
    private String etudiantNom;
    private String etudiantMatricule;
    private Float valeur;
    private String typeEvaluation;
    private LocalDate dateEvaluation;
    private String session;
    
    public NoteDTO() {}
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getElementId() { return elementId; }
    public void setElementId(Long elementId) { this.elementId = elementId; }
    
    public String getElementNom() { return elementNom; }
    public void setElementNom(String elementNom) { this.elementNom = elementNom; }
    
    public Long getModuleId() { return moduleId; }
    public void setModuleId(Long moduleId) { this.moduleId = moduleId; }
    
    public String getModuleIntitule() { return moduleIntitule; }
    public void setModuleIntitule(String moduleIntitule) { this.moduleIntitule = moduleIntitule; }
    
    public Long getEtudiantId() { return etudiantId; }
    public void setEtudiantId(Long etudiantId) { this.etudiantId = etudiantId; }
    
    public String getEtudiantNom() { return etudiantNom; }
    public void setEtudiantNom(String etudiantNom) { this.etudiantNom = etudiantNom; }
    
    public String getEtudiantMatricule() { return etudiantMatricule; }
    public void setEtudiantMatricule(String etudiantMatricule) { this.etudiantMatricule = etudiantMatricule; }
    
    public Float getValeur() { return valeur; }
    public void setValeur(Float valeur) { this.valeur = valeur; }
    
    public String getTypeEvaluation() { return typeEvaluation; }
    public void setTypeEvaluation(String typeEvaluation) { this.typeEvaluation = typeEvaluation; }
    
    public LocalDate getDateEvaluation() { return dateEvaluation; }
    public void setDateEvaluation(LocalDate dateEvaluation) { this.dateEvaluation = dateEvaluation; }
    
    public String getSession() { return session; }
    public void setSession(String session) { this.session = session; }
}