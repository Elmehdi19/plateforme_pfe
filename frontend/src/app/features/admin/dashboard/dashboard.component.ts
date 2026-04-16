import { Component, OnInit, AfterViewInit, ViewChild, ElementRef, OnDestroy } from '@angular/core';
import { CommonModule } from '@angular/common';
import { forkJoin } from 'rxjs';
import { Chart, registerables } from 'chart.js';
import { StatistiquesService } from '../../../core/services/statistiques.service';
import { ChartDataService } from '../../../core/services/chart-data.service';
import { LoaderService } from '../../../shared/loader/loader.service';
import { EtudiantsService } from '../../../core/services/etudiants.service';

Chart.register(...registerables);

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit, AfterViewInit, OnDestroy {
  @ViewChild('notesChart') notesChartCanvas!: ElementRef<HTMLCanvasElement>;
  @ViewChild('decisionChart') decisionChartCanvas!: ElementRef<HTMLCanvasElement>;

  totalEtudiants = 0;
  totalFilieres = 0;
  totalModules = 0;
  totalSemestres = 6;
  lastEtudiants: any[] = [];

  private notesChart?: Chart;
  private decisionChart?: Chart;

  constructor(
    private statsService: StatistiquesService,
    private chartDataService: ChartDataService,
    private etudiantService: EtudiantsService,
    private loaderService: LoaderService
  ) {}

  ngOnInit(): void {
    this.loadStats();
    this.loadLastEtudiants();
    this.loadChartData();
  }

  ngAfterViewInit(): void {}

  ngOnDestroy(): void {
    if (this.notesChart) this.notesChart.destroy();
    if (this.decisionChart) this.decisionChart.destroy();
  }

  loadStats(): void {
    this.loaderService.show();
    this.statsService.getGlobales().subscribe({
      next: (data) => {
        this.totalEtudiants = data.totalEtudiants;
        this.totalFilieres = data.totalFilieres;
        this.totalModules = data.totalModules;
        this.loaderService.hide();
      },
      error: () => this.loaderService.hide()
    });
  }

  loadLastEtudiants(): void {
  this.etudiantService.getAll().subscribe({
    next: (etudiants) => {
      this.lastEtudiants = etudiants.slice(-5).map(e => ({
        nom: e.nom,
        filiere: e.filiere,
        semestre: e.semestre || ''   // ← champ maintenant présent
      }));
    },
    error: (err) => console.error(err)
  });
}

  loadChartData(): void {
    this.loaderService.show();
    forkJoin({
      moyennes: this.chartDataService.getMoyennesParModule(),
      decisions: this.chartDataService.getDecisionsParFiliere()
    }).subscribe({
      next: (results) => {
        if (this.notesChartCanvas) {
          this.initNotesChart(results.moyennes.labels, results.moyennes.values);
        }
        if (this.decisionChartCanvas) {
          this.initDecisionChart(results.decisions.labels, results.decisions.values);
        }
        this.loaderService.hide();
      },
      error: (err) => {
        console.error(err);
        this.loaderService.hide();
      }
    });
  }

  initNotesChart(labels: string[], values: number[]): void {
  this.notesChart = new Chart(this.notesChartCanvas.nativeElement, {
    type: 'bar',
    data: {
      labels: labels,
      datasets: [{
        label: 'Moyenne des notes',
        data: values,
        backgroundColor: 'rgba(54, 162, 235, 0.5)',
        borderColor: 'rgba(54, 162, 235, 1)',
        borderWidth: 1,
        borderRadius: 5,
        barPercentage: 0.6
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        tooltip: {
          callbacks: {
            label: (context) => `Moyenne : ${(context.raw as number).toFixed(2)}`
          }
        }
      },
      scales: {
        y: {
          beginAtZero: true,
          max: 20,
          title: { display: true, text: 'Note' }
        },
        x: {
          title: { display: true, text: 'Modules' }
        }
      }
    }
  });
}

initDecisionChart(labels: string[], values: number[]): void {
  this.decisionChart = new Chart(this.decisionChartCanvas.nativeElement, {
    type: 'doughnut',
    data: {
      labels: labels,
      datasets: [{
        data: values,
        backgroundColor: ['#4caf50', '#ffc107', '#f44336'],
        borderWidth: 0,
        hoverOffset: 10
      }]
    },
    options: {
      responsive: true,
      maintainAspectRatio: false,
      plugins: {
        legend: { position: 'top' },
        tooltip: { callbacks: { label: (ctx) => `${ctx.label}: ${ctx.raw}%` } }
      }
    }
  });
}
}