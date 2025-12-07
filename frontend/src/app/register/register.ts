import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserService } from '../user-service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './register.html',
  styleUrls: ['../../styles.css'],
})
export class RegisterComponent {
  bar = '';
  email = '';
  clientId = '';
  clientSecret = '';
  pwd1 = '';
  pwd2 = '';

  registroExitoso = false;
  errorMessage = '';

  constructor(private userService: UserService) {}

  register() {
    console.log('Intento de registro...');

    // Validación básica
    if (!this.bar || !this.email || !this.pwd1 || !this.pwd2 || !this.clientId || !this.clientSecret) {
      this.errorMessage = 'Por favor, completa todos los campos obligatorios.';
      return;
    }

    if (this.pwd1 !== this.pwd2) {
      this.errorMessage = 'Las contraseñas no coinciden.';
      return;
    }

    this.errorMessage = '';
    const user = {
      name: this.bar,
      email: this.email,
      password: this.pwd1,
      spotifyClientId: this.clientId,
      spotifyClientSecret: this.clientSecret,
    };

    console.log('Enviando datos al backend:', user);

    this.userService.register(this.bar,this.email,this.pwd1,this.pwd2,this.clientId,this.clientSecret).subscribe({
      next: (response) => {
        console.log('Respuesta exitosa:', response);
        this.registroExitoso = true;
      },
      error: (error) => {
        console.error('Error en registro:', error);
        if (error.status === 0) {
          this.errorMessage = 'No se pudo conectar con el servidor. ¿Está encendido el Backend?';
        } else {
          this.errorMessage =
            error.error || 'Hubo un problema al crear la cuenta. Verifica los datos.';
        }
      },
    });
  }
}
