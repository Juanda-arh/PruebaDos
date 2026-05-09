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
      <h1 class="mb-5 text-2xl font-semibold">Registro</h1>
      <app-alert [message]="error" />
      <app-alert [message]="success" type="success" />
      <form class="space-y-4" [formGroup]="form" (ngSubmit)="submit()">
        <input class="w-full rounded border px-3 py-2" placeholder="Nombre" formControlName="nombre">
        <input class="w-full rounded border px-3 py-2" placeholder="Email" formControlName="email">
        <input class="w-full rounded border px-3 py-2" placeholder="Telefono" formControlName="telefono">
        <input class="w-full rounded border px-3 py-2" placeholder="Password" type="password" formControlName="password">
        <button class="w-full rounded bg-slate-950 px-4 py-2 text-white disabled:bg-slate-400" [disabled]="form.invalid || loading">
          {{ loading ? 'Creando...' : 'Registrarse' }}
        </button>
      </form>
      <a routerLink="/login" class="mt-4 block text-sm text-blue-700">Ya tengo cuenta</a>
    </section>
  `
})
export class RegisterComponent {
  private readonly fb = inject(FormBuilder);
  private readonly auth = inject(AuthService);
  private readonly router = inject(Router);
  error = '';
  success = '';
  loading = false;
  form = this.fb.nonNullable.group({
    nombre: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    telefono: ['', Validators.required],
    password: ['', [Validators.required, Validators.minLength(8)]]
  });

  submit(): void {
    this.error = '';
    this.success = '';
    this.loading = true;
    this.auth.register(this.form.getRawValue()).subscribe({
      next: () => {
        this.loading = false;
        this.success = 'Cliente creado correctamente. Ahora puedes iniciar sesion.';
        setTimeout(() => this.router.navigateByUrl('/login'), 700);
      },
      error: (error: Error) => {
        this.loading = false;
        this.error = error.message;
      }
    });
  }
}
