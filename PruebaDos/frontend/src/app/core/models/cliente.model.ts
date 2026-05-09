export interface Suscripcion {
  idFondo: string;
  nombreFondo: string;
  montoVinculado: number;
  fechaApertura: string;
  idTransaccion: string;
}

export interface Cliente {
  id: string;
  email: string;
  nombre: string;
  saldo: number;
  preferenciaNotificacion: 'EMAIL' | 'SMS' | null;
  telefono: string;
  roles: string[];
  suscripcionesActivas: Suscripcion[];
}
