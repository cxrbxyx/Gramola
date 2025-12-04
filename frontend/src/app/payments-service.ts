import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class PaymentService {
  
  // URL base de tu backend
  private apiUrl = 'http://localhost:8080/payments';

  constructor(private http: HttpClient) { }

  // Solicita el prepago al backend. 
  // amount: cantidad en céntimos (1000 = 10.00€)
  // email: correo del usuario que se está registrando
  prepay(email: string, amount: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/prepay?email=${email}&amount=${amount}`);
  }

  confirm(transactionId: string): Observable<void> {
    return this.http.post<void>(`${this.apiUrl}/confirm`, transactionId);
  }
}