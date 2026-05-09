import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router, RouterLink } from '@angular/router';
import { AlertComponent } from '../../shared/alert.component';
import { AuthService } from '../../core/services/auth.service';

@Component({
  standalone: true,
  imports: [ReactiveFormsModule, RouterLink, AlertComponent],
  template: `
    <section class="mx-auto max-w-md rounded bg-white p-6 shadow-sm">
      <h1 class="mb-5 text-2xl font-semibold">Ingresar</h1>
      <app-alert [message]="error" />
      <app-alert [message]="info" type="info" />
      <form class="space-y-4" [formGroup]="form" (ngSubmit)="submit()">
        <input class="w-full rounded border px-3 py-2" placeholder="Email" formControlName="email">
        <input class="w-full rounded border px-3 py-2" placeholder="Password" type="password" formControlName="password">
        <button class="w-full rounded bg-slate-950 px-4 py-2 text-white disabled:bg-slate-400" [disabled]="form.invalid || loading">
          {{ loading ? 'Validando...' : 'Entrar' }}
        </button>
      </form>
      <a routerLink="/register" class="mt-4 block text-sm text-blue-700">Crear cuenta</a>
    </section>
  `
})
export class LoginComponent {
  private readonly fb = inject(FormBuilder);
  private readonly auth = inject(AuthService);
  private readonly router = inject(Router);
  error = '';
  info = 'Usa el email y la clave con los que te registraste.';
  loading = false;
  form = this.fb.nonNullable.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', Validators.required]
  });

  submit(): void {
    this.error = '';
    this.info = 'Validando credenciales...';
    this.loading = true;
    this.auth.login(this.form.controls.email.value, this.form.controls.password.value).subscribe({
      next: () => this.router.navigateByUrl('/fondos'),
      error: (error: Error) => {
        this.loading = false;
        this.info = '';
        this.error = error.message;
      }
    });
  }
}
