import { CurrencyPipe, DatePipe, NgFor } from '@angular/common';
import { Component, OnInit, computed, inject, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { AlertComponent } from '../../shared/alert.component';
import { Transaccion } from '../../core/models/transaccion.model';
import { TransaccionesService } from '../../core/services/transacciones.service';

@Component({
  standalone: true,
  imports: [NgFor, CurrencyPipe, DatePipe, FormsModule, AlertComponent],
  template: `
    <div class="mb-5 flex items-center justify-between gap-4">
      <h1 class="text-2xl font-semibold">Historial</h1>
      <select class="rounded border px-3 py-2" [ngModel]="tipo()" (ngModelChange)="tipo.set($event)">
        <option value="">Todas</option>
        <option value="APERTURA">Aperturas</option>
        <option value="CANCELACION">Cancelaciones</option>
      </select>
    </div>
    <app-alert [message]="error" />
    <app-alert [message]="info" type="info" />
    <div class="overflow-hidden rounded bg-white shadow-sm">
      <table class="w-full text-left text-sm">
        <thead class="bg-slate-100">
          <tr>
            <th class="p-3">Tipo</th>
            <th class="p-3">Fondo</th>
            <th class="p-3">Monto</th>
            <th class="p-3">Saldo</th>
            <th class="p-3">Fecha</th>
          </tr>
        </thead>
        <tbody>
          <tr *ngFor="let tx of filtradas()" class="border-t">
            <td class="p-3">{{ tx.tipo }}</td>
            <td class="p-3">{{ tx.nombreFondo }}</td>
            <td class="p-3">{{ tx.monto | currency:'COP':'symbol-narrow':'1.0-0' }}</td>
            <td class="p-3">{{ tx.saldoResultante | currency:'COP':'symbol-narrow':'1.0-0' }}</td>
            <td class="p-3">{{ tx.fecha | date:'short' }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  `
})
export class TransaccionesComponent implements OnInit {
  private readonly service = inject(TransaccionesService);
  transacciones = signal<Transaccion[]>([]);
  tipo = signal('');
  error = '';
  info = '';
  filtradas = computed(() => {
    const tipo = this.tipo();
    return tipo ? this.transacciones().filter((tx) => tx.tipo === tipo) : this.transacciones();
  });

  ngOnInit(): void {
    this.error = '';
    this.info = 'Cargando historial de transacciones...';
    this.service.listar().subscribe({
      next: (response) => {
        this.transacciones.set(response.data);
        this.info = response.data.length ? '' : 'Todavia no tienes transacciones registradas.';
      },
      error: (error: Error) => {
        this.info = '';
        this.error = error.message;
      }
    });
  }
}
