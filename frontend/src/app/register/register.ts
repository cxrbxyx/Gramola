import { Component } from '@angular/core';
import { FormsModule, NgForm } from '@angular/forms';
import { UserService } from '../user-service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './register.html',
  styleUrls: ['../../styles.css'] 
})
export class RegisterComponent {
  user = {
    name: '',
    email: '',
    password: '',
    role: 'BAR',
    spotifyClientId: '',
    spotifyClientSecret: ''
  };

  isRegistered = false; 
  errorMessage = '';

  constructor(private userService: UserService) {}

  onSubmit(form: NgForm) {
    console.log('Intento de registro...');
    
    // 1. Validación manual: Si el formulario es inválido, mostramos errores y paramos.
    if (form.invalid) {
      console.warn('El formulario es inválido. Campos erróneos detectados.');
      this.errorMessage = 'Por favor, completa todos los campos obligatorios correctamente.';
      
      // Truco: Marca todos los campos como "tocados" para que salten las alertas visuales en el HTML
      Object.keys(form.controls).forEach(key => {
        form.controls[key].markAsTouched();
      });
      return;
    }

    // 2. Si es válido, procedemos al envío
    this.errorMessage = ''; 
    console.log('Enviando datos al backend:', this.user);
    
    this.userService.register(this.user).subscribe({
      next: (response) => {
        console.log('Respuesta exitosa:', response);
        this.isRegistered = true; 
      },
      error: (error) => {
        console.error('Error en registro:', error);
        // Manejo básico de error para mostrar algo útil al usuario
        if (error.status === 0) {
            this.errorMessage = 'No se pudo conectar con el servidor. ¿Está encendido el Backend?';
        } else {
            this.errorMessage = error.error || 'Hubo un problema al crear la cuenta. Verifica los datos.';
        }
      }
    });
  }
}