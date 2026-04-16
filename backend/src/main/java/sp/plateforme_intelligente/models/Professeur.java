package sp.plateforme_intelligente.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "professeurs")
public class Professeur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    private String bureau;
    private String specialite;
    private String discipline;
    private String departement;
    
    @OneToMany(mappedBy = "professeur")
    private List<Module> modulesResponsables;
    
    @OneToMany(mappedBy = "professeur")
    private List<Element> elementsEnseignes;
    
    private boolean responsableFiliere;

    public Professeur() {}

    // Getters et Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getBureau() { return bureau; }
    public void setBureau(String bureau) { this.bureau = bureau; }
    public String getSpecialite() { return specialite; }
    public void setSpecialite(String specialite) { this.specialite = specialite; }
    public String getDiscipline() { return discipline; }
    public void setDiscipline(String discipline) { this.discipline = discipline; }
    public String getDepartement() { return departement; }
    public void setDepartement(String departement) { this.departement = departement; }
    public List<Module> getModulesResponsables() { return modulesResponsables; }
    public void setModulesResponsables(List<Module> modulesResponsables) { 
        this.modulesResponsables = modulesResponsables; 
    }
    public List<Element> getElementsEnseignes() { return elementsEnseignes; }
    public void setElementsEnseignes(List<Element> elementsEnseignes) { 
        this.elementsEnseignes = elementsEnseignes; 
    }
    public boolean isResponsableFiliere() { return responsableFiliere; }
    public void setResponsableFiliere(boolean responsableFiliere) { 
        this.responsableFiliere = responsableFiliere; 
    }
}