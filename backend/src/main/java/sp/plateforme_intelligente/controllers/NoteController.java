package sp.plateforme_intelligente.controllers;

import sp.plateforme_intelligente.dto.NoteDTO;
import sp.plateforme_intelligente.dto.NoteRequestDTO;
import sp.plateforme_intelligente.models.Note;
import sp.plateforme_intelligente.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notes")
@CrossOrigin(origins = "*")
public class NoteController {
    
    @Autowired
    private NoteService noteService;
    
    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<List<NoteDTO>> getAllNotes() {
        return ResponseEntity.ok(noteService.getAllNotes());
    }
    
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<NoteDTO> getNoteById(@PathVariable Long id) {
        return ResponseEntity.ok(noteService.getNoteById(id));
    }
    
    @GetMapping("/etudiant/{etudiantId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR') or hasRole('ETUDIANT')")
    public ResponseEntity<List<NoteDTO>> getNotesByEtudiant(@PathVariable Long etudiantId) {
        return ResponseEntity.ok(noteService.getNotesByEtudiant(etudiantId));
    }
    
    @GetMapping("/element/{elementId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<List<NoteDTO>> getNotesByElement(@PathVariable Long elementId) {
        return ResponseEntity.ok(noteService.getNotesByElement(elementId));
    }
    
    @GetMapping("/etudiant/{etudiantId}/session/{session}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<List<NoteDTO>> getNotesByEtudiantAndSession(
            @PathVariable Long etudiantId,
            @PathVariable String session) {
        return ResponseEntity.ok(noteService.getNotesByEtudiantAndSession(etudiantId, session));
    }
    
    @PostMapping
     @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')") 
    public ResponseEntity<NoteDTO> createNote(@RequestBody NoteRequestDTO noteRequest) {
        NoteDTO created = noteService.createNote(noteRequest);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<NoteDTO> updateNote(@PathVariable Long id, @RequestBody Note note) {
        return ResponseEntity.ok(noteService.updateNote(id, note));
    }
    
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<?> deleteNote(@PathVariable Long id) {
        noteService.deleteNote(id);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/import/{elementId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<?> importerNotes(
            @PathVariable Long elementId,
            @RequestBody List<Map<String, Object>> notesData) {
        return ResponseEntity.ok(noteService.importerNotes(elementId, notesData));
    }
    
    @GetMapping("/moyenne/module/{moduleId}/etudiant/{etudiantId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('PROFESSEUR')")
    public ResponseEntity<?> calculerMoyenneModule(
            @PathVariable Long moduleId,
            @PathVariable Long etudiantId,
            @RequestParam Float noteMinModule) {
        return ResponseEntity.ok(noteService.calculerMoyenneModule(etudiantId, moduleId, noteMinModule));
    }
}