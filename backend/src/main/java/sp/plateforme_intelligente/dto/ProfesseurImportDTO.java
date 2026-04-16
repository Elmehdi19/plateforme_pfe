package sp.plateforme_intelligente.dto;

public class ProfesseurImportDTO {
    private String ligne;
    private String nom;
    private String email;
    private String bureau;
    private String specialite;
    private String discipline;
    private String departement;
    private String erreur;

    // Constructeur
    public ProfesseurImportDTO() {}

    // Getters et Setters
    public String getLigne() { return ligne; }
    public void setLigne(String ligne) { this.ligne = ligne; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getBureau() { return bureau; }
    public void setBureau(String bureau) { this.bureau = bureau; }

    public String getSpecialite() { return specialite; }
    public void setSpecialite(String specialite) { this.specialite = specialite; }

    public String getDiscipline() { return discipline; }
    public void setDiscipline(String discipline) { this.discipline = discipline; }

    public String getDepartement() { return departement; }
    public void setDepartement(String departement) { this.departement = departement; }

    public String getErreur() { return erreur; }
    public void setErreur(String erreur) { this.erreur = erreur; }
}