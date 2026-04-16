package sp.plateforme_intelligente.dto;

public class EtudiantImportDTO {
    private String matricule;
    private String cne;              // ← AJOUTÉ
    private String nom;              // Nom
    private String prenom;            // ← AJOUTÉ
    private String email;
    private String filiere;
    private String annee;             // ← CHANGÉ EN String (ou Integer selon ton Excel)
    private String ligne;
    private String erreur;
    
    public EtudiantImportDTO() {}
    
    // Getters et Setters
    public String getMatricule() { return matricule; }
    public void setMatricule(String matricule) { this.matricule = matricule; }
    
    public String getCne() { return cne; }
    public void setCne(String cne) { this.cne = cne; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getFiliere() { return filiere; }
    public void setFiliere(String filiere) { this.filiere = filiere; }
    
    public String getAnnee() { return annee; }
    public void setAnnee(String annee) { this.annee = annee; }
    
    public String getLigne() { return ligne; }
    public void setLigne(String ligne) { this.ligne = ligne; }
    
    public String getErreur() { return erreur; }
    public void setErreur(String erreur) { this.erreur = erreur; }
}