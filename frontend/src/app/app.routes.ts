import { Routes } from '@angular/router';
import { RegisterComponent } from './register/register';
import { PaymentComponent } from './payments/payments';

export const routes: Routes = [
    { path: 'register', component: RegisterComponent },
    // Ruta para el pago, espera un parámetro opcional si lo deseas manejar así, 
    // o simplemente por query params como hemos hecho (?token=...)
    { path: 'payment', component: PaymentComponent },
    
    { path: '', redirectTo: '/register', pathMatch: 'full' }
];