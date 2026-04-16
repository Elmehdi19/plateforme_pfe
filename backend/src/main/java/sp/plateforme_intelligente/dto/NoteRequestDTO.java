package sp.plateforme_intelligente.dto;

public class NoteRequestDTO {
    private Long etudiantId;
    private Long elementId;
    private Float valeur;
    private String typeEvaluation;
    private String session;

    // Getters et setters
    public Long getEtudiantId() { return etudiantId; }
    public void setEtudiantId(Long etudiantId) { this.etudiantId = etudiantId; }
    public Long getElementId() { return elementId; }
    public void setElementId(Long elementId) { this.elementId = elementId; }
    public Float getValeur() { return valeur; }
    public void setValeur(Float valeur) { this.valeur = valeur; }
    public String getTypeEvaluation() { return typeEvaluation; }
    public void setTypeEvaluation(String typeEvaluation) { this.typeEvaluation = typeEvaluation; }
    public String getSession() { return session; }
    public void setSession(String session) { this.session = session; }
}