import { CurrencyPipe, NgFor } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AlertComponent } from '../../shared/alert.component';
import { Fondo } from '../../core/models/fondo.model';
import { FondosService } from '../../core/services/fondos.service';
import { SuscripcionesService } from '../../core/services/suscripciones.service';
import { AuthService } from '../../core/services/auth.service';

@Component({
  standalone: true,
  imports: [NgFor, CurrencyPipe, FormsModule, AlertComponent],
  template: `
    <header class="mb-5 flex items-center justify-between">
      <h1 class="text-2xl font-semibold">Fondos disponibles</h1>
    </header>
    <app-alert [message]="error" />
    <app-alert [message]="success" type="success" />
    <app-alert [message]="info" type="info" />
    <div class="grid gap-4 md:grid-cols-2">
      <article *ngFor="let fondo of fondos" class="rounded bg-white p-4 shadow-sm">
        <div class="flex items-start justify-between gap-4">
          <div>
            <h2 class="font-semibold">{{ fondo.nombre }}</h2>
            <p class="mt-1 text-sm text-slate-600">{{ fondo.categoria }}</p>
          </div>
          <span class="rounded bg-slate-100 px-2 py-1 text-sm">
            {{ fondo.montoMinimo | currency:'COP':'symbol-narrow':'1.0-0' }}
          </span>
        </div>
        <label class="mt-4 block text-sm font-medium text-slate-700" [for]="'preferencia-' + fondo.id">
          Notificacion para esta suscripcion
        </label>
        <select class="mt-1 w-full rounded border px-3 py-2 text-sm"
                [id]="'preferencia-' + fondo.id"
                [ngModel]="preferencias[fondo.id] ?? 'EMAIL'"
                (ngModelChange)="preferencias[fondo.id] = $event">
          <option value="EMAIL">Email</option>
          <option value="SMS">SMS</option>
        </select>
        <button class="mt-4 rounded bg-blue-700 px-3 py-2 text-sm text-white disabled:bg-slate-400"
                [disabled]="loadingId === fondo.id"
                (click)="suscribir(fondo.id)">
          {{ loadingId === fondo.id ? 'Procesando...' : 'Suscribirse' }}
        </button>
      </article>
    </div>
  `
})
export class FondosComponent implements OnInit {
  private readonly fondosService = inject(FondosService);
  private readonly suscripcionesService = inject(SuscripcionesService);
  private readonly auth = inject(AuthService);
  fondos: Fondo[] = [];
  error = '';
  success = '';
  info = '';
  loadingId = '';
  preferencias: Record<string, 'EMAIL' | 'SMS'> = {};

  ngOnInit(): void {
    this.cargar();
  }

  cargar(): void {
    this.error = '';
    this.success = '';
    this.info = 'Cargando fondos disponibles...';
    this.fondosService.listar().subscribe({
      next: (response) => {
        this.fondos = response.data;
        this.info = response.data.length ? '' : 'No hay fondos disponibles en este momento.';
      },
      error: (error: Error) => {
        this.info = '';
        this.error = error.message;
      }
    });
  }

  suscribir(idFondo: string): void {
    const fondo = this.fondos.find((item) => item.id === idFondo);
    const preferencia = this.preferencias[idFondo] ?? 'EMAIL';
    this.error = '';
    this.success = '';
    this.info = `Intentando suscribirte a ${fondo?.nombre ?? 'este fondo'} con notificacion por ${preferencia}...`;
    this.loadingId = idFondo;
    this.suscripcionesService.suscribir(idFondo, preferencia).subscribe({
      next: (response) => {
        this.loadingId = '';
        this.info = '';
        this.success = `${response.message}. Nuevo saldo actualizado en la barra superior.`;
        this.auth.me().subscribe();
      },
      error: (error: Error) => {
        this.loadingId = '';
        this.info = '';
        this.error = error.message;
      }
    });
  }
}
