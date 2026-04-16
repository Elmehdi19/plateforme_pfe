package sp.plateforme_intelligente.dto;

public class FiliereImportDTO {
    private String nom;
    private String code;
    private String departementNom;  // Pour trouver le département
    private String ligne;
    private String erreur;
    
    // Getters et Setters
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public String getDepartementNom() { return departementNom; }
    public void setDepartementNom(String departementNom) { this.departementNom = departementNom; }
    public String getLigne() { return ligne; }
    public void setLigne(String ligne) { this.ligne = ligne; }
    public String getErreur() { return erreur; }
    public void setErreur(String erreur) { this.erreur = erreur; }
}