package sp.plateforme_intelligente.services;

import sp.plateforme_intelligente.models.*;
import sp.plateforme_intelligente.models.Module;
import sp.plateforme_intelligente.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DeliberationService {
    
    // ==================== REPOSITOIRES ====================
    
    @Autowired
    private NoteRepository noteRepository;
    
    @Autowired
    private ModuleRepository moduleRepository;
    
    @Autowired
    private SemestreRepository semestreRepository;
    
    @Autowired
    private ElementRepository elementRepository;
    
    @Autowired
    private InscriptionRepository inscriptionRepository;
    
    @Autowired
    private AnneeRepository anneeRepository;
    
    @Autowired
    private EtudiantRepository etudiantRepository;
    
    // ==================== CALCUL DES MOYENNES ====================
    
    /**
     * Calcule la moyenne d'un élément pour un étudiant
     */
    public Float calculerMoyenneElement(Long elementId, Long etudiantId) {
        List<Note> notes = noteRepository.findByElementIdAndEtudiantId(elementId, etudiantId);
        
        if (notes.isEmpty()) {
            return null;
        }
        
        // Regrouper par type d'évaluation
        Map<String, List<Note>> notesParType = notes.stream()
            .collect(Collectors.groupingBy(Note::getTypeEvaluation));
        
        float somme = 0;
        int count = 0;
        
        // Calcul selon les types (CC, TP, EXAMEN)
        if (notesParType.containsKey("CC")) {
            float moyenneCC = (float) notesParType.get("CC").stream()
                .mapToDouble(Note::getValeur).average().orElse(0);
            somme += moyenneCC;
            count++;
        }
        
        if (notesParType.containsKey("TP")) {
            float moyenneTP = (float) notesParType.get("TP").stream()
                .mapToDouble(Note::getValeur).average().orElse(0);
            somme += moyenneTP;
            count++;
        }
        
        if (notesParType.containsKey("EXAMEN")) {
            // Prendre la note d'examen (généralement une seule)
            float noteExamen = notesParType.get("EXAMEN").get(0).getValeur();
            somme += noteExamen;
            count++;
        }
        
        return count > 0 ? somme / count : null;
    }
    
    /**
     * Calcule la moyenne d'un module pour un étudiant
     */
    public Float calculerMoyenneModule(Long moduleId, Long etudiantId) {
        Module module = moduleRepository.findById(moduleId).orElse(null);
        if (module == null || module.getElements() == null) {
            return null;
        }
        
        float somme = 0;
        float totalCoeff = 0;
        
        for (Element element : module.getElements()) {
            Float moyenneElement = calculerMoyenneElement(element.getId(), etudiantId);
            if (moyenneElement != null) {
                float coeff = element.getCoefficient() != null ? element.getCoefficient().floatValue() : 1.0f;
                somme += coeff * moyenneElement;
                totalCoeff += coeff;
            }
        }
        
        return totalCoeff > 0 ? somme / totalCoeff : null;
    }
    
    /**
     * Calcule la moyenne d'un semestre pour un étudiant
     */
    public Float calculerMoyenneSemestre(Long semestreId, Long etudiantId) {
        Semestre semestre = semestreRepository.findById(semestreId).orElse(null);
        if (semestre == null || semestre.getModules() == null) {
            return null;
        }
        
        float somme = 0;
        float totalCoeff = 0;
        
        for (Module module : semestre.getModules()) {
            Float moyenneModule = calculerMoyenneModule(module.getId(), etudiantId);
            if (moyenneModule != null) {
                float coeff = module.getCoefficient() != null ? module.getCoefficient().floatValue() : 1.0f;
                somme += coeff * moyenneModule;
                totalCoeff += coeff;
            }
        }
        
        return totalCoeff > 0 ? somme / totalCoeff : null;
    }
    
    /**
     * Calcule la moyenne d'une année pour un étudiant
     */
    public Float calculerMoyenneAnnee(Long anneeId, Long etudiantId) {
        Annee annee = anneeRepository.findById(anneeId).orElse(null);
        if (annee == null || annee.getSemestres() == null || annee.getSemestres().isEmpty()) {
            return null;
        }
        
        float somme = 0;
        int count = 0;
        
        for (Semestre semestre : annee.getSemestres()) {
            Float moyenneSemestre = calculerMoyenneSemestre(semestre.getId(), etudiantId);
            if (moyenneSemestre != null) {
                somme += moyenneSemestre;
                count++;
            }
        }
        
        return count > 0 ? somme / count : null;
    }
    
    // ==================== GESTION DU RATTRAPAGE ====================
    
    /**
     * Calcule la note de rattrapage (max entre ordinaire et rattrapage)
     */
    public Float calculerNoteRattrapage(Long moduleId, Long etudiantId) {
        Float noteOrdinaire = calculerMoyenneModule(moduleId, etudiantId);
        
        // Récupérer les notes de rattrapage
        List<Note> notesRattrapage = noteRepository.findByEtudiantIdAndSession(etudiantId, "RATTRAPAGE")
            .stream()
            .filter(n -> n.getElement() != null && 
                         n.getElement().getModule() != null && 
                         n.getElement().getModule().getId().equals(moduleId))
            .collect(Collectors.toList());
        
        if (notesRattrapage.isEmpty()) {
            return noteOrdinaire;
        }
        
        // Calculer la moyenne des notes de rattrapage
        float sommeRattrapage = 0;
        for (Note note : notesRattrapage) {
            sommeRattrapage += note.getValeur();
        }
        float moyenneRattrapage = sommeRattrapage / notesRattrapage.size();
        
        // Retourner le max
        if (noteOrdinaire == null) return moyenneRattrapage;
        return Math.max(noteOrdinaire, moyenneRattrapage);
    }
    
    // ==================== RÉSULTATS ====================
    
    /**
     * Détermine le résultat d'un module (VALIDÉ, RATTRAPAGE, NON VALIDÉ)
     */
    public String resultatModule(Long moduleId, Long etudiantId, Float noteMinModule) {
        Float moyenne = calculerMoyenneModule(moduleId, etudiantId);
        
        if (moyenne == null) {
            return "NON RENSEIGNÉ";
        }
        
        if (moyenne >= noteMinModule) {
            return "VALIDÉ";
        } else {
            return "RATTRAPAGE";
        }
    }
    
    /**
     * Détermine le résultat d'un semestre selon les règles du cahier des charges
     */
    public String resultatSemestre(Long semestreId, Long etudiantId, 
                                   Float moyenneMinSemestre, Float noteMinModule, 
                                   Float noteEliminatoire) {
        
        Float moyenneSemestre = calculerMoyenneSemestre(semestreId, etudiantId);
        if (moyenneSemestre == null) {
            return "NON RENSEIGNÉ";
        }
        
        Semestre semestre = semestreRepository.findById(semestreId).orElse(null);
        if (semestre == null || semestre.getModules() == null) {
            return "NON RENSEIGNÉ";
        }
        
        boolean tousModulesValides = true;
        boolean moduleEliminatoire = false;
        List<Float> notesModules = new ArrayList<>();
        
        for (Module module : semestre.getModules()) {
            Float moyenneModule = calculerMoyenneModule(module.getId(), etudiantId);
            if (moyenneModule == null) {
                tousModulesValides = false;
            } else {
                notesModules.add(moyenneModule);
                if (moyenneModule < noteMinModule) {
                    tousModulesValides = false;
                    if (moyenneModule < noteEliminatoire) {
                        moduleEliminatoire = true;
                    }
                }
            }
        }
        
        // Application des règles du cahier des charges
        if (moyenneSemestre >= moyenneMinSemestre && tousModulesValides) {
            return "VALIDÉ";
        } else if (moyenneSemestre >= moyenneMinSemestre && !moduleEliminatoire) {
            return "VALIDÉ PAR COMPENSATION";
        } else {
            return "NON VALIDÉ";
        }
    }
    
    /**
     * Détermine le résultat annuel selon les règles du cahier des charges
     */
    public String resultatAnnuel(Long anneeId, Long etudiantId, 
                                 Float moyenneMinAnnuelle, Float moyenneMinSemestre,
                                 Float noteMinModule, Float noteEliminatoire) {
        
        Annee annee = anneeRepository.findById(anneeId).orElse(null);
        if (annee == null || annee.getSemestres() == null || annee.getSemestres().size() < 2) {
            return "NON RENSEIGNÉ (année incomplète)";
        }
        
        // Récupérer les deux semestres de l'année (triés par ordre)
        List<Semestre> semestres = annee.getSemestres().stream()
            .sorted(Comparator.comparing(Semestre::getOrdre))
            .collect(Collectors.toList());
        
        if (semestres.size() < 2) {
            return "NON RENSEIGNÉ (pas assez de semestres)";
        }
        
        Semestre s1 = semestres.get(0);
        Semestre s2 = semestres.get(1);
        
        // Calculer les moyennes des semestres
        Float moyenneS1 = calculerMoyenneSemestre(s1.getId(), etudiantId);
        Float moyenneS2 = calculerMoyenneSemestre(s2.getId(), etudiantId);
        
        if (moyenneS1 == null || moyenneS2 == null) {
            return "NON RENSEIGNÉ (notes manquantes)";
        }
        
        // Calculer la moyenne annuelle
        float moyenneAnnuelle = (moyenneS1 + moyenneS2) / 2;
        
        // Déterminer les résultats des semestres
        String resultatS1 = resultatSemestre(s1.getId(), etudiantId, moyenneMinSemestre, noteMinModule, noteEliminatoire);
        String resultatS2 = resultatSemestre(s2.getId(), etudiantId, moyenneMinSemestre, noteMinModule, noteEliminatoire);
        
        boolean s1Valide = resultatS1.contains("VALIDÉ");
        boolean s2Valide = resultatS2.contains("VALIDÉ");
        boolean s1Compensation = resultatS1.contains("COMPENSATION");
        boolean s2Compensation = resultatS2.contains("COMPENSATION");
        
        // Règles du cahier des charges
        if (moyenneAnnuelle >= moyenneMinAnnuelle && s1Valide && s2Valide) {
            return "VALIDÉ";
        } else if (moyenneAnnuelle >= moyenneMinAnnuelle && (s1Valide || s2Valide) && !s1Compensation && !s2Compensation) {
            return "VALIDÉ PAR COMPENSATION";
        } else if (moyenneAnnuelle >= moyenneMinAnnuelle && (s1Compensation || s2Compensation)) {
            return "VALIDÉ PAR COMPENSATION";
        } else {
            return "NON VALIDÉ";
        }
    }
    
    // ==================== GÉNÉRATION DE PV ====================
    
    /**
     * Génère le PV d'un module
     */
    public List<Map<String, Object>> genererPVModule(Long moduleId, String session, Float noteMinModule) {
        
        Module module = moduleRepository.findById(moduleId)
            .orElseThrow(() -> new RuntimeException("Module non trouvé"));
        
        // Récupérer le semestre du module
        Semestre semestre = module.getSemestre();
        if (semestre == null) {
            throw new RuntimeException("Le module n'est pas associé à un semestre");
        }
        
        // Récupérer tous les étudiants inscrits à ce semestre
        List<Inscription> inscriptions = inscriptionRepository.findBySemestreId(semestre.getId());
        
        List<Map<String, Object>> resultats = new ArrayList<>();
        
        for (Inscription inscription : inscriptions) {
            Etudiant etudiant = inscription.getEtudiant();
            if (etudiant == null) continue;
            
            Float moyenne;
            if ("RATTRAPAGE".equals(session)) {
                moyenne = calculerNoteRattrapage(moduleId, etudiant.getId());
            } else {
                moyenne = calculerMoyenneModule(moduleId, etudiant.getId());
            }
            
            String resultat = "NON RENSEIGNÉ";
            if (moyenne != null) {
                resultat = moyenne >= noteMinModule ? "VALIDÉ" : "NON VALIDÉ";
            }
            
            Map<String, Object> ligne = new HashMap<>();
            ligne.put("etudiantId", etudiant.getId());
            ligne.put("etudiantNom", etudiant.getUser() != null ? etudiant.getUser().getNom() : "N/A");
            ligne.put("etudiantMatricule", etudiant.getMatricule());
            ligne.put("moyenne", moyenne != null ? moyenne : 0);
            ligne.put("resultat", resultat);
            ligne.put("session", session);
            
            resultats.add(ligne);
        }
        
        // Trier par nom
        resultats.sort(Comparator.comparing(m -> (String) m.get("etudiantNom")));
        
        return resultats;
    }
    
    /**
     * Génère le PV d'un semestre
     */
    public List<Map<String, Object>> genererPVSemestre(Long semestreId, Long filiereId,
                                                       Float moyenneMinSemestre, 
                                                       Float noteMinModule,
                                                       Float noteEliminatoire) {
        
        Semestre semestre = semestreRepository.findById(semestreId)
            .orElseThrow(() -> new RuntimeException("Semestre non trouvé"));
        
        // Récupérer les étudiants de la filière
        List<Etudiant> etudiants = etudiantRepository.findByFiliereId(filiereId);
        
        List<Map<String, Object>> resultats = new ArrayList<>();
        
        for (Etudiant etudiant : etudiants) {
            Float moyenneSemestre = calculerMoyenneSemestre(semestreId, etudiant.getId());
            String resultat = resultatSemestre(semestreId, etudiant.getId(), 
                                               moyenneMinSemestre, noteMinModule, noteEliminatoire);
            
            // Récupérer les notes par module
            List<Map<String, Object>> notesModules = new ArrayList<>();
            for (Module module : semestre.getModules()) {
                Float moyenneModule = calculerMoyenneModule(module.getId(), etudiant.getId());
                notesModules.add(Map.of(
                    "module", module.getIntitule(),
                    "moyenne", moyenneModule != null ? moyenneModule : 0,
                    "coefficient", module.getCoefficient() != null ? module.getCoefficient() : 1.0f
                ));
            }
            
            Map<String, Object> ligne = new HashMap<>();
            ligne.put("etudiantId", etudiant.getId());
            ligne.put("etudiantNom", etudiant.getUser() != null ? etudiant.getUser().getNom() : "N/A");
            ligne.put("etudiantMatricule", etudiant.getMatricule());
            ligne.put("moyenneSemestre", moyenneSemestre != null ? moyenneSemestre : 0);
            ligne.put("resultat", resultat != null ? resultat : "NON RENSEIGNÉ");
            ligne.put("notesModules", notesModules);
            
            resultats.add(ligne);
        }
        
        return resultats;
    }
    
    // ==================== STATISTIQUES ====================
    
    /**
     * Calcule la moyenne d'une filière pour un semestre donné
     */
    public Map<String, Object> calculerMoyenneFiliere(Long filiereId, Long semestreId) {
        
        List<Etudiant> etudiants = etudiantRepository.findByFiliereId(filiereId);
        
        List<Map<String, Object>> resultats = new ArrayList<>();
        float sommeMoyennes = 0;
        int nbEtudiantsAvecNotes = 0;
        
        for (Etudiant etudiant : etudiants) {
            Float moyenne = calculerMoyenneSemestre(semestreId, etudiant.getId());
            
            Map<String, Object> ligne = new HashMap<>();
            ligne.put("etudiantId", etudiant.getId());
            ligne.put("etudiantNom", etudiant.getUser() != null ? etudiant.getUser().getNom() : "N/A");
            ligne.put("etudiantMatricule", etudiant.getMatricule());
            ligne.put("moyenne", moyenne != null ? moyenne : 0);
            
            resultats.add(ligne);
            
            if (moyenne != null) {
                sommeMoyennes += moyenne;
                nbEtudiantsAvecNotes++;
            }
        }
        
        float moyenneGenerale = nbEtudiantsAvecNotes > 0 ? sommeMoyennes / nbEtudiantsAvecNotes : 0;
        
        return Map.of(
            "etudiants", resultats,
            "moyenneGenerale", moyenneGenerale,
            "nbEtudiants", etudiants.size(),
            "nbEtudiantsAvecNotes", nbEtudiantsAvecNotes
        );
    }
    
    /**
     * Vérifie si un étudiant a validé tous les prérequis pour un module
     */
    public boolean peutValiderModule(Long moduleId, Long etudiantId, Float noteMinModule) {
        String resultat = resultatModule(moduleId, etudiantId, noteMinModule);
        return "VALIDÉ".equals(resultat);
    }
    
    /**
     * Récupère les statistiques de délibération pour un semestre
     */
    public Map<String, Object> getStatistiquesSemestre(Long semestreId, Long filiereId,
                                                       Float moyenneMinSemestre, 
                                                       Float noteMinModule,
                                                       Float noteEliminatoire) {
        
        List<Etudiant> etudiants = etudiantRepository.findByFiliereId(filiereId);
        
        int total = 0;
        int valides = 0;
        int compenses = 0;
        int nonValides = 0;
        int nonRenseignes = 0;
        
        for (Etudiant etudiant : etudiants) {
            String resultat = resultatSemestre(semestreId, etudiant.getId(), 
                                              moyenneMinSemestre, noteMinModule, noteEliminatoire);
            total++;
            
            switch (resultat) {
                case "VALIDÉ":
                    valides++;
                    break;
                case "VALIDÉ PAR COMPENSATION":
                    compenses++;
                    break;
                case "NON VALIDÉ":
                    nonValides++;
                    break;
                default:
                    nonRenseignes++;
            }
        }
        
        double tauxReussite = total > 0 ? (valides + compenses) * 100.0 / total : 0;
        
        return Map.of(
            "total", total,
            "valides", valides,
            "compenses", compenses,
            "nonValides", nonValides,
            "nonRenseignes", nonRenseignes,
            "tauxReussite", tauxReussite
        );
    }
    
    /**
     * Récupère les étudiants en risque d'échec
     */
    public List<Map<String, Object>> getEtudiantsEnRisque(Long filiereId, Long semestreId,
                                                          Float noteSeuil) {
        
        List<Etudiant> etudiants = etudiantRepository.findByFiliereId(filiereId);
        
        return etudiants.stream()
            .map(etudiant -> {
                Float moyenne = calculerMoyenneSemestre(semestreId, etudiant.getId());
                boolean enRisque = moyenne != null && moyenne < noteSeuil;
                
                Map<String, Object> result = new HashMap<>();
                result.put("etudiantId", etudiant.getId());
                result.put("etudiantNom", etudiant.getUser() != null ? etudiant.getUser().getNom() : "N/A");
                result.put("etudiantMatricule", etudiant.getMatricule());
                result.put("moyenne", moyenne != null ? moyenne : 0);
                result.put("enRisque", enRisque);
                result.put("noteSeuil", noteSeuil);
                return result;
            })
            .filter(m -> (boolean) m.get("enRisque"))
            .collect(Collectors.toList());
    }
}