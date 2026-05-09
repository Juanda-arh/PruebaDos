import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { catchError, throwError } from 'rxjs';

export const errorInterceptor: HttpInterceptorFn = (request, next) => {
  return next(request).pipe(
    catchError((error: HttpErrorResponse) => {
      return throwError(() => new Error(buildErrorMessage(error)));
    })
  );
};

function buildErrorMessage(error: HttpErrorResponse): string {
  if (error.status === 0) {
    return 'No se pudo conectar con el backend. Verifica que Spring Boot este corriendo en http://localhost:8080.';
  }

  const backendMessage = error.error?.message;
  const fieldErrors = error.error?.data;
  if (fieldErrors && typeof fieldErrors === 'object' && !Array.isArray(fieldErrors)) {
    const details = Object.entries(fieldErrors)
      .map(([field, message]) => `${field}: ${message}`)
      .join('; ');
    return `${backendMessage ?? 'Datos invalidos'}: ${details}`;
  }

  if (backendMessage) {
    return backendMessage;
  }

  if (error.status === 401) {
    return 'No fue posible iniciar sesion. Revisa el email y la clave.';
  }
  if (error.status === 403) {
    return 'No tienes permisos para ejecutar esta accion.';
  }
  if (error.status === 404) {
    return 'No se encontro el recurso solicitado.';
  }
  if (error.status >= 500) {
    return 'El servidor tuvo un error interno. Revisa los logs del backend.';
  }
  return 'Ocurrio un error inesperado.';
}
