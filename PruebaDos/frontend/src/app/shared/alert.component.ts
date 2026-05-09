import { NgClass, NgIf } from '@angular/common';
import { Component, Input } from '@angular/core';

type AlertType = 'error' | 'success' | 'info';

@Component({
  selector: 'app-alert',
  standalone: true,
  imports: [NgClass, NgIf],
  template: `
    <div *ngIf="message" class="mb-4 rounded border px-3 py-2 text-sm" [ngClass]="classes">
      {{ message }}
    </div>
  `
})
export class AlertComponent {
  @Input() message = '';
  @Input() type: AlertType = 'error';

  get classes(): string {
    if (this.type === 'success') {
      return 'border-emerald-200 bg-emerald-50 text-emerald-800';
    }
    if (this.type === 'info') {
      return 'border-blue-200 bg-blue-50 text-blue-800';
    }
    return 'border-red-200 bg-red-50 text-red-800';
  }
}
