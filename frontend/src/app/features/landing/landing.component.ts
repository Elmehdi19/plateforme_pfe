import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-landing',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.scss']
})
export class LandingComponent {
  features = [
    { title: 'Gestion des notes', description: 'Saisie, modification et consultation des notes par module.', icon: '📊' },
    { title: 'Délibération automatique', description: 'Calcul des moyennes, compensation, génération de PV.', icon: '⚖️' },
    { title: 'Prédictions IA', description: 'Détection des étudiants à risque grâce à l’intelligence artificielle.', icon: '🤖' },
    { title: 'Tableaux de bord', description: 'Statistiques visuelles et graphiques interactifs.', icon: '📈' },
    { title: 'Import Excel', description: 'Importation massive des données (étudiants, professeurs, etc.).', icon: '📁' },
    { title: 'Sécurité JWT', description: 'Authentification sécurisée et gestion fine des rôles.', icon: '🔒' }
  ];
}