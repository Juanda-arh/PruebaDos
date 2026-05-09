import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

export const roleGuard: CanActivateFn = () => {
  const auth = inject(AuthService);
  const router = inject(Router);
  if (auth.cliente()?.roles.includes('ROLE_ADMIN')) {
    return true;
  }
  return router.createUrlTree(['/fondos']);
};
