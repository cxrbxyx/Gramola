import { Routes } from '@angular/router';
import { RegisterComponent } from './register/register';


export const routes: Routes = [
  // Ruta por defecto
  { path: '', redirectTo: '/register', pathMatch: 'full' }, 
  
  { path: 'register', component: RegisterComponent },
  
  // Nueva ruta para verificación
];