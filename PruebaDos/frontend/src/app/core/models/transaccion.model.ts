export interface Transaccion {
  id: string;
  idCliente: string;
  idFondo: string;
  nombreFondo: string;
  tipo: 'APERTURA' | 'CANCELACION';
  monto: number;
  fecha: string;
  saldoResultante: number;
}
