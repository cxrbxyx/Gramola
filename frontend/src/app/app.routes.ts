import { Routes } from '@angular/router';
import { RegisterComponent } from './register/register'; // Ojo a la ruta de importación, ajusta si es necesario

export const routes: Routes = [
    { path: 'register', component: RegisterComponent },
    { path: '', redirectTo: '/register', pathMatch: 'full' } // Redirigir al registro por defecto para probar
];