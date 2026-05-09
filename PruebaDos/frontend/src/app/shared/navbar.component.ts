import { Component, computed, inject } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { CurrencyPipe, NgIf } from '@angular/common';
import { AuthService } from '../core/services/auth.service';

@Component({
  selector: 'app-navbar',
  standalone: true,
  imports: [RouterLink, NgIf, CurrencyPipe],
  template: `
    <nav *ngIf="cliente()" class="border-b border-slate-200 bg-white">
      <div class="mx-auto flex max-w-6xl items-center justify-between px-4 py-3">
        <a routerLink="/fondos" class="text-lg font-semibold text-slate-950">BTG Fondos</a>
        <div class="flex items-center gap-4 text-sm" *ngIf="cliente() as current">
          <a routerLink="/fondos" class="text-slate-700 hover:text-slate-950">Fondos</a>
          <a routerLink="/suscripciones" class="text-slate-700 hover:text-slate-950">Suscripciones</a>
          <a routerLink="/transacciones" class="text-slate-700 hover:text-slate-950">Historial</a>
          <span class="rounded bg-emerald-50 px-2 py-1 font-medium text-emerald-800">
            {{ current.saldo | currency:'COP':'symbol-narrow':'1.0-0' }}
          </span>
          <button class="rounded bg-slate-950 px-3 py-1.5 text-white" (click)="logout()">Salir</button>
        </div>
      </div>
    </nav>
  `
})
export class NavbarComponent {
  private readonly auth = inject(AuthService);
  private readonly router = inject(Router);
  readonly cliente = computed(() => this.auth.cliente());

  logout(): void {
    this.auth.logout();
    this.router.navigateByUrl('/login');
  }
}
