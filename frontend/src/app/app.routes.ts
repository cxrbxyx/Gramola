import { Routes } from '@angular/router';
import { RegisterComponent } from './register/register';
import { VerifyComponent } from './verify/verify'; // Importación nueva

export const routes: Routes = [
  // Ruta por defecto
  { path: '', redirectTo: '/register', pathMatch: 'full' }, 
  
  { path: 'register', component: RegisterComponent },
  
  // Nueva ruta para verificación
  { path: 'verify', component: VerifyComponent }
];