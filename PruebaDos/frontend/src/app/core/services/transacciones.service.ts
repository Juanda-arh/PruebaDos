import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApiResponse } from '../models/api-response.model';
import { Transaccion } from '../models/transaccion.model';

@Injectable({ providedIn: 'root' })
export class TransaccionesService {
  private readonly apiUrl = 'http://localhost:8080/api/transacciones';

  constructor(private readonly http: HttpClient) {}

  listar() {
    return this.http.get<ApiResponse<Transaccion[]>>(this.apiUrl);
  }
}
