import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import AOS from 'aos';

@Component({
  selector: 'app-landing',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './landing.component.html',
  styleUrls: ['./landing.component.scss']
})
export class LandingComponent implements OnInit {
  features = [
    { title: 'Gestion des notes', description: 'Saisie, import Excel et consultation des notes par élément, module ou semestre.', icon: '📊' },
    { title: 'Délibération automatisée', description: 'Calcul des moyennes, compensation, rattrapage et génération de PV.', icon: '⚖️' },
    { title: 'Prédictions IA', description: 'Détection des étudiants à risque grâce à un modèle de machine learning.', icon: '🤖' },
    { title: 'Tableaux de bord', description: 'Statistiques en temps réel, graphiques Chart.js, indicateurs de performance.', icon: '📈' },
    { title: 'Import/Export Excel', description: 'Importation massive des données (étudiants, professeurs, notes).', icon: '📁' },
    { title: 'Sécurité JWT', description: 'Authentification sécurisée, gestion fine des rôles (admin, prof, étudiant).', icon: '🔒' }
  ];

  stats = [
    { value: 100, suffix: '%', label: 'Automatisation' },
    { value: 0, suffix: '', label: 'Erreur de calcul' },
    { value: 24, suffix: '/7', label: 'Disponibilité' },
    { value: 50, suffix: '+', label: 'Établissements' }
  ];

  currentYear = new Date().getFullYear();

  ngOnInit(): void {
    AOS.init({
      duration: 800,
      easing: 'ease-out-cubic',
      once: true,
      mirror: false
    });
  }
}