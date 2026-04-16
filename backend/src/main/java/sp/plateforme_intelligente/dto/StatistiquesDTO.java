package sp.plateforme_intelligente.dto;

public class StatistiquesDTO {
    private long totalEtudiants;
    private long totalProfesseurs;
    private long totalFilieres;
    private long totalModules;
    private long totalInscriptions;
    private long totalNotes;
    private double moyenneGenerale;
    private long etudiantsRisqueEleve;
    private long etudiantsRisqueMoyen;
    private long etudiantsRisqueFaible;
    
    public StatistiquesDTO() {}
    
    // Getters et Setters
    public long getTotalEtudiants() { return totalEtudiants; }
    public void setTotalEtudiants(long totalEtudiants) { this.totalEtudiants = totalEtudiants; }
    
    public long getTotalProfesseurs() { return totalProfesseurs; }
    public void setTotalProfesseurs(long totalProfesseurs) { this.totalProfesseurs = totalProfesseurs; }
    
    public long getTotalFilieres() { return totalFilieres; }
    public void setTotalFilieres(long totalFilieres) { this.totalFilieres = totalFilieres; }
    
    public long getTotalModules() { return totalModules; }
    public void setTotalModules(long totalModules) { this.totalModules = totalModules; }
    
    public long getTotalInscriptions() { return totalInscriptions; }
    public void setTotalInscriptions(long totalInscriptions) { this.totalInscriptions = totalInscriptions; }
    
    public long getTotalNotes() { return totalNotes; }
    public void setTotalNotes(long totalNotes) { this.totalNotes = totalNotes; }
    
    public double getMoyenneGenerale() { return moyenneGenerale; }
    public void setMoyenneGenerale(double moyenneGenerale) { this.moyenneGenerale = moyenneGenerale; }
    
    public long getEtudiantsRisqueEleve() { return etudiantsRisqueEleve; }
    public void setEtudiantsRisqueEleve(long etudiantsRisqueEleve) { this.etudiantsRisqueEleve = etudiantsRisqueEleve; }
    
    public long getEtudiantsRisqueMoyen() { return etudiantsRisqueMoyen; }
    public void setEtudiantsRisqueMoyen(long etudiantsRisqueMoyen) { this.etudiantsRisqueMoyen = etudiantsRisqueMoyen; }
    
    public long getEtudiantsRisqueFaible() { return etudiantsRisqueFaible; }
    public void setEtudiantsRisqueFaible(long etudiantsRisqueFaible) { this.etudiantsRisqueFaible = etudiantsRisqueFaible; }
}