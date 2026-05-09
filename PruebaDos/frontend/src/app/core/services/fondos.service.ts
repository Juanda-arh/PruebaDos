import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ApiResponse } from '../models/api-response.model';
import { Fondo } from '../models/fondo.model';

@Injectable({ providedIn: 'root' })
export class FondosService {
  private readonly apiUrl = 'http://localhost:8080/api/fondos';

  constructor(private readonly http: HttpClient) {}

  listar() {
    return this.http.get<ApiResponse<Fondo[]>>(this.apiUrl);
  }
}
