import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { UserService } from '../user-service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './register.html',
  styleUrls: ['../../styles.css'] // Usamos el estilo global
})
export class RegisterComponent {
  user = {
    name: '',
    email: '',
    password: '',
    role: 'BAR' // Valor por defecto
  };

  isRegistered = false; // Estado para controlar la vista de éxito
  errorMessage = '';

  constructor(private userService: UserService) {}

  onSubmit() {
    this.errorMessage = ''; // Limpiar errores previos
    
    this.userService.register(this.user).subscribe({
      next: (response) => {
        console.log('Usuario registrado:', response);
        this.isRegistered = true; // Cambiamos el estado a registrado
      },
      error: (error) => {
        console.error('Error en registro:', error);
        this.errorMessage = 'Hubo un problema al crear la cuenta. Inténtalo de nuevo.';
      }
    });
  }
}