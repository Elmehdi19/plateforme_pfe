export interface Inscription {
  id?: number;
  etudiantId?: number;
  etudiantNom?: string;
  etudiantMatricule?: string;
  anneeUniversitaireId?: number;
  anneeUniversitaireCode?: string;
  dateInscription?: string;
  type?: string;
  semestreId?: number;
  semestreNom?: string;
  inscritAutomatiquement?: boolean;
}