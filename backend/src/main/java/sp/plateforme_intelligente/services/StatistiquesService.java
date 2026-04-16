package sp.plateforme_intelligente.services;

import sp.plateforme_intelligente.dto.StatistiquesDTO;
import sp.plateforme_intelligente.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatistiquesService {
    
    @Autowired
    private EtudiantRepository etudiantRepository;
    
    @Autowired
    private ProfesseurRepository professeurRepository;
    
    @Autowired
    private FiliereRepository filiereRepository;
    
    @Autowired
    private ModuleRepository moduleRepository;
    
    @Autowired
    private InscriptionRepository inscriptionRepository;
    
    @Autowired
    private NoteRepository noteRepository;
    
    @Autowired
    private PredictionRepository predictionRepository;
    
    public StatistiquesDTO getStatistiques() {
        StatistiquesDTO stats = new StatistiquesDTO();
        
        stats.setTotalEtudiants(etudiantRepository.count());
        stats.setTotalProfesseurs(professeurRepository.count());
        stats.setTotalFilieres(filiereRepository.count());
        stats.setTotalModules(moduleRepository.count());
        stats.setTotalInscriptions(inscriptionRepository.count());
        stats.setTotalNotes(noteRepository.count());
        
        stats.setEtudiantsRisqueEleve(predictionRepository.countByNiveauRisque("ELEVE"));
        stats.setEtudiantsRisqueMoyen(predictionRepository.countByNiveauRisque("MOYEN"));
        stats.setEtudiantsRisqueFaible(predictionRepository.countByNiveauRisque("FAIBLE"));
        
        // Calculer la moyenne générale (optionnel)
        // stats.setMoyenneGenerale(...);
        
        return stats;
    }
}