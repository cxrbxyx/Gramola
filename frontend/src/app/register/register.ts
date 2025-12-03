import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms'; // Necesario para [(ngModel)]
import { CommonModule } from '@angular/common'; // Necesario para *ngIf
import { UserService } from '../user-service'; // Importamos nuestro servicio
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, CommonModule], // Importamos los módulos aquí
  templateUrl: './register.html',
  styleUrl: './register.css' // Ojo: verifica si tu archivo es .css o .scss
})
export class RegisterComponent {

  // Variables vinculadas al formulario
  bar: string = "";
  email: string = "";
  pwd1: string = "";
  pwd2: string = "";
  clientId: string = "";
  clientSecret: string = "";

  // Mensajes de feedback
  mensajeError: string = "";
  mensajeExito: string = "";

  constructor(private userService: UserService, private router: Router) {}

  register() {
    // 1. Limpiamos mensajes previos
    this.mensajeError = "";
    this.mensajeExito = "";

    // 2. Validación básica frontend
    if (this.pwd1 !== this.pwd2) {
      this.mensajeError = "Las contraseñas no coinciden";
      return;
    }

    // 3. Llamada al servicio
    console.log("Enviando datos de registro...");
    this.userService.register(this.bar, this.email, this.pwd1, this.pwd2, this.clientId, this.clientSecret)
      .subscribe({
        next: (response) => {
          console.log("Registro correcto", response);
          this.mensajeExito = "¡Registro realizado! Revisa la consola de tu Backend (Eclipse/VSCode) para ver el link de confirmación simulado.";
          // Opcional: limpiar formulario
        },
        error: (error) => {
          console.error("Error en registro", error);
          if (error.status === 409) {
            this.mensajeError = "El usuario ya existe.";
          } else if (error.status === 406) {
             this.mensajeError = "Datos no aceptables (revisa contraseñas).";
          } else {
            this.mensajeError = "Error al conectar con el servidor.";
          }
        }
      });
  }
}