import { Routes } from '@angular/router';
import { RegisterComponent } from './register/register';
import { PaymentsComponent } from './payments/payments';
import { Callback } from './callback/callback';

export const routes: Routes = [
  { path: '', redirectTo: '/payments', pathMatch: 'full' },
  { path: 'payments', component: PaymentsComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'callback', component: Callback }
];