import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterLink } from '@angular/router';
import { UserService } from '../user-service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-verify',
  standalone: true,
  imports: [CommonModule, RouterLink], // Importamos CommonModule para directivas básicas y RouterLink para navegación
  templateUrl: './verify.html',
  styleUrls: ['../../styles.css'] // Reutilizamos estilos globales
})
export class VerifyComponent implements OnInit {

  message: string = 'Verificando tu cuenta...';
  isSuccess: boolean = false;
  isLoading: boolean = true;

  constructor(
    private route: ActivatedRoute,
    private userService: UserService
  ) {}

  ngOnInit(): void {
    // Capturamos el código de la URL
    const code = this.route.snapshot.queryParamMap.get('code');

    if (code) {
      this.verify(code);
    } else {
      this.isLoading = false;
      this.isSuccess = false;
      this.message = 'Enlace de verificación inválido.';
    }
  }

  verify(code: string) {
    this.userService.verifyAccount(code).subscribe({
      next: (response) => {
        this.isLoading = false;
        this.isSuccess = true;
        this.message = response; // Mensaje que viene del backend
      },
      error: (err) => {
        this.isLoading = false;
        this.isSuccess = false;
        // Intentamos leer el mensaje de error del backend, si existe
        this.message = err.error || 'Hubo un error al verificar tu cuenta.';
      }
    });
  }
}