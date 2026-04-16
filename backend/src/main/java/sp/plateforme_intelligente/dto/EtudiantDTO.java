package sp.plateforme_intelligente.dto;

import java.time.LocalDate;

public class EtudiantDTO {
    private Long id;
    private String matricule;
    private String cne;
    private String cin;
    private String nom;
    private String email;
    private String nationalite;
    private String sexe;
    private LocalDate dateNaissance;
    private String lieuNaissance;
    private String adresse;
    private String ville;
    private String telephone;
    private String bacAnnee;
    private String bacSerie;
    private String bacMention;
    private String bacLycée;
    private String bacLieuObtention;
    private String bacAcademie;
    private String filiere;
    private Long filiereId;
    private String semestre;

    public String getSemestre() { return semestre; }
    public void setSemestre(String semestre) { this.semestre = semestre; }
    
    public EtudiantDTO() {}
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getMatricule() { return matricule; }
    public void setMatricule(String matricule) { this.matricule = matricule; }
    
    public String getCne() { return cne; }
    public void setCne(String cne) { this.cne = cne; }
    
    public String getCin() { return cin; }
    public void setCin(String cin) { this.cin = cin; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getNationalite() { return nationalite; }
    public void setNationalite(String nationalite) { this.nationalite = nationalite; }
    
    public String getSexe() { return sexe; }
    public void setSexe(String sexe) { this.sexe = sexe; }
    
    public LocalDate getDateNaissance() { return dateNaissance; }
    public void setDateNaissance(LocalDate dateNaissance) { this.dateNaissance = dateNaissance; }
    
    public String getLieuNaissance() { return lieuNaissance; }
    public void setLieuNaissance(String lieuNaissance) { this.lieuNaissance = lieuNaissance; }
    
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    
    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }
    
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    
    public String getBacAnnee() { return bacAnnee; }
    public void setBacAnnee(String bacAnnee) { this.bacAnnee = bacAnnee; }
    
    public String getBacSerie() { return bacSerie; }
    public void setBacSerie(String bacSerie) { this.bacSerie = bacSerie; }
    
    public String getBacMention() { return bacMention; }
    public void setBacMention(String bacMention) { this.bacMention = bacMention; }
    
    public String getBacLycée() { return bacLycée; }
    public void setBacLycée(String bacLycée) { this.bacLycée = bacLycée; }
    
    public String getBacLieuObtention() { return bacLieuObtention; }
    public void setBacLieuObtention(String bacLieuObtention) { this.bacLieuObtention = bacLieuObtention; }
    
    public String getBacAcademie() { return bacAcademie; }
    public void setBacAcademie(String bacAcademie) { this.bacAcademie = bacAcademie; }
    
    public String getFiliere() { return filiere; }
    public void setFiliere(String filiere) { this.filiere = filiere; }
    
    public Long getFiliereId() { return filiereId; }
    public void setFiliereId(Long filiereId) { this.filiereId = filiereId; }
}