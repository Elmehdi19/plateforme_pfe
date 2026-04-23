package sp.plateforme_intelligente.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "absences")
public class Absence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "etudiant_id", nullable = false)
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;

    private LocalDate dateAbsence;
    private Boolean justifiee = false;
    private String raison;

    // Getters et setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Etudiant getEtudiant() { return etudiant; }
    public void setEtudiant(Etudiant etudiant) { this.etudiant = etudiant; }

    public Module getModule() { return module; }
    public void setModule(Module module) { this.module = module; }

    public LocalDate getDateAbsence() { return dateAbsence; }
    public void setDateAbsence(LocalDate dateAbsence) { this.dateAbsence = dateAbsence; }

    public Boolean getJustifiee() { return justifiee; }
    public void setJustifiee(Boolean justifiee) { this.justifiee = justifiee; }

    public String getRaison() { return raison; }
    public void setRaison(String raison) { this.raison = raison; }
}