import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';
import { LoginComponent } from './features/auth/login.component';
import { RegisterComponent } from './features/auth/register.component';
import { FondosComponent } from './features/fondos/fondos.component';
import { SuscripcionesComponent } from './features/suscripciones/suscripciones.component';
import { TransaccionesComponent } from './features/transacciones/transacciones.component';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'fondos', component: FondosComponent, canActivate: [authGuard] },
  { path: 'suscripciones', component: SuscripcionesComponent, canActivate: [authGuard] },
  { path: 'transacciones', component: TransaccionesComponent, canActivate: [authGuard] },
  { path: '', pathMatch: 'full', redirectTo: 'fondos' },
  { path: '**', redirectTo: 'fondos' }
];
