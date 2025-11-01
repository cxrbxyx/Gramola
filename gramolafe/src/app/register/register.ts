import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserService } from '../user-service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class RegisterComponent {
  bar: string = '';
  email: string = '';
  clientID: string = '';
  clientSecret: string = '';
  pwd1: string = '';
  pwd2: string = '';

  // Variables para mostrar mensajes al usuario
  successMessage: string = '';
  errorMessage: string = '';

  constructor(private service: UserService) {}

  registrar() {
    // Resetea mensajes
    this.successMessage = '';
    this.errorMessage = '';

    if (this.pwd1 !== this.pwd2) {
      this.errorMessage = 'Las contraseñas no coinciden';
      return;
    }

    this.service
      .register(this.bar, this.email, this.pwd1, this.pwd2, this.clientID, this.clientSecret)
      .subscribe({
        next: (responseMessage) => {
          // 'responseMessage' es el string "¡Registro casi completo!..."
          this.successMessage = responseMessage; 
          console.log('Registro exitoso', responseMessage);
        },
        error: (errorResponse) => {
          // 'errorResponse.error' es el string de error del backend
          this.errorMessage = errorResponse.error; 
          console.error('Error en el registro', errorResponse);
        }
      });
  }
}