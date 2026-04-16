package sp.plateforme_intelligente.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "elements")
public class Element {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nom; // "POO JAVA", "TP JAVA", "Cours JAVA"
    
    private String code;
    
    private Float coefficient; // Coefficient dans le module
    
    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;
    
    @ManyToOne
    @JoinColumn(name = "professeur_id")
    private Professeur professeur;
    
    @OneToMany(mappedBy = "element", cascade = CascadeType.ALL, orphanRemoval=true)
    private List<Note> notes;

    public Element() {}

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public Float getCoefficient() { return coefficient; }
    public void setCoefficient(Float coefficient) { this.coefficient = coefficient; }
    public Module getModule() { return module; }
    public void setModule(Module module) { this.module = module; }
    public Professeur getProfesseur() { return professeur; }
    public void setProfesseur(Professeur professeur) { this.professeur = professeur; }
    public List<Note> getNotes() { return notes; }
    public void setNotes(List<Note> notes) { this.notes = notes; }
}