package sp.plateforme_intelligente.dto;

import java.util.ArrayList;
import java.util.List;

public class ImportElementResultDTO {
    private int total;
    private int success;
    private int errors;
    private List<ElementImportDTO> importedElements = new ArrayList<>();
    private List<ElementImportDTO> failedElements = new ArrayList<>();
    
    public ImportElementResultDTO() {}
    
    // Getters et Setters
    public int getTotal() { return total; }
    public void setTotal(int total) { this.total = total; }
    
    public int getSuccess() { return success; }
    public void setSuccess(int success) { this.success = success; }
    
    public int getErrors() { return errors; }
    public void setErrors(int errors) { this.errors = errors; }
    
    public List<ElementImportDTO> getImportedElements() { return importedElements; }
    public void setImportedElements(List<ElementImportDTO> importedElements) { 
        this.importedElements = importedElements; 
    }
    
    public List<ElementImportDTO> getFailedElements() { return failedElements; }
    public void setFailedElements(List<ElementImportDTO> failedElements) { 
        this.failedElements = failedElements; 
    }
}