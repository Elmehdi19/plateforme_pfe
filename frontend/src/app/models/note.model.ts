export interface Note {
  id?: number;
  elementId: number;
  elementNom?: string;
  moduleId?: number;
  moduleIntitule?: string;
  etudiantId: number;
  etudiantNom?: string;
  etudiantMatricule?: string;
  valeur: number;
  typeEvaluation?: string;
  dateEvaluation?: string;
  session?: string;
}