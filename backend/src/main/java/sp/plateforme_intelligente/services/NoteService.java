package sp.plateforme_intelligente.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sp.plateforme_intelligente.dto.NoteDTO;
import sp.plateforme_intelligente.dto.NoteRequestDTO;
import sp.plateforme_intelligente.models.Element;
import sp.plateforme_intelligente.models.Etudiant;
import sp.plateforme_intelligente.models.Note;
import sp.plateforme_intelligente.repositories.ElementRepository;
import sp.plateforme_intelligente.repositories.EtudiantRepository;
import sp.plateforme_intelligente.repositories.NoteRepository;

@Service
public class NoteService {
    
    @Autowired
    private NoteRepository noteRepository;
    
    @Autowired
    private ElementRepository elementRepository;
    
    @Autowired
    private EtudiantRepository etudiantRepository;
    
    @Autowired
    private DeliberationService deliberationService;
    
    public List<NoteDTO> getAllNotes() {
        return noteRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public NoteDTO getNoteById(Long id) {
        Note note = noteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Note non trouvée"));
        return convertToDTO(note);
    }
    
    @Transactional
    public NoteDTO createNote(NoteRequestDTO request) {
        // Vérifier l'étudiant
        Etudiant etudiant = etudiantRepository.findById(request.getEtudiantId())
            .orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));
        // Vérifier l'élément
        Element element = elementRepository.findById(request.getElementId())
            .orElseThrow(() -> new RuntimeException("Élément non trouvé"));
        
        Note note = new Note();
        note.setEtudiant(etudiant);
        note.setElement(element);
        note.setValeur(request.getValeur());
        note.setTypeEvaluation(request.getTypeEvaluation());
        note.setSession(request.getSession());
        note.setDateEvaluation(LocalDate.now());
        
        Note saved = noteRepository.save(note);
        return convertToDTO(saved);
    }
    
    @Transactional
    public NoteDTO updateNote(Long id, Note noteDetails) {
        Note note = noteRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Note non trouvée"));
        
        note.setValeur(noteDetails.getValeur());
        note.setTypeEvaluation(noteDetails.getTypeEvaluation());
        note.setSession(noteDetails.getSession());
        
        Note updated = noteRepository.save(note);
        return convertToDTO(updated);
    }
    
    @Transactional
    public void deleteNote(Long id) {
        if (!noteRepository.existsById(id)) {
            throw new RuntimeException("Note non trouvée");
        }
        noteRepository.deleteById(id);
    }
    
    public List<NoteDTO> getNotesByEtudiant(Long etudiantId) {
        return noteRepository.findByEtudiantId(etudiantId).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public List<NoteDTO> getNotesByElement(Long elementId) {
        return noteRepository.findByElementId(elementId).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public List<NoteDTO> getNotesByEtudiantAndSession(Long etudiantId, String session) {
        return noteRepository.findByEtudiantIdAndSession(etudiantId, session).stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    @Transactional
    public List<Note> importerNotes(Long elementId, List<Map<String, Object>> notesData) {
        Element element = elementRepository.findById(elementId)
            .orElseThrow(() -> new RuntimeException("Élément non trouvé"));
        
        return notesData.stream()
            .map(data -> {
                Long etudiantId = Long.valueOf(data.get("etudiantId").toString());
                Etudiant etudiant = etudiantRepository.findById(etudiantId)
                    .orElseThrow(() -> new RuntimeException("Étudiant non trouvé"));
                
                Float valeur = Float.valueOf(data.get("valeur").toString());
                String type = data.get("type").toString();
                String session = data.getOrDefault("session", "ORDINAIRE").toString();
                
                Note note = new Note();
                note.setElement(element);
                note.setEtudiant(etudiant);
                note.setValeur(valeur);
                note.setTypeEvaluation(type);
                note.setSession(session);
                note.setDateEvaluation(LocalDate.now());
                
                return noteRepository.save(note);
            })
            .collect(Collectors.toList());
    }
    
    public Map<String, Object> calculerMoyenneModule(Long etudiantId, Long moduleId, Float noteMinModule) {
        Float moyenne = deliberationService.calculerMoyenneModule(moduleId, etudiantId);
        String resultat = deliberationService.resultatModule(moduleId, etudiantId, noteMinModule);
        
        return Map.of(
            "etudiantId", etudiantId,
            "moduleId", moduleId,
            "moyenne", moyenne != null ? moyenne : 0,
            "resultat", resultat != null ? resultat : "NON RENSEIGNÉ"
        );
    }
    
    private NoteDTO convertToDTO(Note note) {
        NoteDTO dto = new NoteDTO();
        dto.setId(note.getId());
        dto.setValeur(note.getValeur());
        dto.setTypeEvaluation(note.getTypeEvaluation());
        dto.setDateEvaluation(note.getDateEvaluation());
        dto.setSession(note.getSession());
        
        if (note.getElement() != null) {
            dto.setElementId(note.getElement().getId());
            dto.setElementNom(note.getElement().getNom());
            
            if (note.getElement().getModule() != null) {
                dto.setModuleId(note.getElement().getModule().getId());
                dto.setModuleIntitule(note.getElement().getModule().getIntitule());
            }
        }
        
        if (note.getEtudiant() != null) {
            dto.setEtudiantId(note.getEtudiant().getId());
            dto.setEtudiantMatricule(note.getEtudiant().getMatricule());
            
            if (note.getEtudiant().getUser() != null) {
                dto.setEtudiantNom(note.getEtudiant().getUser().getNom());
            }
        }
        
        return dto;
    }
}