export interface Module {
  id?: number;
  code: string;
  intitule: string;
  credits?: number;
  coefficient?: number;
  semestreId?: number;
  semestreNom?: string;
  filiereId?: number;
  filiereNom?: string;
  professeurId?: number;
  professeurNom?: string;
}