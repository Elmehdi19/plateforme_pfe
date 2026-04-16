package sp.plateforme_intelligente.services;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import sp.plateforme_intelligente.dto.ElementImportDTO;
import sp.plateforme_intelligente.dto.EtudiantImportDTO;
import sp.plateforme_intelligente.dto.FiliereImportDTO;
import sp.plateforme_intelligente.dto.ImportElementResultDTO;
import sp.plateforme_intelligente.dto.ImportFiliereResultDTO;
import sp.plateforme_intelligente.dto.ImportModuleResultDTO;
import sp.plateforme_intelligente.dto.ImportProfesseurResultDTO;
import sp.plateforme_intelligente.dto.ImportResultDTO;
import sp.plateforme_intelligente.dto.ModuleImportDTO;
import sp.plateforme_intelligente.dto.ProfesseurImportDTO;
import sp.plateforme_intelligente.models.Annee;
import sp.plateforme_intelligente.models.Departement;
import sp.plateforme_intelligente.models.Element;
import sp.plateforme_intelligente.models.Etudiant;
import sp.plateforme_intelligente.models.Filiere;
import sp.plateforme_intelligente.models.Module;
import sp.plateforme_intelligente.models.Professeur;
import sp.plateforme_intelligente.models.Role;
import sp.plateforme_intelligente.models.Semestre;
import sp.plateforme_intelligente.models.User;
import sp.plateforme_intelligente.repositories.DepartementRepository;
import sp.plateforme_intelligente.repositories.ElementRepository;
import sp.plateforme_intelligente.repositories.EtudiantRepository;
import sp.plateforme_intelligente.repositories.FiliereRepository;
import sp.plateforme_intelligente.repositories.ModuleRepository;
import sp.plateforme_intelligente.repositories.ProfesseurRepository;
import sp.plateforme_intelligente.repositories.UserRepository;

@Service
public class ImportService {

    @Autowired
    private EtudiantRepository etudiantRepository;

    @Autowired
    private ProfesseurRepository professeurRepository;

    @Autowired
    private FiliereRepository filiereRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    @Autowired
    private ElementRepository elementRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartementRepository departementRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ==================== IMPORT ÉTUDIANTS ====================
    public ImportResultDTO importEtudiants(MultipartFile file) {
        ImportResultDTO result = new ImportResultDTO();
        List<EtudiantImportDTO> importedList = new ArrayList<>();
        List<EtudiantImportDTO> failedList = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            int rowNumber = 0;
            int successCount = 0;
            int errorCount = 0;

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                rowNumber++;

                if (rowNumber == 1) continue; // Ignorer l'en-tête

                EtudiantImportDTO importDTO = new EtudiantImportDTO();
                importDTO.setLigne("Ligne " + rowNumber);

                try {
                    String matricule = getCellValueAsString(currentRow.getCell(0));
                    String cne = getCellValueAsString(currentRow.getCell(1));
                    String nom = getCellValueAsString(currentRow.getCell(2));
                    String prenom = getCellValueAsString(currentRow.getCell(3));
                    String email = getCellValueAsString(currentRow.getCell(4));
                    String filiereNom = getCellValueAsString(currentRow.getCell(5));
                    String annee = getCellValueAsString(currentRow.getCell(6));

                    if (matricule == null || matricule.trim().isEmpty()) {
                        throw new Exception("Matricule obligatoire");
                    }
                    if (nom == null || nom.trim().isEmpty()) {
                        throw new Exception("Nom obligatoire");
                    }

                    if (etudiantRepository.findByMatricule(matricule).isPresent()) {
                        throw new Exception("Matricule '" + matricule + "' existe déjà");
                    }

                    // Créer l'utilisateur
                    User user = new User();
                    user.setNom(nom + " " + (prenom != null ? prenom : ""));
                    user.setEmail(email != null ? email : matricule + "@etudiant.com");
                    user.setMotDePasse(passwordEncoder.encode("default123"));
                    user.setRole(Role.ETUDIANT);
                    user.setDateCreation(LocalDate.now());

                    User savedUser = userRepository.save(user);

                    // Créer l'étudiant
                    Etudiant etudiant = new Etudiant();
                    etudiant.setMatricule(matricule);
                    etudiant.setCne(cne);
                    etudiant.setUser(savedUser);

                    // Récupérer la filière si spécifiée
                    if (filiereNom != null && !filiereNom.isEmpty()) {
                        Filiere filiere = filiereRepository.findByNom(filiereNom)
                                .orElse(null);
                        etudiant.setFiliere(filiere);
                    }

                    etudiantRepository.save(etudiant);

                    importDTO.setMatricule(matricule);
                    importDTO.setCne(cne);
                    importDTO.setNom(nom);
                    importDTO.setPrenom(prenom);
                    importDTO.setEmail(user.getEmail());
                    importDTO.setFiliere(filiereNom);
                    importDTO.setAnnee(annee);

                    importedList.add(importDTO);
                    successCount++;

                } catch (Exception e) {
                    importDTO.setErreur(e.getMessage());
                    failedList.add(importDTO);
                    errorCount++;
                }
            }

