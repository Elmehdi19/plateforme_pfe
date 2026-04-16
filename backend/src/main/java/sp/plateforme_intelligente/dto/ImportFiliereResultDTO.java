package sp.plateforme_intelligente.dto;

import java.util.ArrayList;
import java.util.List;

public class ImportFiliereResultDTO {
    private int total;
    private int success;
    private int errors;
    private List<FiliereImportDTO> importedFilieres = new ArrayList<>();
    private List<FiliereImportDTO> failedFilieres = new ArrayList<>();

    // Constructeur par défaut
    public ImportFiliereResultDTO() {}

    // Getters et Setters
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getErrors() {
        return errors;
    }

    public void setErrors(int errors) {
        this.errors = errors;
    }

    public List<FiliereImportDTO> getImportedFilieres() {
        return importedFilieres;
    }

    public void setImportedFilieres(List<FiliereImportDTO> importedFilieres) {
        this.importedFilieres = importedFilieres;
    }

    public List<FiliereImportDTO> getFailedFilieres() {
        return failedFilieres;
    }


    public void setFailedFilieres(List<FiliereImportDTO> failedFilieres) {
        this.failedFilieres = failedFilieres;
    }

    // Méthode utilitaire pour ajouter un import réussi
    public void addImportedFiliere(FiliereImportDTO dto) {
        this.importedFilieres.add(dto);
        this.success++;
        this.total++;
    }

    // Méthode utilitaire pour ajouter un import échoué
    public void addFailedFiliere(FiliereImportDTO dto) {
        this.failedFilieres.add(dto);
        this.errors++;
        this.total++;
    }
}