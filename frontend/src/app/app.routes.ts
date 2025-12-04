import { Routes } from '@angular/router';
import { RegisterComponent } from './register/register'; // Ajusta si se llama diferente
import { PaymentsComponent } from './payments/payments'; // Importamos tu componente

export const routes: Routes = [
    { path: 'register', component: RegisterComponent },
    
    // AÑADIDA: Esta ruta captura el enlace del correo
    { path: 'verify', component: PaymentsComponent },
    
    { path: '', redirectTo: '/register', pathMatch: 'full' },
    { path: '**', redirectTo: '/register' } // Redirección por defecto si la ruta falla
];