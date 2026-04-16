package sp.plateforme_intelligente.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "inscriptions")
public class Inscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "etudiant_id", nullable = false)
    private Etudiant etudiant;
    
    @ManyToOne
    @JoinColumn(name = "annee_universitaire_id")
    private AnneeUniversitaire anneeUniversitaire;
    
    private LocalDate dateInscription;
    
    private String type; // "EN_LIGNE", "ADMINISTRATIVE", "PEDAGOGIQUE"
    
    // Pour l'inscription en ligne
    private String massarEtudiant;
    private String nomArabe;
    private String prenomArabe;
    private String lieuNaissanceArabe;
    
    // Pour l'inscription administrative
    private String adresse;
    private String ville;
    private String telephone;
    private String bourse;
    
    // Pour l'inscription pédagogique
    @ManyToOne
    @JoinColumn(name = "semestre_id")
    private Semestre semestre;
    
    private boolean inscritAutomatiquement;

    public Inscription() {}

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Etudiant getEtudiant() { return etudiant; }
    public void setEtudiant(Etudiant etudiant) { this.etudiant = etudiant; }
    public AnneeUniversitaire getAnneeUniversitaire() { return anneeUniversitaire; }
    public void setAnneeUniversitaire(AnneeUniversitaire anneeUniversitaire) { 
        this.anneeUniversitaire = anneeUniversitaire; 
    }
    public LocalDate getDateInscription() { return dateInscription; }
    public void setDateInscription(LocalDate dateInscription) { this.dateInscription = dateInscription; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getMassarEtudiant() { return massarEtudiant; }
    public void setMassarEtudiant(String massarEtudiant) { this.massarEtudiant = massarEtudiant; }
    public String getNomArabe() { return nomArabe; }
    public void setNomArabe(String nomArabe) { this.nomArabe = nomArabe; }
    public String getPrenomArabe() { return prenomArabe; }
    public void setPrenomArabe(String prenomArabe) { this.prenomArabe = prenomArabe; }
    public String getLieuNaissanceArabe() { return lieuNaissanceArabe; }
    public void setLieuNaissanceArabe(String lieuNaissanceArabe) { this.lieuNaissanceArabe = lieuNaissanceArabe; }
    public String getAdresse() { return adresse; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }
    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }
    public String getBourse() { return bourse; }
    public void setBourse(String bourse) { this.bourse = bourse; }
    public Semestre getSemestre() { return semestre; }
    public void setSemestre(Semestre semestre) { this.semestre = semestre; }
    public boolean isInscritAutomatiquement() { return inscritAutomatiquement; }
    public void setInscritAutomatiquement(boolean inscritAutomatiquement) { 
        this.inscritAutomatiquement = inscritAutomatiquement; 
    }
}