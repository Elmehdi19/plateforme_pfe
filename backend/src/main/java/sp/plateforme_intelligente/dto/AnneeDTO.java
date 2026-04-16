package sp.plateforme_intelligente.dto;

public class AnneeDTO {
    private Long id;
    private String nom;
    private Integer ordre;
    private boolean diplômante;
    private Long filiereId;
    private String filiereNom;
    
    public AnneeDTO() {}
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public Integer getOrdre() { return ordre; }
    public void setOrdre(Integer ordre) { this.ordre = ordre; }
    
    public boolean isDiplômante() { return diplômante; }
    public void setDiplômante(boolean diplômante) { this.diplômante = diplômante; }
    
    public Long getFiliereId() { return filiereId; }
    public void setFiliereId(Long filiereId) { this.filiereId = filiereId; }
    
    public String getFiliereNom() { return filiereNom; }
    public void setFiliereNom(String filiereNom) { this.filiereNom = filiereNom; }
}