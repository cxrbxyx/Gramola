import { Component } from '@angular/core';
import { CommonModule } from '@angular/common'; // Importa CommonModule
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

  constructor(private service: UserService) {}

  registrar() {
    if (this.pwd1 != this.pwd2) {
      console.error('Las contraseñas no coinciden');
      return;
    }

    // Llama al servicio con TODOS los campos
    this.service
      .register(this.bar, this.email, this.pwd1, this.pwd2, this.clientID, this.clientSecret)
      .subscribe(
        (ok) => {
          console.log('Registro exitoso', ok);
        },
        (error) => {
          console.error('Error en el registro', error);
        }
      );
  }
}
