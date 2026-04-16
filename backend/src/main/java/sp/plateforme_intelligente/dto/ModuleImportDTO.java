package sp.plateforme_intelligente.dto;

public class ModuleImportDTO {
    private String ligne;
    private String code;
    private String intitule;
    private Integer credits;
    private Integer semestre;
    private String filiereNom;
    private String professeurEmail;
    private String erreur;

    public ModuleImportDTO() {}

    // Getters et Setters
    public String getLigne() { return ligne; }
    public void setLigne(String ligne) { this.ligne = ligne; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getIntitule() { return intitule; }
    public void setIntitule(String intitule) { this.intitule = intitule; }

    public Integer getCredits() { return credits; }
    public void setCredits(Integer credits) { this.credits = credits; }

    public Integer getSemestre() { return semestre; }
    public void setSemestre(Integer semestre) { this.semestre = semestre; }

    public String getFiliereNom() { return filiereNom; }
    public void setFiliereNom(String filiereNom) { this.filiereNom = filiereNom; }

    public String getProfesseurEmail() { return professeurEmail; }
    public void setProfesseurEmail(String professeurEmail) { this.professeurEmail = professeurEmail; }

    public String getErreur() { return erreur; }
    public void setErreur(String erreur) { this.erreur = erreur; }
}