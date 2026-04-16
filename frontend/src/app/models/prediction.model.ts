export interface Prediction {
  id?: number;
  etudiantId: number;
  etudiantNom?: string;
  etudiantMatricule?: string;
  scoreRisque?: number;
  niveauRisque?: string;
  datePrediction?: string;
  modeleVersion?: string;
}