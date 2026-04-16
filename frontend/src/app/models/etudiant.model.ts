export interface Etudiant {
  id?: number;
  matricule: string;
  cne?: string;
  cin?: string;
  nom?: string;
  email?: string;
  nationalite?: string;
  sexe?: string;
  dateNaissance?: string;
  lieuNaissance?: string;
  adresse?: string;
  ville?: string;
  telephone?: string;
  bacAnnee?: string;
  bacSerie?: string;
  bacMention?: string;
  bacLycée?: string;
  bacLieuObtention?: string;
  bacAcademie?: string;
  filiere?: string;
  filiereId?: number;
  semestre?: string; // ← nouveau champ
}