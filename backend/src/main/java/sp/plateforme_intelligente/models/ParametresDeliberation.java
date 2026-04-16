package sp.plateforme_intelligente.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "parametres_deliberation")
public class ParametresDeliberation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "filiere_id")
    private Filiere filiere;
    
    @Column(nullable = false)
    private Float noteMinModule; // X : note minimale pour valider un module
    
    @Column(nullable = false)
    private Float moyenneMinSemestre; // Y : moyenne minimale pour valider un semestre
    
    @Column(nullable = false)
    private Float noteEliminatoire; // Z : note éliminatoire
    
    private Float moyenneMinAnnuelle; // k : moyenne minimale pour valider l'année
    
    private Integer maxModulesParSemestre = 7; // 7 par défaut
    
    private boolean actif = true;

    public ParametresDeliberation() {}

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Filiere getFiliere() { return filiere; }
    public void setFiliere(Filiere filiere) { this.filiere = filiere; }
    public Float getNoteMinModule() { return noteMinModule; }
    public void setNoteMinModule(Float noteMinModule) { this.noteMinModule = noteMinModule; }
    public Float getMoyenneMinSemestre() { return moyenneMinSemestre; }
    public void setMoyenneMinSemestre(Float moyenneMinSemestre) { this.moyenneMinSemestre = moyenneMinSemestre; }
    public Float getNoteEliminatoire() { return noteEliminatoire; }
    public void setNoteEliminatoire(Float noteEliminatoire) { this.noteEliminatoire = noteEliminatoire; }
    public Float getMoyenneMinAnnuelle() { return moyenneMinAnnuelle; }
    public void setMoyenneMinAnnuelle(Float moyenneMinAnnuelle) { this.moyenneMinAnnuelle = moyenneMinAnnuelle; }
    public Integer getMaxModulesParSemestre() { return maxModulesParSemestre; }
    public void setMaxModulesParSemestre(Integer maxModulesParSemestre) { 
        this.maxModulesParSemestre = maxModulesParSemestre; 
    }
    public boolean isActif() { return actif; }
    public void setActif(boolean actif) { this.actif = actif; }
}