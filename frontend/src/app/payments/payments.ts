import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { PaymentService } from '../payments-service';
import { CommonModule } from '@angular/common';

// Declaramos la variable global de Stripe que cargamos en el index.html
declare var Stripe: any;

@Component({
  selector: 'app-payment',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './payments.html',
  styleUrls: ['./payments.css']
})
export class PaymentComponent implements OnInit {
  
  // Clave pública de prueba de Stripe.
  stripe = Stripe('pk_test_51SIV1j0Op3tHBSoLMp0bkfCdzgrL8EJ5BN0p5uygmGd6wPylMTRM3jCdV5YnafbHKj3BX7uBngFie8oNQMAJdjyW00P0gQ9vWO'); 
  
  card: any;
  transactionDetails: any;
  token: string | null = null; // Token de registro del usuario
  email: string | null = null; // Email recuperado (podrías pasarlo por URL también)
  
  paymentStatus: string = '';
  errorMessage: string = '';

  constructor(
    private paymentService: PaymentService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    // 1. Obtener el token de registro de la URL (del enlace del correo)
    this.token = this.route.snapshot.queryParamMap.get('token');
    // Para este ejemplo, asumimos que el email también podría venir o lo tienes guardado.
    // Si no, usa un email de prueba o modifica el link del correo para incluirlo.
    this.email = 'usuario_demo@test.com'; 
  }

  // Inicia el proceso de pago al hacer clic en el botón
  startPayment() {
    if (!this.email) return;

    // 2. Llamar al backend para crear el PaymentIntent (10.00€ para registro)
    this.paymentService.prepay(this.email, 1000).subscribe({
      next: (response) => {
        // Parseamos el JSON que guardamos en 'data' en el backend
        this.transactionDetails = JSON.parse(response.data);
        this.showStripeForm();
      },
      error: (err) => {
        this.errorMessage = 'Error iniciando el pago: ' + err.message;
      }
    });
  }

  // Muestra y monta el formulario de tarjeta de Stripe
  showStripeForm() {
    const elements = this.stripe.elements();
    
    // Estilos personalizados para el input de Stripe
    const style = {
      base: {
        color: '#32325d',
        fontFamily: '"Helvetica Neue", Helvetica, sans-serif',
        fontSmoothing: 'antialiased',
        fontSize: '16px',
        '::placeholder': {
          color: '#aab7c4'
        }
      },
      invalid: {
        color: '#fa755a',
        iconColor: '#fa755a'
      }
    };

    // Crear el elemento de tarjeta
    this.card = elements.create('card', { style: style });
    // Montarlo en el div con id 'card-element'
    this.card.mount('#card-element');

    // Escuchar cambios para validar errores en tiempo real
    this.card.on('change', (event: any) => {
      const displayError = document.getElementById('card-errors');
      if (event.error) {
        displayError!.textContent = event.error.message;
      } else {
        displayError!.textContent = '';
      }
    });
  }

  // Ejecutar el pago contra Stripe
  pay() {
    this.stripe.confirmCardPayment(this.transactionDetails.client_secret, {
      payment_method: {
        card: this.card
      }
    }).then((result: any) => {
      if (result.error) {
        // Error en el pago
        const displayError = document.getElementById('card-errors');
        displayError!.textContent = result.error.message;
      } else {
        // Pago exitoso
        if (result.paymentIntent.status === 'succeeded') {
          this.paymentStatus = '¡Pago realizado con éxito!';
          
          // Notificar al backend para finalizar el registro
          // Aquí usaríamos el ID de nuestra transacción interna
           // this.paymentService.confirm(...);

          setTimeout(() => {
             this.router.navigate(['/login']); // Redirigir al login
          }, 2000);
        }
      }
    });
  }
}