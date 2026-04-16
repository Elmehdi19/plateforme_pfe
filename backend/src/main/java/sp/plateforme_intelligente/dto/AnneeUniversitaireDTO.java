package sp.plateforme_intelligente.dto;

import java.time.LocalDate;

public class AnneeUniversitaireDTO {
    private Long id;
    private String code;
    private LocalDate dateDebut;
    private LocalDate dateFin;
    private boolean active;
    
    public AnneeUniversitaireDTO() {}
    
    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    
    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate dateDebut) { this.dateDebut = dateDebut; }
    
    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate dateFin) { this.dateFin = dateFin; }
    
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}