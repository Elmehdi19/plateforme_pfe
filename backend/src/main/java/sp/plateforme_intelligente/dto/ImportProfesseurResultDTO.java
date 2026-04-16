package sp.plateforme_intelligente.dto;

import java.util.ArrayList;
import java.util.List;

public class ImportProfesseurResultDTO {
    private int total;
    private int success;
    private int errors;
    private List<ProfesseurImportDTO> importedProfesseurs = new ArrayList<>();
    private List<ProfesseurImportDTO> failedProfesseurs = new ArrayList<>();
    
    // Getters et Setters
    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }
    public int getSuccess() { return success; }
    public void setSuccess(int success) { this.success = success; }
    public int getErrors() { return errors; }
    public void setErrors(int errors) { this.errors = errors; }
    public List<ProfesseurImportDTO> getImportedProfesseurs() { return importedProfesseurs; }
    public void setImportedProfesseurs(List<ProfesseurImportDTO> importedProfesseurs) { 
        this.importedProfesseurs = importedProfesseurs; 
    }
    public List<ProfesseurImportDTO> getFailedProfesseurs() { return failedProfesseurs; }
    public void setFailedProfesseurs(List<ProfesseurImportDTO> failedProfesseurs) { 
        this.failedProfesseurs = failedProfesseurs; 
    }
}