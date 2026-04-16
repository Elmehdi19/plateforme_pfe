package sp.plateforme_intelligente.dto;

public class SemestreDTO {
    private Long id;
    private String nom;
    private Integer ordre;
    private Long anneeId;
    private String anneeNom;
    
    public SemestreDTO() {}
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public Integer getOrdre() { return ordre; }
    public void setOrdre(Integer ordre) { this.ordre = ordre; }
    
    public Long getAnneeId() { return anneeId; }
    public void setAnneeId(Long anneeId) { this.anneeId = anneeId; }
    
    public String getAnneeNom() { return anneeNom; }
    public void setAnneeNom(String anneeNom) { this.anneeNom = anneeNom; }
}