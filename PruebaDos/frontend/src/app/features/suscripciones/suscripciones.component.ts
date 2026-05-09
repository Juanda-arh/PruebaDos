import { CurrencyPipe, DatePipe, NgFor } from '@angular/common';
import { Component, OnInit, inject } from '@angular/core';
import { AlertComponent } from '../../shared/alert.component';
import { Suscripcion } from '../../core/models/cliente.model';
import { AuthService } from '../../core/services/auth.service';
import { SuscripcionesService } from '../../core/services/suscripciones.service';

@Component({
  standalone: true,
  imports: [NgFor, CurrencyPipe, DatePipe, AlertComponent],
  template: `
    <h1 class="mb-5 text-2xl font-semibold">Suscripciones activas</h1>
    <app-alert [message]="error" />
    <app-alert [message]="success" type="success" />
    <app-alert [message]="info" type="info" />
    <div class="overflow-hidden rounded bg-white shadow-sm">
      <table class="w-full text-left text-sm">
        <thead class="bg-slate-100">
          <tr>
            <th class="p-3">Fondo</th>
            <th class="p-3">Monto</th>
            <th class="p-3">Fecha</th>
            <th class="p-3"></th>
          </tr>
        </thead>
        <tbody>
          <tr *ngFor="let suscripcion of suscripciones" class="border-t">
            <td class="p-3">{{ suscripcion.nombreFondo }}</td>
            <td class="p-3">{{ suscripcion.montoVinculado | currency:'COP':'symbol-narrow':'1.0-0' }}</td>
            <td class="p-3">{{ suscripcion.fechaApertura | date:'short' }}</td>
            <td class="p-3 text-right">
              <button class="rounded bg-red-700 px-3 py-1.5 text-white disabled:bg-slate-400"
                      [disabled]="loadingId === suscripcion.idFondo"
                      (click)="cancelar(suscripcion.idFondo)">
                {{ loadingId === suscripcion.idFondo ? 'Cancelando...' : 'Cancelar' }}
              </button>
            </td>
          </tr>
        </tbody>
      </table>
    </div>
  `
})
export class SuscripcionesComponent implements OnInit {
  private readonly service = inject(SuscripcionesService);
  private readonly auth = inject(AuthService);
  suscripciones: Suscripcion[] = [];
  error = '';
  success = '';
  info = '';
  loadingId = '';

  ngOnInit(): void {
    this.cargar();
  }

  cargar(): void {
    this.error = '';
    this.info = 'Consultando tus suscripciones activas...';
    this.service.listar().subscribe({
      next: (response) => {
        this.suscripciones = response.data;
        this.info = response.data.length ? '' : 'Aun no tienes suscripciones activas.';
      },
      error: (error: Error) => {
        this.info = '';
        this.error = error.message;
      }
    });
  }

  cancelar(idFondo: string): void {
    const suscripcion = this.suscripciones.find((item) => item.idFondo === idFondo);
    this.error = '';
    this.success = '';
    this.info = `Cancelando ${suscripcion?.nombreFondo ?? 'la suscripcion'}...`;
    this.loadingId = idFondo;
    this.service.cancelar(idFondo).subscribe({
      next: (response) => {
        this.loadingId = '';
        this.success = `${response.message}. El monto fue devuelto a tu saldo.`;
        this.cargar();
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
