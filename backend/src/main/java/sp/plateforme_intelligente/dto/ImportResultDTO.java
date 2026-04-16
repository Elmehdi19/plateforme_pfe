package sp.plateforme_intelligente.dto;

import java.util.ArrayList;
import java.util.List;

public class ImportResultDTO {
    private int total;
    private int success;
    private int errors;
    private List<EtudiantImportDTO> importedStudents = new ArrayList<>();
    private List<EtudiantImportDTO> failedStudents = new ArrayList<>();
    
    // Getters et Setters
    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }
    
    public int getSuccess() { return success; }
    public void setSuccess(int success) { this.success = success; }
    
    public int getErrors() { return errors; }
    public void setErrors(int errors) { this.errors = errors; }
    
    public List<EtudiantImportDTO> getImportedStudents() { return importedStudents; }
    public void setImportedStudents(List<EtudiantImportDTO> importedStudents) { 
        this.importedStudents = importedStudents; 
    }
    
    public List<EtudiantImportDTO> getFailedStudents() { return failedStudents; }
    public void setFailedStudents(List<EtudiantImportDTO> failedStudents) { 
        this.failedStudents = failedStudents; 
    }
}