package sp.plateforme_intelligente.dto;

import java.util.ArrayList;
import java.util.List;

public class ImportModuleResultDTO {
    private int total;
    private int success;
    private int errors;
    private List<ModuleImportDTO> importedModules = new ArrayList<>();
    private List<ModuleImportDTO> failedModules = new ArrayList<>();

    // Constructeur par défaut
    public ImportModuleResultDTO() {}

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

    public List<ModuleImportDTO> getImportedModules() {
        return importedModules;
    }

    public void setImportedModules(List<ModuleImportDTO> importedModules) {
        this.importedModules = importedModules;
    }

    public List<ModuleImportDTO> getFailedModules() {
        return failedModules;
    }

    public void setFailedModules(List<ModuleImportDTO> failedModules) {
        this.failedModules = failedModules;
    }

    // Méthode utilitaire pour ajouter un import réussi
    public void addImportedModule(ModuleImportDTO dto) {
        this.importedModules.add(dto);
        this.success++;
        this.total++;
    }

    // Méthode utilitaire pour ajouter un import échoué
    public void addFailedModule(ModuleImportDTO dto) {
        this.failedModules.add(dto);
        this.errors++;
        this.total++;
    }
}