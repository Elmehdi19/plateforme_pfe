package sp.plateforme_intelligente.dto;

public class ParametresDeliberationDTO {
    private Long id;
    private Long filiereId;
    private String filiereNom;
    private Float noteMinModule;
    private Float moyenneMinSemestre;
    private Float noteEliminatoire;
    private Float moyenneMinAnnuelle;
    private Integer maxModulesParSemestre;
    private boolean actif;
    
    public ParametresDeliberationDTO() {}
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getFiliereId() { return filiereId; }
    public void setFiliereId(Long filiereId) { this.filiereId = filiereId; }
    
    public String getFiliereNom() { return filiereNom; }
    public void setFiliereNom(String filiereNom) { this.filiereNom = filiereNom; }
    
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