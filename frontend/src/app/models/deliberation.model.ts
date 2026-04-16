export interface ResultatModule {
  etudiantId: number;
  moduleId: number;
  moyenne: number;
  resultat: string; // VALIDÉ, RATTRAPAGE, NON RENSEIGNÉ
}

export interface ResultatSemestre {
  etudiantId: number;
  semestreId: number;
  moyenne: number;
  resultat: string; // VALIDÉ, VALIDÉ PAR COMPENSATION, NON VALIDÉ
}

export interface StatistiquesSemestre {
  total: number;
  valides: number;
  compenses: number;
  nonValides: number;
  nonRenseignes: number;
  tauxReussite: number;
}