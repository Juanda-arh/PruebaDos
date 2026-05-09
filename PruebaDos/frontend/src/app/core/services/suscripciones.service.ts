import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApiResponse } from '../models/api-response.model';
import { Suscripcion } from '../models/cliente.model';
import { Transaccion } from '../models/transaccion.model';

@Injectable({ providedIn: 'root' })
export class SuscripcionesService {
  private readonly apiUrl = 'http://localhost:8080/api/suscripciones';

  constructor(private readonly http: HttpClient) {}

  listar() {
    return this.http.get<ApiResponse<Suscripcion[]>>(this.apiUrl);
  }

  suscribir(idFondo: string, preferenciaNotificacion: 'EMAIL' | 'SMS') {
    return this.http.post<ApiResponse<Transaccion>>(this.apiUrl, { idFondo, preferenciaNotificacion });
  }

  cancelar(idFondo: string) {
    return this.http.delete<ApiResponse<Transaccion>>(`${this.apiUrl}/${idFondo}`);
  }
}
