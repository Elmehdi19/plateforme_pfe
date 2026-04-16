package sp.plateforme_intelligente.models;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "etudiants")
public class Etudiant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(unique = true)
    private String matricule;
    
    private String cne; // Code Massar
    private String cin;
    private String nationalite;
    private String sexe;
    private LocalDate dateNaissance;
    private String lieuNaissance;
    private String adresse;
    private String ville;
    private String telephone;
    
    // Informations BAC
    private String bacAnnee;
    private String bacSerie;
    private String bacMention;
    private String bacLycée;
    private String bacLieuObtention;
    private String bacAcademie;
    
    @ManyToOne
    @JoinColumn(name = "filiere_id")
    private Filiere filiere;
    
    @OneToMany(mappedBy = "etudiant")
    private List<Inscription> inscriptions;
    
    @OneToMany(mappedBy = "etudiant")
    private List<Note> notes;
    
    @OneToMany(mappedBy = "etudiant")
    private List<Prediction> predictions;

    public Etudiant() {}

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getMatricule() { return matricule; }
    public void setMatricule(String matricule) { this.matricule = matricule; }
    public String getCne() { return cne; }
    public void setCne(String cne) { this.cne = cne; }
    public String getCin() { return cin; }
    public void setCin(String cin) { this.cin = cin; }
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
    public Filiere getFiliere() { return filiere; }
    public void setFiliere(Filiere filiere) { this.filiere = filiere; }
    public List<Inscription> getInscriptions() { return inscriptions; }
    public void setInscriptions(List<Inscription> inscriptions) { this.inscriptions = inscriptions; }
    public List<Note> getNotes() { return notes; }
    public void setNotes(List<Note> notes) { this.notes = notes; }
    public List<Prediction> getPredictions() { return predictions; }
    public void setPredictions(List<Prediction> predictions) { this.predictions = predictions; }
}