            result.setTotal(successCount + errorCount);
            result.setSuccess(successCount);
            result.setErrors(errorCount);
            result.setImportedStudents(importedList);
            result.setFailedStudents(failedList);

        } catch (IOException e) {
            throw new RuntimeException("Erreur de lecture du fichier: " + e.getMessage());
        }

        return result;
    }

    // ==================== IMPORT PROFESSEURS ====================
    public ImportProfesseurResultDTO importProfesseurs(MultipartFile file) {
        ImportProfesseurResultDTO result = new ImportProfesseurResultDTO();
        List<ProfesseurImportDTO> importedList = new ArrayList<>();
        List<ProfesseurImportDTO> failedList = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            int rowNumber = 0;
            int successCount = 0;
            int errorCount = 0;

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                rowNumber++;

                if (rowNumber == 1) continue;

                ProfesseurImportDTO importDTO = new ProfesseurImportDTO();
                importDTO.setLigne("Ligne " + rowNumber);

                try {
                    String nom = getCellValueAsString(currentRow.getCell(0));
                    String prenom = getCellValueAsString(currentRow.getCell(1));
                    String email = getCellValueAsString(currentRow.getCell(2));
                    String bureau = getCellValueAsString(currentRow.getCell(3));
                    String specialite = getCellValueAsString(currentRow.getCell(4));
                    String discipline = getCellValueAsString(currentRow.getCell(5));
                    String departement = getCellValueAsString(currentRow.getCell(6));

                    if (nom == null || nom.trim().isEmpty()) {
                        throw new Exception("Nom obligatoire");
                    }
                    if (email == null || email.trim().isEmpty()) {
                        throw new Exception("Email obligatoire");
                    }

                    if (userRepository.findByEmail(email).isPresent()) {
                        throw new Exception("Email '" + email + "' déjà utilisé");
                    }

                    // Créer l'utilisateur
                    User user = new User();
                    user.setNom(nom + " " + (prenom != null ? prenom : ""));
                    user.setEmail(email);
                    user.setMotDePasse(passwordEncoder.encode("default123"));
                    user.setRole(Role.PROFESSEUR);
                    user.setDateCreation(LocalDate.now());

                    User savedUser = userRepository.save(user);

                    // Créer le professeur
                    Professeur professeur = new Professeur();
                    professeur.setBureau(bureau);
                    professeur.setSpecialite(specialite);
                    professeur.setDiscipline(discipline);
                    professeur.setDepartement(departement);
                    professeur.setUser(savedUser);

                    professeurRepository.save(professeur);

                    importDTO.setNom(nom);
                    importDTO.setEmail(email);
                    importDTO.setBureau(bureau);
                    importDTO.setSpecialite(specialite);
                    importDTO.setDiscipline(discipline);
                    importDTO.setDepartement(departement);

                    importedList.add(importDTO);
                    successCount++;

                } catch (Exception e) {
                    importDTO.setErreur(e.getMessage());
                    failedList.add(importDTO);
                    errorCount++;
                }
            }

            result.setTotal(successCount + errorCount);
            result.setSuccess(successCount);
            result.setErrors(errorCount);
            result.setImportedProfesseurs(importedList);
            result.setFailedProfesseurs(failedList);

        } catch (IOException e) {
            throw new RuntimeException("Erreur de lecture du fichier: " + e.getMessage());
        }

        return result;
    }

    // ==================== IMPORT FILIÈRES ====================
    public ImportFiliereResultDTO importFilieres(MultipartFile file) {
        ImportFiliereResultDTO result = new ImportFiliereResultDTO();
        List<FiliereImportDTO> importedList = new ArrayList<>();
        List<FiliereImportDTO> failedList = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            int rowNumber = 0;
            int successCount = 0;
            int errorCount = 0;

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                rowNumber++;

                if (rowNumber == 1) continue;

                FiliereImportDTO importDTO = new FiliereImportDTO();
                importDTO.setLigne("Ligne " + rowNumber);

                try {
                    String nom = getCellValueAsString(currentRow.getCell(0));
                    String code = getCellValueAsString(currentRow.getCell(1));
                    String departementNom = getCellValueAsString(currentRow.getCell(2));

                    if (nom == null || nom.trim().isEmpty()) {
                        throw new Exception("Nom de filière obligatoire");
                    }

                    if (filiereRepository.findByNom(nom).isPresent()) {
                        throw new Exception("Filière '" + nom + "' existe déjà");
                    }

                    Departement departement = null;
                    if (departementNom != null && !departementNom.trim().isEmpty()) {
                        departement = departementRepository.findByNom(departementNom)
                                .orElseThrow(() -> new Exception("Département '" + departementNom + "' non trouvé"));
                    }

                    Filiere filiere = new Filiere();
                    filiere.setNom(nom);
                    filiere.setCode(code);
                    filiere.setDepartement(departement);

                    filiereRepository.save(filiere);

                    importDTO.setNom(nom);
                    importDTO.setCode(code);
                    importDTO.setDepartementNom(departementNom);

                    importedList.add(importDTO);
                    successCount++;

                } catch (Exception e) {
                    importDTO.setErreur(e.getMessage());
                    failedList.add(importDTO);
                    errorCount++;
                }
            }

            result.setTotal(successCount + errorCount);
            result.setSuccess(successCount);
            result.setErrors(errorCount);
            result.setImportedFilieres(importedList);
            result.setFailedFilieres(failedList);

        } catch (IOException e) {
            throw new RuntimeException("Erreur de lecture du fichier: " + e.getMessage());
        }

        return result;
    }

    // ==================== IMPORT MODULES ====================
    public ImportModuleResultDTO importModules(MultipartFile file) {
        ImportModuleResultDTO result = new ImportModuleResultDTO();
        List<ModuleImportDTO> importedList = new ArrayList<>();
        List<ModuleImportDTO> failedList = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            int rowNumber = 0;
            int successCount = 0;
            int errorCount = 0;

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                rowNumber++;

                if (rowNumber == 1) continue;

                ModuleImportDTO importDTO = new ModuleImportDTO();
                importDTO.setLigne("Ligne " + rowNumber);

                try {
                    String code = getCellValueAsString(currentRow.getCell(0));
                    String intitule = getCellValueAsString(currentRow.getCell(1));
                    Integer credits = getCellValueAsInteger(currentRow.getCell(2));
                    Integer semestreNum = getCellValueAsInteger(currentRow.getCell(3));
                    String filiereNom = getCellValueAsString(currentRow.getCell(4));
                    String professeurEmail = getCellValueAsString(currentRow.getCell(5));

                    if (code == null || code.trim().isEmpty()) {
                        throw new Exception("Code module obligatoire");
                    }
                    if (intitule == null || intitule.trim().isEmpty()) {
                        throw new Exception("Intitulé obligatoire");
                    }

                    if (moduleRepository.findByCode(code).isPresent()) {
                        throw new Exception("Module avec code '" + code + "' existe déjà");
                    }

                    Filiere filiere = null;
                    if (filiereNom != null && !filiereNom.trim().isEmpty()) {
                        filiere = filiereRepository.findByNom(filiereNom)
                                .orElseThrow(() -> new Exception("Filière '" + filiereNom + "' non trouvée"));
                    }

                    // Trouver le semestre (par défaut S1 si non spécifié)
                    Semestre semestre = null;
                    if (filiere != null && semestreNum != null) {
                        List<Annee> annees = filiere.getAnnees();
                        if (annees != null && !annees.isEmpty()) {
                            // Par défaut, prendre la première année
                            Annee premiereAnnee = annees.get(0);
                            semestre = premiereAnnee.getSemestres().stream()
                                    .filter(s -> Objects.equals(s.getOrdre(), semestreNum))
                                    .findFirst().orElse(null);
                        }
                    }

                    Professeur professeur = null;
                    if (professeurEmail != null && !professeurEmail.trim().isEmpty()) {
                        User user = userRepository.findByEmail(professeurEmail)
                                .orElseThrow(() -> new Exception("Professeur avec email '" + professeurEmail + "' non trouvé"));

                        professeur = professeurRepository.findByUserId(user.getId())
                                .orElseThrow(() -> new Exception("Professeur associé à cet email non trouvé"));
                    }

                    Module module = new Module();
                    module.setCode(code);
                    module.setIntitule(intitule);
                    module.setCredits(credits);
                    module.setCoefficient(1.0f); // Coefficient par défaut
                    module.setFiliere(filiere);
                    module.setSemestre(semestre);
                    module.setProfesseur(professeur);

                    moduleRepository.save(module);

                    importDTO.setCode(code);
                    importDTO.setIntitule(intitule);
                    importDTO.setCredits(credits);
                    importDTO.setSemestre(semestreNum);
                    importDTO.setFiliereNom(filiereNom);
                    importDTO.setProfesseurEmail(professeurEmail);

                    importedList.add(importDTO);
                    successCount++;

                } catch (Exception e) {
                    importDTO.setErreur(e.getMessage());
                    failedList.add(importDTO);
                    errorCount++;
                }
            }

            result.setTotal(successCount + errorCount);
            result.setSuccess(successCount);
            result.setErrors(errorCount);
            result.setImportedModules(importedList);
            result.setFailedModules(failedList);

        } catch (IOException e) {
            throw new RuntimeException("Erreur de lecture du fichier: " + e.getMessage());
        }

        return result;
    }

    // ==================== IMPORT ÉLÉMENTS ====================
    public ImportElementResultDTO importElements(MultipartFile file) {
        ImportElementResultDTO result = new ImportElementResultDTO();
        List<ElementImportDTO> importedList = new ArrayList<>();
        List<ElementImportDTO> failedList = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            int rowNumber = 0;
            int successCount = 0;
            int errorCount = 0;

            while (rows.hasNext()) {
                Row currentRow = rows.next();
                rowNumber++;

                if (rowNumber == 1) continue;

                ElementImportDTO importDTO = new ElementImportDTO();
                importDTO.setLigne("Ligne " + rowNumber);

                try {
                    String nom = getCellValueAsString(currentRow.getCell(0));
                    String code = getCellValueAsString(currentRow.getCell(1));
                    Float coefficient = getCellValueAsFloat(currentRow.getCell(2));
                    String moduleCode = getCellValueAsString(currentRow.getCell(3));
                    String professeurEmail = getCellValueAsString(currentRow.getCell(4));

                    if (nom == null || nom.trim().isEmpty()) {
                        throw new Exception("Nom de l'élément obligatoire");
                    }

                    Module module = null;
                    if (moduleCode != null && !moduleCode.trim().isEmpty()) {
                        module = moduleRepository.findByCode(moduleCode)
                                .orElseThrow(() -> new Exception("Module avec code '" + moduleCode + "' non trouvé"));
                    }

                    Professeur professeur = null;
                    if (professeurEmail != null && !professeurEmail.trim().isEmpty()) {
                        User user = userRepository.findByEmail(professeurEmail)
                                .orElseThrow(() -> new Exception("Professeur avec email '" + professeurEmail + "' non trouvé"));

                        professeur = professeurRepository.findByUserId(user.getId())
                                .orElseThrow(() -> new Exception("Professeur associé à cet email non trouvé"));
                    }

                    Element element = new Element();
                    element.setNom(nom);
                    element.setCode(code);
                    element.setCoefficient(coefficient != null ? coefficient : 1.0f);
                    element.setModule(module);
                    element.setProfesseur(professeur);

                    elementRepository.save(element);

                    importDTO.setNom(nom);
                    importDTO.setCode(code);
                    importDTO.setCoefficient(coefficient);
                    importDTO.setModuleCode(moduleCode);
                    importDTO.setProfesseurEmail(professeurEmail);

                    importedList.add(importDTO);
                    successCount++;

                } catch (Exception e) {
                    importDTO.setErreur(e.getMessage());
                    failedList.add(importDTO);
                    errorCount++;
                }
            }

            result.setTotal(successCount + errorCount);
            result.setSuccess(successCount);
            result.setErrors(errorCount);
            result.setImportedElements(importedList);
            result.setFailedElements(failedList);

        } catch (IOException e) {
            throw new RuntimeException("Erreur de lecture du fichier: " + e.getMessage());
        }

        return result;
    }

    // Méthodes utilitaires pour lire les cellules
    private String getCellValueAsString(Cell cell) {
        if (cell == null) return null;

        try {
            switch (cell.getCellType()) {
                case STRING:
                    return cell.getStringCellValue();
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        return cell.getDateCellValue().toString();
                    } else {
                        double numericValue = cell.getNumericCellValue();
                        if (numericValue == Math.floor(numericValue)) {
                            return String.valueOf((long) numericValue);
                        }
                        return String.valueOf(numericValue);
                    }
                case BOOLEAN:
                    return String.valueOf(cell.getBooleanCellValue());
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    private Integer getCellValueAsInteger(Cell cell) {
        if (cell == null) return null;

        try {
            if (cell.getCellType() == CellType.NUMERIC) {
                return (int) cell.getNumericCellValue();
            } else if (cell.getCellType() == CellType.STRING) {
                String value = cell.getStringCellValue().trim();
                if (!value.isEmpty()) {
                    return Integer.valueOf(value);
                }
            }
        } catch (NumberFormatException e) {
            // Ignorer
        }
        return null;
    }

    private Float getCellValueAsFloat(Cell cell) {
        if (cell == null) return null;

        try {
            if (cell.getCellType() == CellType.NUMERIC) {
                return (float) cell.getNumericCellValue();
            } else if (cell.getCellType() == CellType.STRING) {
                String value = cell.getStringCellValue().trim();
                if (!value.isEmpty()) {
                    return Float.valueOf(value);
                }
            }
        } catch (NumberFormatException e) {
            // Ignorer
        }
        return null;
    }
}