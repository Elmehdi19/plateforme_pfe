package sp.plateforme_intelligente.dto;

import java.time.LocalDate;

public class InscriptionDTO {
    private Long id;
    private Long etudiantId;
    private String etudiantNom;
    private String etudiantMatricule;
    private Long anneeUniversitaireId;
    private String anneeUniversitaireCode;
    private LocalDate dateInscription;
    private String type;
    private Long semestreId;
    private String semestreNom;
    private boolean inscritAutomatiquement;
    
    // Champs inscription administrative
    private String adresse;
    private String ville;
    private String telephone;
    private String bourse;
    
    // Champs inscription en ligne
    private String massarEtudiant;
    private String nomArabe;
    private String prenomArabe;
    private String lieuNaissanceArabe;
    
    public InscriptionDTO() {}
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getEtudiantId() { return etudiantId; }
    public void setEtudiantId(Long etudiantId) { this.etudiantId = etudiantId; }
    
    public String getEtudiantNom() { return etudiantNom; }
    public void setEtudiantNom(String etudiantNom) { this.etudiantNom = etudiantNom; }
    
    public String getEtudiantMatricule() { return etudiantMatricule; }
    public void setEtudiantMatricule(String etudiantMatricule) { this.etudiantMatricule = etudiantMatricule; }
    
    public Long getAnneeUniversitaireId() { return anneeUniversitaireId; }
    public void setAnneeUniversitaireId(Long anneeUniversitaireId) { this.anneeUniversitaireId = anneeUniversitaireId; }
    
    public String getAnneeUniversitaireCode() { return anneeUniversitaireCode; }
    public void setAnneeUniversitaireCode(String anneeUniversitaireCode) { this.anneeUniversitaireCode = anneeUniversitaireCode; }
    
    public LocalDate getDateInscription() { return dateInscription; }
    public void setDateInscription(LocalDate dateInscription) { this.dateInscription = dateInscription; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public Long getSemestreId() { return semestreId; }
    public void setSemestreId(Long semestreId) { this.semestreId = semestreId; }
    
    public String getSemestreNom() { return semestreNom; }
    public void setSemestreNom(String semestreNom) { this.semestreNom = semestreNom; }
    
    public boolean isInscritAutomatiquement() { return inscritAutomatiquement; }
    public void setInscritAutomatiquement(boolean inscritAutomatiquement) { this.inscritAutomatiquement = inscritAutomatiquement; }
    
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    
    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }
    
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    
    public String getBourse() { return bourse; }
    public void setBourse(String bourse) { this.bourse = bourse; }
    
    public String getMassarEtudiant() { return massarEtudiant; }
    public void setMassarEtudiant(String massarEtudiant) { this.massarEtudiant = massarEtudiant; }
    
    public String getNomArabe() { return nomArabe; }
    public void setNomArabe(String nomArabe) { this.nomArabe = nomArabe; }
    
    public String getPrenomArabe() { return prenomArabe; }
    public void setPrenomArabe(String prenomArabe) { this.prenomArabe = prenomArabe; }
    
    public String getLieuNaissanceArabe() { return lieuNaissanceArabe; }
    public void setLieuNaissanceArabe(String lieuNaissanceArabe) { this.lieuNaissanceArabe = lieuNaissanceArabe; }
}