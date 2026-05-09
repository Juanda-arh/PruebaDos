import { HttpClient } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';
import { tap } from 'rxjs';
import { ApiResponse } from '../models/api-response.model';
import { Cliente } from '../models/cliente.model';

interface AuthResponse {
  token: string;
  cliente: Cliente;
}

@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly apiUrl = 'http://localhost:8080/api/auth';
  readonly cliente = signal<Cliente | null>(null);

  constructor(private readonly http: HttpClient) {
    if (this.token) {
      this.me().subscribe();
    }
  }

  get token(): string | null {
    return localStorage.getItem('btg_token');
  }

  login(email: string, password: string) {
    return this.http.post<ApiResponse<AuthResponse>>(`${this.apiUrl}/login`, { email, password }).pipe(
      tap((response) => {
        localStorage.setItem('btg_token', response.data.token);
        this.cliente.set(response.data.cliente);
      })
    );
  }

  register(payload: { email: string; password: string; nombre: string; telefono: string }) {
    return this.http.post<ApiResponse<Cliente>>(`${this.apiUrl}/register`, payload);
  }

  me() {
    return this.http.get<ApiResponse<Cliente>>(`${this.apiUrl}/me`).pipe(
      tap((response) => this.cliente.set(response.data))
    );
  }

  logout(): void {
    localStorage.removeItem('btg_token');
    this.cliente.set(null);
  }
}
